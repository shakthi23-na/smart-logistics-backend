package com.smartlogistics.smart_logistics_backend.repo;

import com.smartlogistics.smart_logistics_backend.model.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    Optional<Shipment> findByTrackingNumber(String trackingNumber);
    
}
