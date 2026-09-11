package com.example.speechtotext.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/audio")
public class AudioController {

    @PostMapping
    public ResponseEntity<String> uploadAudio(
            @RequestParam("audio") MultipartFile audio) {

        System.out.println("Received File:" + audio.getOriginalFilename());
        System.out.println("File size:" + audio.getSize() + "butes");

        return ResponseEntity.ok("Audio was sucessfully recieved");

    }

}
