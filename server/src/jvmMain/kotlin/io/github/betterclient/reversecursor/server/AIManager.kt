package io.github.betterclient.reversecursor.server

import java.util.UUID

fun generate(input: String): GenerateOutput {

    return GenerateOutput("", "")
}

fun message(input: String, hash: String): MessageOutput {

    return MessageOutput("", "")
}

class GenerateOutput(val output: String, val link: String, val hash: String = UUID.randomUUID().toString())
class MessageOutput(val output: String, val link: String)