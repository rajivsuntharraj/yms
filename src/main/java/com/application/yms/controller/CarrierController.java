package com.application.yms.controller;

import com.application.yms.model.Carrier;
import com.application.yms.service.CarrierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carriers")
@RequiredArgsConstructor
public class CarrierController {

    private final CarrierService carrierService;

    @PostMapping
    public ResponseEntity<Carrier> createCarrier(@RequestBody Carrier carrier) {
        Carrier created = carrierService.createCarrier(carrier);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{scac}")
    public ResponseEntity<Carrier> getCarrierByScac(@PathVariable String scac) {
        Carrier carrier = carrierService.getCarrierByScac(scac);
        return ResponseEntity.ok(carrier);
    }

    @PutMapping("/{scac}")
    public ResponseEntity<Carrier> updateCarrier(@PathVariable String scac, @RequestBody Carrier carrier) {
        Carrier updated = carrierService.updateCarrier(scac, carrier);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{scac}")
    public ResponseEntity<Carrier> softDeleteCarrier(@PathVariable String scac) {
        Carrier deleted = carrierService.softDeleteCarrier(scac);
        return ResponseEntity.ok(deleted);
    }
}

