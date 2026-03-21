package com.smartlogistics.smart_logistics_backend.controller;

import com.smartlogistics.smart_logistics_backend.model.Shipment;
import com.smartlogistics.smart_logistics_backend.repo.ShipmentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/shipments")
@CrossOrigin(origins = "*")
public class ShipmentController {

    private static final Logger logger = LoggerFactory.getLogger(ShipmentController.class);

    private final ShipmentRepository repo;

    public ShipmentController(ShipmentRepository repo) {
        this.repo = repo;
    }

    // ✅ CREATE
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Shipment shipment) {
        try {
            logger.info("Creating shipment with tracking number: {}", shipment.getTrackingNumber());
            
            if (shipment.getStatus() == null || shipment.getStatus().isBlank()) {
                shipment.setStatus("CREATED");
            }

            Shipment saved = repo.save(shipment);
            logger.info("Shipment created successfully with ID: {}", saved.getId());
            return ResponseEntity.ok(saved);

        } catch (Exception e) {
            logger.error("Error creating shipment", e);
            String errorMsg = "Error: " + e.getClass().getSimpleName() + " - " + e.getMessage();
            if (e.getCause() != null) {
                errorMsg += " | Cause: " + e.getCause().getMessage();
            }
            return ResponseEntity.badRequest().body(errorMsg);
        }
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

    // ✅ READ BY TRACKING NUMBER
    @GetMapping("/tracking/{trackingNumber}")
    public ResponseEntity<Shipment> getByTracking(@PathVariable String trackingNumber) {
        return repo.findByTrackingNumber(trackingNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable @NonNull Long id, @RequestBody Shipment updated) {
        try {
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

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NonNull Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}