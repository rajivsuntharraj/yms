package com.application.yms.controller;

import com.application.yms.model.Carrier;
import com.application.yms.model.Shipment;
import com.application.yms.service.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shipments")
@RequiredArgsConstructor
public class ShipmentController {
    private final ShipmentService shipmentService;
    @PostMapping
    public ResponseEntity<Shipment> createShipment(@RequestBody Shipment shipment){
        Shipment created = shipmentService.createShipment(shipment);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);

    }
    @GetMapping("/{shipmentNo}")
    public ResponseEntity<Shipment> fetchShipment(@PathVariable String shipmentNo){
        Shipment shipment = shipmentService.getShipmentByShipmentNo(shipmentNo);
        return ResponseEntity.ok(shipment);

    }
}
