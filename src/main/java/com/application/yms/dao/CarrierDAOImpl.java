package com.application.yms.dao;

import com.application.yms.model.Carrier;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CarrierDAOImpl implements CarrierDAO {

    private final MongoTemplate mongoTemplate;

    @Override
    public Carrier save(Carrier carrier) {
        return mongoTemplate.save(carrier);
    }

    @Override
    public Carrier findByScac(String scac) {
        Query query = new Query(Criteria.where("scac").is(scac));
        return mongoTemplate.findOne(query, Carrier.class);
    }

    @Override
    public boolean existsByScac(String scac) {
        Query query = new Query(Criteria.where("scac").is(scac));
        return mongoTemplate.exists(query, Carrier.class);
    }

    @Override
    public Carrier update(Carrier carrier) {
        return mongoTemplate.save(carrier);
    }

    @Override
    public Carrier softDeleteByScac(String scac) {
        Query query = new Query(Criteria.where("scac").is(scac));
        Carrier existing = mongoTemplate.findOne(query, Carrier.class);
        if (existing == null) {
            return null;
        }
        existing.setIs_deleted(true);
        return mongoTemplate.save(existing);
    }
}

