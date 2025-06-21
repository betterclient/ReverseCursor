package io.github.betterclient.reversecursor.server

import io.github.betterclient.reversecursor.common.LinGanEncoder
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.defaultForFilePath
import io.ktor.server.netty.Netty
import io.ktor.server.engine.*
import io.ktor.server.request.receiveText
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main() {
    embeddedServer(Netty, port = 8888) {
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

                val path = call.parameters["path"] ?: return@get notFound()
                val safePath = path.replace(Regex("""[\\/]+"""), "/").removePrefix("/").removeSuffix("/").replace("..", "")
                val resource = ::main::class.java.getResourceAsStream("/static/$safePath")
                if (resource != null) {
                    val contentType = ContentType.defaultForFilePath(safePath)
                    call.respondBytes(resource.use { it.readAllBytes() }, contentType = contentType)
                } else {
                    notFound()
                }
            }
        }
    }.start(true)
}