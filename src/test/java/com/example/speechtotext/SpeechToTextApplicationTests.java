package com.example.speechtotext;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class SpeechToTextApplicationTests {

    @Autowired 
    private MockMvc mockMvc;

    @Test
    void statusEndpointReturnsRunningMessage() throws Exception {
        mockMvc.perform(get("/api/status"))
                .andExpect(status().isOk())
                .andExpect(content().string("Application is running"));
    }

    @Test
    void uptimeEndpointRetrunsInfo() throws Exception {
          mockMvc.perform(get("/api/v1/admin/uptime"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.utcServerStart").exists())
            .andExpect(jsonPath("$.utcNow").exists())
            .andExpect(jsonPath("$.serverUptimeSeconds").exists());
    }

    @Test
    void globalStatsEndpointReturnsTokenUsage() throws Exception {
           mockMvc.perform(get("/api/v1/global/stats"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.inputTokens").exists())
            .andExpect(jsonPath("$.outputTokens").exists());
    }


}

