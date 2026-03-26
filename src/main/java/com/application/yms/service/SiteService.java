package com.application.yms.service;

import com.application.yms.dao.SiteDAO;
import com.application.yms.exception.DuplicateResourceException;
import com.application.yms.exception.ResourceNotFoundException;
import com.application.yms.exception.YmsBusinessException;
import com.application.yms.model.Site;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SiteService {

    private final SiteDAO siteDAO;

    public Site createSite(Site site) {
        // Generate UUID if not provided
        if (site.getUuid() == null || site.getUuid().isEmpty()) {
            site.setUuid(UUID.randomUUID().toString());
        }

        // Set _id to code if not provided
        if (site.get_id() == null || site.get_id().isEmpty()) {
            site.set_id(site.getCode());
        }

        // Check if site with same code already exists
        if (siteDAO.existsByCode(site.getCode())) {
            throw new DuplicateResourceException("Site with code " + site.getCode() + " already exists");
        }

        // Save the site
        try {
            return siteDAO.save(site);
        } catch (DataAccessException ex) {
            throw new YmsBusinessException("Database error while creating site", ex);
        }
    }

    public Site getSiteByCode(String code) {
        try {
            Site site = siteDAO.findByCode(code);
            if (site == null) {
                throw new ResourceNotFoundException("Site with code " + code + " not found");
            }
            return site;
        } catch (DataAccessException ex) {
            throw new YmsBusinessException("Database error while fetching site", ex);
        }
    }
}

