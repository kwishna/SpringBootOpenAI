package ai.spring.code.services;

import org.springframework.ai.chat.messages.Media;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.openai.api.OpenAiApi.ChatCompletionRequest;
import org.springframework.ai.openai.api.OpenAiApi.ChatCompletionMessage;
import org.springframework.ai.openai.api.OpenAiApi.ChatCompletion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import java.util.List;

@Service
public class OpenAiServices {

    @Autowired
    OpenAiChatModel openAiChatModel;

    @Autowired
    OpenAiApi getOpenAiApi;

    public ChatResponse explainTheImage(String prompt, Resource image) {
        var userMessage = new UserMessage(prompt, List.of(new Media(MimeTypeUtils.IMAGE_PNG, image)));

        // ChatResponse response = chatModel.call(
        //    new Prompt(List.of(userMessage),
        //    OpenAiChatOptions.builder().withModel(OpenAiApi.ChatModel.GPT_4_O.getValue()).build())
        //    );
        return openAiChatModel.call(new Prompt(List.of(userMessage)));
    }

    public ChatResponse answerQuery(String prompt) {
        ChatResponse response = openAiChatModel.call(new Prompt(prompt));

        // Or, with streaming responses
        // Flux<ChatResponse> response = openAiChatModel.stream(new Prompt("Generate the names of 5 famous pirates."));
        return response;
    }

    public ResponseEntity<ChatCompletion> chatResponse(String query) {
        ChatCompletionMessage systemMsg = new ChatCompletionMessage("You're a friendly AI chatbot.", ChatCompletionMessage.Role.SYSTEM);
        ChatCompletionMessage userMsg1 = new ChatCompletionMessage("Hello Bot", ChatCompletionMessage.Role.USER);
        ChatCompletionMessage assistantMsg1 = new ChatCompletionMessage("Hello! I welcome you. Please ask your question.", ChatCompletionMessage.Role.ASSISTANT);
        ChatCompletionMessage userMsg2 = new ChatCompletionMessage(query, ChatCompletionMessage.Role.USER);

        ChatCompletionRequest chatCompletionRequest = new ChatCompletionRequest(
                List.of(systemMsg, userMsg1, assistantMsg1, userMsg2),
                OpenAiApi.ChatModel.GPT_4_O.getValue(),
                0.8f,
                false
        );

        // Streaming request
        //  Flux<OpenAiApi.ChatCompletionChunk> streamResponse = getOpenAiApi.chatCompletionStream(
        //      new OpenAiApi.ChatCompletionRequest(List.of(chatCompletionMessage), "gpt-3.5-turbo", 0.8f, true)
        //      );

        // Sync request
        return getOpenAiApi.chatCompletionEntity(chatCompletionRequest);
    }

}
