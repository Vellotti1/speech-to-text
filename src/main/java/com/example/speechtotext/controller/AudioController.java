package com.example.speechtotext.controller;

import com.example.speechtotext.service.OpenAIService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/audio")
public class AudioController {

    private final OpenAIService openAIService;

    public AudioController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping
    public ResponseEntity<String> uploadAudio(
            @RequestParam("audio") MultipartFile audio) {

            try {
                System.out.println("Received File:" + audio.getOriginalFilename());
                System.out.println("File size:" + audio.getSize() + "bytes");

                String transcription = openAIService.transcribeAudio(audio);

                System.out.println("Transcription:" + transcription);

                return ResponseEntity.ok(transcription);

            } catch (Exception error) {
                error.printStackTrace();

                return ResponseEntity.internalServerError()
                    .body("Failed to transcribe audio. Try again later");
            }
                
       

        

    }

}
