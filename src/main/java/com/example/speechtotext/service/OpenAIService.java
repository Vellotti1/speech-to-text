package com.example.speechtotext.service;


import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import org.springframework.stereotype.Service;

@Service 
public class OpenAIService {

    private final OpenAIClient client;

    public OpenAIService() {
        this.client = OpenAIOkHttpClient.fromEnv();
    }

    public String testConnection() {
        return "OpenAI client conifgured.";
    }

}