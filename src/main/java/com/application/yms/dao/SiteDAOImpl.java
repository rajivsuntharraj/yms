package com.application.yms.dao;

import com.application.yms.model.Site;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SiteDAOImpl implements SiteDAO {

    private final MongoTemplate mongoTemplate;

    @Override
    public Site save(Site site) {
        return mongoTemplate.save(site);
    }

    @Override
    public Site findByCode(String code) {
        Query query = new Query(Criteria.where("code").is(code));
        return mongoTemplate.findOne(query, Site.class);
    }

    @Override
    public boolean existsByCode(String code) {
        Query query = new Query(Criteria.where("code").is(code));
        return mongoTemplate.exists(query, Site.class);
    }
}
