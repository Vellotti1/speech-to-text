package com.example.speechtotext.service;


import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.audio.AudioModel;
import com.openai.models.audio.transcriptions.TranscriptionCreateParams;
import com.openai.models.audio.transcriptions.TranscriptionCreateResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service 
public class OpenAIService {

    private final OpenAIClient client;

    public OpenAIService() {
        this.client = OpenAIOkHttpClient.fromEnv();
    }

    public String transcribeAudio(MultipartFile audio) throws Exception{

        TranscriptionCreateParams paramiters = TranscriptionCreateParams.builder()
        .file(audio.getBytes())
        .model(AudioModel.GPT_4O_MINI_TRANSCRIBE_2025_12_15)
        .build();

        TranscriptionCreateResponse response = 
            client.audio()
                .transcriptions()
                .create(paramiters);

        return response.asTranscription().text();
    }
    

}