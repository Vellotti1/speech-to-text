package com.example.speechtotext.controller;

import com.example.speechtotext.service.StatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/global")
public class GlobalStatsController {
    
    private final StatisticsService statisticsService;

    public GlobalStatsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/stats")
    public ResponseEntity<GlobalStatsResponse> getGlobalStats() {
        
        GlobalStatsResponse response = new GlobalStatsResponse(
        statisticsService.getInputTokens(),
        statisticsService.getOutputTokens()
        );

        return ResponseEntity.ok(response);
    } 

    public record GlobalStatsResponse(
        long inputTokens,
        long outputTokens
    ) {}
}
