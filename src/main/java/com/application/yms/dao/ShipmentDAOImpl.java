package com.application.yms.dao;

import com.application.yms.model.Carrier;
import com.application.yms.model.Shipment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ShipmentDAOImpl implements ShipmentDAO {
    private final MongoTemplate mongoTemplate;

    @Override
    public Shipment save(Shipment shipment) {
        return mongoTemplate.save(shipment);
    }

    @Override
    public Shipment findByShipmentNo(String shipmentNo) {
        Query query = new Query(Criteria.where("_id").is(shipmentNo));
        return mongoTemplate.findOne(query, Shipment.class);
    }
}
