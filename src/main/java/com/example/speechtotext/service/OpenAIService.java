package com.example.speechtotext.service;


import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.audio.AudioModel;
import com.openai.models.audio.transcriptions.TranscriptionCreateParams;
import com.openai.models.audio.transcriptions.TranscriptionCreateResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.speechtotext.service.StatisticsService;
import com.openai.models.audio.transcriptions.Transcription;

@Service 
public class OpenAIService {

    private final OpenAIClient client;
    private final StatisticsService statisticsService;

    public OpenAIService(StatisticsService statisticsService) {
        this.client = OpenAIOkHttpClient.fromEnv();
        this.statisticsService = statisticsService;
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

        Transcription transcription = response.asTranscription();

        if (transcription.usage().isPresent()) {
            var usage = transcription.usage().get();


            if (usage.tokens().isPresent()) {
                var tokens = usage.tokens().get();

                statisticsService.addUsage(
                tokens.inputTokens(),
                tokens.outputTokens()
            );
            }

        }
        return transcription.text();
    }
    

}