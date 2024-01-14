package com.davi.Screescreenmatch.service;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;

import java.util.ArrayList;
import java.util.List;

public class ConsultaChatGPT {
    public static String obterTraducao(String texto) {
        List<ChatMessage> messages = new ArrayList<>();
        OpenAiService service = new OpenAiService(
                System.getenv("OPENAI_APIKEY"));

        ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value()
                , "Traduza para o português o texto:"+ texto);
        messages.add(systemMessage);

        ChatCompletionRequest requisicao = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo-1106")
                .messages(messages)
                .maxTokens(1000)
                .temperature(0.7)
                .build();

        var resposta = service.createChatCompletion(requisicao);
        return resposta.getChoices().get(0).getMessage().getContent();
    }
}