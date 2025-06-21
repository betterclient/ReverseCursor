package io.github.betterclient.reversecursor.server.ai

import io.github.sashirestela.openai.SimpleOpenAIGeminiGoogle
import io.github.sashirestela.openai.domain.chat.ChatMessage
import io.github.sashirestela.openai.domain.chat.ChatRequest
import io.github.sashirestela.openai.exception.OpenAIException
import java.io.File

object GeminiManager {
    val tokens = mutableListOf<String>()
    init {
        val text = File("../.env").readLines()
        text.forEach {
            tokens.add(it
                .replace("\r", "")
                .replace("\n", "")
            )
        }
    }

    fun generate(prompt: Prompt) {
        val tools = getTools()
        val chatRequest = ChatRequest.builder()
            .model("gemini-2.5-flash")
            .messages(prompt.conversation)
            .tools(tools.toolFunctions)
            .build()

        for (token in tokens) {
            try {
                val gemini = SimpleOpenAIGeminiGoogle.builder().apiKey(token).build()

                val req = gemini.chatCompletions().create(chatRequest)
                val chat = req.join()

                val message = chat.firstMessage()

                val trim = message.content?.trim()
                if (trim?.isNotEmpty() == true) {
                    prompt.conversation.add(ChatMessage.AssistantMessage.of(trim))
                }

                if (!handleToolCalls(prompt, message, tools)) {
                    generate(prompt) //recurse if we need to handle tool calls
                }
                return
            } catch (_: OpenAIException.RateLimitException) {
                continue
            }
        }

        prompt.conversation.add(ChatMessage.AssistantMessage.of("Rate limited."))
    }
}