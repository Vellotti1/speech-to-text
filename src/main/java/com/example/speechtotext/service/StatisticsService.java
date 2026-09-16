package com.example.speechtotext.service;

import org.springframework.stereotype.Service;

@Service 
public class StatisticsService {
    
    private long inputTokens = 0;
    private long outputTokens = 0;

    public synchronized void addUsage(long inputTokens, long outputTokens) {
        this.inputTokens += inputTokens;
        this.outputTokens += outputTokens;
    }

    public synchronized long getInputTokens() {
        return inputTokens;
    }

    public synchronized long getOutputTokens() {
        return outputTokens;
    }

}
