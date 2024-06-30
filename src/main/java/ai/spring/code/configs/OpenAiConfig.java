package ai.spring.code.configs;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAiConfig {

    @Value("${spring.ai.openai.chat.api-key}")
    String openAiApiKey;

    @Bean
    public OpenAiChatModel openAiChatModel() {
        return new OpenAiChatModel(
                new OpenAiApi(openAiApiKey),
                OpenAiChatOptions.builder()
                        .withTemperature(0.7f)
                        .withModel(OpenAiApi.ChatModel.GPT_4_O.getValue())
                        .build()
        );
    }

    @Bean
    public OpenAiApi getOpenAiApi() {
        return new OpenAiApi(openAiApiKey);
    }
}
