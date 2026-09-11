package com.example.speechtotext.controller;

import java.time.Duration;
import java.time.Instant;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final Instant serverStart;

    public AdminController () {
        this.serverStart = Instant.now();
    }

    @GetMapping("/uptime")
    public ResponseEntity<UptimeResponse> getServerUptime() {
        Instant now = Instant.now();

        double seconds =
            Duration.between(serverStart, now).toNanos() / 1000000000.0;

        UptimeResponse response = new UptimeResponse(
            serverStart,
            now,
            seconds
        );

        return ResponseEntity.ok(response);
    }

    public record UptimeResponse(
        Instant utcServerStart,
        Instant utcNow,
        double serverUptimeSeconds
    ) {}


}