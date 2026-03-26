package com.application.yms.service;

import com.application.yms.dao.ShipmentDAO;
import com.application.yms.exception.ResourceNotFoundException;
import com.application.yms.exception.YmsBusinessException;
import com.application.yms.model.Carrier;
import com.application.yms.model.Shipment;
import com.application.yms.model.Site;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShipmentService {
    private final ShipmentDAO shipmentDAO;
    public Shipment createShipment(Shipment shipment) {
        shipment.set_id(shipment.getShipmentNo());
        // Save the shipment
        try {
            return shipmentDAO.save(shipment);
        } catch (DataAccessException ex) {
            throw new YmsBusinessException("Database error while creating site", ex);
        }
    }

    public Shipment getShipmentByShipmentNo(String shipmentNo) {
        try {
            Shipment shipment = shipmentDAO.findByShipmentNo(shipmentNo);
            if (shipment == null) {
                throw new ResourceNotFoundException("Shipment with shipmentNo " + shipmentNo + " not found");
            }
            return shipment;
        } catch (DataAccessException ex) {
            throw new YmsBusinessException("Database error while fetching carrier", ex);
        }

    }
}
