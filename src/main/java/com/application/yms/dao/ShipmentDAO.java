package com.application.yms.dao;

import com.application.yms.model.Shipment;

public interface ShipmentDAO {
    Shipment save(Shipment shipment);

    Shipment findByShipmentNo(String shipmentNo);
}
