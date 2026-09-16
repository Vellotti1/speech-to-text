package com.example.speechtotext.controller;

import java.time.Duration;
import java.time.Instant;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final Instant serverStart;
    private final ConfigurableApplicationContext appContext;
    private boolean isShutingdown = false;

    public AdminController (ConfigurableApplicationContext appContext) {
        this.serverStart = Instant.now();
        this.appContext = appContext;
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

    @PostMapping("/shutdown")

    public ResponseEntity<ShutdownResponse> shutdown() {
        if (isShutingdown) {
            return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(new ShutdownResponse("Graceful shutdown is already in progress."));
        }

        isShutingdown = true;

        Thread shutdownThread = new Thread(() -> {
            appContext.close();
        });

        shutdownThread.start();

        return ResponseEntity
        .status(HttpStatus.ACCEPTED)
        .body(new ShutdownResponse("Graceful shutdown requested."));

    }

    public record UptimeResponse(
        Instant utcServerStart,
        Instant utcNow,
        double serverUptimeSeconds
    ) {}

    public record ShutdownResponse(
        String message
    ) {}


}