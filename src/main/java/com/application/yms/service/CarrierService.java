package com.application.yms.service;

import com.application.yms.dao.CarrierDAO;
import com.application.yms.exception.DuplicateResourceException;
import com.application.yms.exception.ResourceNotFoundException;
import com.application.yms.exception.YmsBusinessException;
import com.application.yms.model.Carrier;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarrierService {

    private final CarrierDAO carrierDAO;

    public Carrier createCarrier(Carrier carrier) {
        if (carrier.getUuid() == null || carrier.getUuid().isEmpty()) {
            carrier.setUuid(UUID.randomUUID().toString());
        }

        // Default _id to scac if not provided
        if (carrier.get_id() == null || carrier.get_id().isEmpty()) {
            carrier.set_id(carrier.getScac());
        }

        if (carrier.getScac() == null || carrier.getScac().isEmpty()) {
            throw new IllegalArgumentException("scac is required");
        }

        if (carrierDAO.existsByScac(carrier.getScac())) {
            throw new DuplicateResourceException("Carrier with scac " + carrier.getScac() + " already exists");
        }

        try {
            return carrierDAO.save(carrier);
        } catch (DataAccessException ex) {
            throw new YmsBusinessException("Database error while creating carrier", ex);
        }
    }

    public Carrier getCarrierByScac(String scac) {
        try {
            Carrier carrier = carrierDAO.findByScac(scac);
            if (carrier == null) {
                throw new ResourceNotFoundException("Carrier with scac " + scac + " not found");
            }
            return carrier;
        } catch (DataAccessException ex) {
            throw new YmsBusinessException("Database error while fetching carrier", ex);
        }
    }

    public Carrier updateCarrier(String scac, Carrier carrier) {
        Carrier existing;
        try {
            existing = carrierDAO.findByScac(scac);
        } catch (DataAccessException ex) {
            throw new YmsBusinessException("Database error while fetching carrier for update", ex);
        }
        if (existing == null) {
            throw new ResourceNotFoundException("Carrier with scac " + scac + " not found");
        }

        // Enforce scac/id consistency
        carrier.setScac(scac);
        carrier.set_id(scac);

        // Preserve UUID if not provided
        if (carrier.getUuid() == null || carrier.getUuid().isEmpty()) {
            carrier.setUuid(existing.getUuid() != null ? existing.getUuid() : UUID.randomUUID().toString());
        }

        try {
            return carrierDAO.update(carrier);
        } catch (DataAccessException ex) {
            throw new YmsBusinessException("Database error while updating carrier", ex);
        }
    }

    public Carrier softDeleteCarrier(String scac) {
        try {
            Carrier deleted = carrierDAO.softDeleteByScac(scac);
            if (deleted == null) {
                throw new ResourceNotFoundException("Carrier with scac " + scac + " not found");
            }
            return deleted;
        } catch (DataAccessException ex) {
            throw new YmsBusinessException("Database error while deleting carrier", ex);
        }
    }
}

