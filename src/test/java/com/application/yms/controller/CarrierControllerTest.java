package com.application.yms.controller;

import com.application.yms.exception.DuplicateResourceException;
import com.application.yms.exception.GlobalExceptionHandler;
import com.application.yms.exception.ResourceNotFoundException;
import com.application.yms.exception.YmsBusinessException;
import com.application.yms.model.Carrier;
import com.application.yms.service.CarrierService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CarrierController.class)
@Import(GlobalExceptionHandler.class)
class CarrierControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarrierService carrierService;

    @Test
    @DisplayName("POST /api/v1/carriers - success")
    void createCarrier_success() throws Exception {
        Carrier saved = new Carrier();
        saved.setScac("ABCD");
        saved.setName("Test Carrier");

        given(carrierService.createCarrier(any(Carrier.class))).willReturn(saved);

        String body = """
                {
                  "scac": "ABCD",
                  "name": "Test Carrier"
                }
                """;

        mockMvc.perform(post("/api/v1/carriers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.scac", is("ABCD")))
                .andExpect(jsonPath("$.name", is("Test Carrier")));
    }

    @Test
    @DisplayName("POST /api/v1/carriers - duplicate SCAC returns 400 with error body")
    void createCarrier_duplicateScac() throws Exception {
        given(carrierService.createCarrier(any(Carrier.class)))
                .willThrow(new DuplicateResourceException("Carrier with scac ABCD already exists"));

        String body = """
                {
                  "scac": "ABCD",
                  "name": "Test Carrier"
                }
                """;

        mockMvc.perform(post("/api/v1/carriers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.error", is("Bad Request")))
                .andExpect(jsonPath("$.message", is("Carrier with scac ABCD already exists")));
    }

    @Test
    @DisplayName("GET /api/v1/carriers/{scac} - not found returns 404 with error body")
    void getCarrier_notFound() throws Exception {
        given(carrierService.getCarrierByScac("MISSING"))
                .willThrow(new ResourceNotFoundException("Carrier with scac MISSING not found"));

        mockMvc.perform(get("/api/v1/carriers/{scac}", "MISSING"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.error", is("Not Found")))
                .andExpect(jsonPath("$.message", is("Carrier with scac MISSING not found")));
    }

    @Test
    @DisplayName("PUT /api/v1/carriers/{scac} - database error is wrapped and returned as 500")
    void updateCarrier_databaseError() throws Exception {
        given(carrierService.updateCarrier(eq("ABCD"), any(Carrier.class)))
                .willThrow(new YmsBusinessException(
                        "Database error while updating carrier",
                        new DataAccessResourceFailureException("Mongo down")));

        String body = """
                {
                  "scac": "ABCD",
                  "name": "Updated Carrier"
                }
                """;

        mockMvc.perform(put("/api/v1/carriers/{scac}", "ABCD")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status", is(500)))
                .andExpect(jsonPath("$.error", is("Internal Server Error")))
                .andExpect(jsonPath("$.message", is("Database error while updating carrier")));
    }

    @Test
    @DisplayName("DELETE /api/v1/carriers/{scac} - success returns 200")
    void softDeleteCarrier_success() throws Exception {
        Carrier deleted = new Carrier();
        deleted.setScac("ABCD");
        given(carrierService.softDeleteCarrier("ABCD")).willReturn(deleted);

        mockMvc.perform(delete("/api/v1/carriers/{scac}", "ABCD"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.scac", is("ABCD")));
    }
}

