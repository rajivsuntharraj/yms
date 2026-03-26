package com.application.yms.dao;

import com.application.yms.model.Site;

public interface SiteDAO {
    Site save(Site site);
    Site findByCode(String code);
    boolean existsByCode(String code);
}
