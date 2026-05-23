package com.slim.agent.config;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class LangChain4jConfig {

    @Value("${langchain4j.ollama.base-url:http://localhost:11434}")
    private String baseUrl;

    @Value("${langchain4j.ollama.model-name:deepseek-r1:7b}")
    private String modelName;

    @Value("${langchain4j.ollama.timeout:120}")
    private Integer timeout;

    @Bean
    public ChatLanguageModel chatLanguageModel() {
        return OllamaChatModel.builder()
                .baseUrl(baseUrl)
                .modelName(modelName)
                .timeout(Duration.ofSeconds(timeout))
                .build();
    }

}
