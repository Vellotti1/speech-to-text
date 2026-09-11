package com.example.speechtotext.controller;

import java.time.Duration;
import java.time.Instant;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final Instant serverStart;
    private boolean isShutingdown = false;

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

    @PostMapping("/shutdown")
    public ResponseEntity<ShutdownResponse> shutdown() {
        if (isShutingdown) {
            return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(new ShutdownResponse("Shutdown in progress"));
        }

        isShutingdown = true;

        return ResponseEntity
        .status(HttpStatus.ACCEPTED)
        .body(new ShutdownResponse("Shutdown Requested"));

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