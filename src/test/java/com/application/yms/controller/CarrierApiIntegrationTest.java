package com.application.yms.controller;

import com.application.yms.dao.CarrierDAO;
import com.application.yms.model.Carrier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Full-flow integration test for Carrier API:
 * Controller -> Service -> DAO -> MongoDB.
 *
 * NOTE: This test expects a running MongoDB as configured in application.properties.
 */
@SpringBootTest
@AutoConfigureMockMvc
class CarrierApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CarrierDAO carrierDAO;

    @BeforeEach
    void cleanDatabase() {
        // Simple cleanup so each test starts with a clean state for this SCAC
        Carrier existing = carrierDAO.findByScac("INT1");
        if (existing != null) {
            existing.setIs_deleted(true);
            carrierDAO.update(existing);
        }
    }

    @Test
    @DisplayName("Full flow: POST /api/v1/carriers creates carrier in Mongo")
    void createCarrier_fullFlow_persistsToMongo() throws Exception {
        String body = """
                {
                  "scac": "INT1",
                  "name": "Integration Carrier"
                }
                """;

        // Call real controller endpoint
        mockMvc.perform(post("/api/v1/carriers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.scac").value("INT1"))
                .andExpect(jsonPath("$.name").value("Integration Carrier"));

        // Verify it really went through Service -> DAO -> Mongo
        Carrier fromDb = carrierDAO.findByScac("INT1");
        assertThat(fromDb).isNotNull();
        assertThat(fromDb.getName()).isEqualTo("Integration Carrier");
    }
}

