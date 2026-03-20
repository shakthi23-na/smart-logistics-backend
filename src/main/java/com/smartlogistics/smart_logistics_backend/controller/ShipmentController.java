package com.smartlogistics.smart_logistics_backend.controller;

import com.smartlogistics.smart_logistics_backend.model.Shipment;
import com.smartlogistics.smart_logistics_backend.repo.ShipmentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {

    private final ShipmentRepository repo;

    public ShipmentController(ShipmentRepository repo) {
        this.repo = repo;
    }

    // ✅ CREATE
    @PostMapping
    public ResponseEntity<Shipment> create(@RequestBody Shipment shipment) {
        if (shipment.getStatus() == null || shipment.getStatus().isBlank()) {
            shipment.setStatus("CREATED");
        }
        return ResponseEntity.ok(repo.save(shipment));
    }

    // ✅ READ ALL
    @GetMapping
    public ResponseEntity<List<Shipment>> getAll() {
        return ResponseEntity.ok(repo.findAll());
    }

    // ✅ READ ONE BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Shipment> getOne(@PathVariable @NonNull Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ READ ONE BY TRACKING NUMBER (smart search)
    @GetMapping("/tracking/{trackingNumber}")
    public ResponseEntity<Shipment> getByTracking(@PathVariable String trackingNumber) {
        return repo.findByTrackingNumber(trackingNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Shipment> update(@PathVariable @NonNull Long id, @RequestBody Shipment updated) {
        return repo.findById(id).map(existing -> {
            existing.setTrackingNumber(updated.getTrackingNumber());
            existing.setSenderName(updated.getSenderName());
            existing.setReceiverName(updated.getReceiverName());
            existing.setPickupCity(updated.getPickupCity());
            existing.setDropCity(updated.getDropCity());
            existing.setStatus(updated.getStatus());
            existing.setVehicleNumber(updated.getVehicleNumber());
            existing.setExpectedDeliveryDate(updated.getExpectedDeliveryDate());
            return ResponseEntity.ok(repo.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NonNull Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
