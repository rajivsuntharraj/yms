package com.application.yms.controller;

import com.application.yms.model.Site;
import com.application.yms.service.SiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sites")
@RequiredArgsConstructor
public class SiteController {

    private final SiteService siteService;

    @PostMapping
    public ResponseEntity<Site> createSite(@RequestBody Site site) {
        Site createdSite = siteService.createSite(site);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSite);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Site> getSiteByCode(@PathVariable String code) {
        Site site = siteService.getSiteByCode(code);
        return ResponseEntity.ok(site);
    }
}
