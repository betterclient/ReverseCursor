package io.github.betterclient.reversecursor.server

import io.github.betterclient.reversecursor.common.LinGanEncoder
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.defaultForFilePath
import io.ktor.server.application.install
import io.ktor.server.netty.Netty
import io.ktor.server.engine.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.request.receiveText
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.UUID

fun main() {
    embeddedServer(Netty, port = 8888) {
        install(Compression)

        routing {
            get("/") {
                call.respondBytes(
                    ::main::class.java.getResourceAsStream("/index.html")!!.use {
                        it.readAllBytes()
                    }, contentType = ContentType.Text.Html
                )
            }

            post("/generate") {
                val text = LinGanEncoder.decrypt(call.receiveText())
                val output = generate(text)

                call.respondText(text =
                    LinGanEncoder.encrypt(output.output)+
                    ":"+
                    LinGanEncoder.encrypt(output.link)+
                    ":"+
                    LinGanEncoder.encrypt(output.hash)
                )
            }

            post("/message") {
                val t = call.receiveText().split(":")
                val chatInput = LinGanEncoder.decrypt(t[0])
                val chatHash = LinGanEncoder.decrypt(t[1])

                val output = message(chatInput, chatHash)

                call.respondText(text =
                    LinGanEncoder.encrypt(output.output) +
                    ":" +
                    LinGanEncoder.encrypt(output.link)
                )
            }

            get("/{path...}") {
                suspend fun notFound() {
                    call.respondText("404", status = HttpStatusCode.NotFound)
                }

                val path = call.parameters.getAll("path")?.joinToString("/") ?: return@get notFound()
                val safePath = path.replace(Regex("""(?:\.\./)+"""), "") //yes, I generate my regex with AI.

                val resource = ::main::class.java.getResourceAsStream("/static/$safePath")
                if (resource != null) {
                    val contentType = ContentType.defaultForFilePath(safePath)
                    call.respondBytes(resource.use { it.readAllBytes() }, contentType = contentType)
                } else {
                    if (handleView(safePath)) return@get

                    notFound()
                }
            }
        }
    }.start(true)
}

suspend fun RoutingContext.handleView(safePath: String): Boolean {
    if (safePath.startsWith("view/")) {
        val uuid = safePath.removePrefix("view/").substringBefore("/")
        val file = safePath.substringAfter("view/$uuid/")

        files[UUID.fromString(uuid)]?.let { files0 ->
            if (file.isEmpty()) {
                files0["index.html"]?.let { file0 ->
                    call.respondBytes(file0.toByteArray(), ContentType.Text.Html)
                    return true
                }
            } else {
                files0[file]?.let { file0 ->
                    call.respondBytes(file0.toByteArray(), ContentType.defaultForFilePath(file))
                    return true
                }
            }
        }
    }

    return false
}