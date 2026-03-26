package com.application.yms.controller;

import com.application.yms.exception.DuplicateResourceException;
import com.application.yms.exception.GlobalExceptionHandler;
import com.application.yms.exception.ResourceNotFoundException;
import com.application.yms.exception.YmsBusinessException;
import com.application.yms.model.Site;
import com.application.yms.service.SiteService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SiteController.class)
@Import(GlobalExceptionHandler.class)
class SiteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SiteService siteService;

    @Test
    @DisplayName("POST /api/v1/sites - success")
    void createSite_success() throws Exception {
        Site saved = new Site();
        saved.setCode("SITE1");
        saved.setSite("Main Site");

        given(siteService.createSite(any(Site.class))).willReturn(saved);

        String body = """
                {
                  "code": "SITE1",
                  "site": "Main Site"
                }
                """;

        mockMvc.perform(post("/api/v1/sites")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code", is("SITE1")))
                .andExpect(jsonPath("$.site", is("Main Site")));
    }

    @Test
    @DisplayName("POST /api/v1/sites - duplicate code returns 400 with error body")
    void createSite_duplicateCode() throws Exception {
        given(siteService.createSite(any(Site.class)))
                .willThrow(new DuplicateResourceException("Site with code SITE1 already exists"));

        String body = """
                {
                  "code": "SITE1",
                  "site": "Main Site"
                }
                """;

        mockMvc.perform(post("/api/v1/sites")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.error", is("Bad Request")))
                .andExpect(jsonPath("$.message", is("Site with code SITE1 already exists")));
    }

    @Test
    @DisplayName("GET /api/v1/sites/{code} - not found returns 404 with error body")
    void getSite_notFound() throws Exception {
        given(siteService.getSiteByCode("MISSING"))
                .willThrow(new ResourceNotFoundException("Site with code MISSING not found"));

        mockMvc.perform(get("/api/v1/sites/{code}", "MISSING"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.error", is("Not Found")))
                .andExpect(jsonPath("$.message", is("Site with code MISSING not found")));
    }

    @Test
    @DisplayName("GET /api/v1/sites/{code} - database error is wrapped and returned as 500")
    void getSite_databaseError() throws Exception {
        given(siteService.getSiteByCode(eq("SITE1")))
                .willThrow(new YmsBusinessException(
                        "Database error while fetching site",
                        new DataAccessResourceFailureException("Mongo down")));

        mockMvc.perform(get("/api/v1/sites/{code}", "SITE1"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status", is(500)))
                .andExpect(jsonPath("$.error", is("Internal Server Error")))
                .andExpect(jsonPath("$.message", is("Database error while fetching site")));
    }
}

