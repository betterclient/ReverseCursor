package io.github.betterclient.reversecursor.server.ai

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyDescription
import io.github.betterclient.reversecursor.server.files
import io.github.sashirestela.openai.common.function.FunctionDef
import io.github.sashirestela.openai.common.function.FunctionExecutor
import io.github.sashirestela.openai.common.function.Functional
import io.github.sashirestela.openai.common.tool.ToolCall
import io.github.sashirestela.openai.domain.chat.ChatMessage
import java.util.UUID

var executingUUID = ""

class IndexTool : Functional {
    @JsonPropertyDescription("Code to put in the index.html document")
    @JsonProperty(required = true)
    lateinit var code: String

    override fun execute(): Any? {
        files.computeIfAbsent(UUID.fromString(executingUUID)) { it ->
            return@computeIfAbsent mutableMapOf()
        }["index.html"] = code

        return "OK."
    }
}

class OtherFile : Functional {
    @JsonPropertyDescription("File name")
    @JsonProperty(required = true)
    lateinit var name: String

    @JsonPropertyDescription("Code to put in the file")
    @JsonProperty(required = true)
    lateinit var code: String

    override fun execute(): Any? {
        files.computeIfAbsent(UUID.fromString(executingUUID)) { it ->
            return@computeIfAbsent mutableMapOf()
        }[name] = code

        return "OK."
    }
}

fun getTools(): FunctionExecutor {
    val functions = FunctionExecutor()
    functions.enrollFunction(
        FunctionDef.builder()
            .name("Index")
            .description("Set index.html")
            .functionalClass(IndexTool::class.java)
            .strict(true)
            .build()
    )

    functions.enrollFunction(
        FunctionDef.builder()
            .name("OtherFile")
            .description("Create a file that index.html references")
            .functionalClass(OtherFile::class.java)
            .strict(true)
            .build()
    )

    return functions
}

fun handleToolCalls(prompt: Prompt, message: ChatMessage.ResponseMessage, tools: FunctionExecutor): Boolean {
    var toolCalls = message.toolCalls?: mutableListOf()
    var allowUser = true
    if (toolCalls.isNotEmpty()) {
        toolCalls = toolCalls.map {
            var id = it.id
            if (id == null || id.isEmpty()) {
                id = UUID.randomUUID().toString()
            }
            return@map ToolCall(it.index, id, it.type, it.function)
        }

        prompt.conversation.add(ChatMessage.AssistantMessage.builder().toolCalls(toolCalls).content(" ").build())

        for (call in toolCalls) {
            val function = call.function
            if (function == null) continue

            val execute = tools.execute<String>(function).ifEmpty { " " }
            allowUser = false

            val toolMessage = ChatMessage.ToolMessage.of(execute, call.id)
            prompt.conversation.add(toolMessage)
        }
    }
    return allowUser
}