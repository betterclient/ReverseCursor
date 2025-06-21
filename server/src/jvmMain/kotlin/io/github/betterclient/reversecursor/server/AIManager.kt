package io.github.betterclient.reversecursor.server

import io.github.betterclient.reversecursor.server.ai.GeminiManager
import io.github.betterclient.reversecursor.server.ai.Prompt
import io.github.betterclient.reversecursor.server.ai.executingUUID
import io.github.sashirestela.openai.domain.chat.ChatMessage
import java.util.UUID

val prompts = mutableMapOf<UUID, Prompt>()
val files = mutableMapOf<UUID, MutableMap<String, String>>()

fun generate(input: String): GenerateOutput {
    val hash = UUID.randomUUID()
    val prompt = Prompt(input)
    prompts[hash] = prompt
    executingUUID = hash.toString()

    val previousSize = prompt.conversation.size
    GeminiManager.generate(prompt)
    val newMessages = prompt.conversation.subList(previousSize, prompt.conversation.size)
    val assistantResponses = newMessages.filterIsInstance<ChatMessage.AssistantMessage>()
    val combinedContent = assistantResponses
        .mapNotNull { it.content as? String }
        .joinToString(" ") { it.trim() }
        .replace(Regex("\\s+"), " ")
        .trim()

    return GenerateOutput(combinedContent, "/view/${hash}/", hash.toString())
}

fun message(input: String, hash: String): MessageOutput {
    val hash = UUID.fromString(hash)
    val prompt = prompts[hash] ?: return MessageOutput("Invalid hash", "")
    executingUUID = hash.toString()
    prompt.conversation.add(ChatMessage.UserMessage.of(input))

    val previousSize = prompt.conversation.size
    GeminiManager.generate(prompt)
    val newMessages = prompt.conversation.subList(previousSize, prompt.conversation.size)
    val assistantResponses = newMessages.filterIsInstance<ChatMessage.AssistantMessage>()
    val combinedContent = assistantResponses
        .mapNotNull { it.content as? String }
        .joinToString(" ") { it.trim() }
        .replace(Regex("\\s+"), " ")
        .trim()

    return MessageOutput(combinedContent, "/view/${hash}/")
}

class GenerateOutput(val output: String, val link: String, val hash: String)
class MessageOutput(val output: String, val link: String)