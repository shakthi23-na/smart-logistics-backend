package com.smartlogistics.smart_logistics_backend.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ShipmentTest {

    @Test
    void testShipmentCreationWithAllArgs() {
        LocalDateTime now = LocalDateTime.now();
        LocalDate expectedDate = LocalDate.now().plusDays(5);
        Shipment shipment = new Shipment(1L, "TRACK123", "CREATED", 
            "John Doe", "Jane Smith", "New York", "Los Angeles", 
            "VH123", expectedDate, now, now);

        assertEquals(1L, shipment.getId());
        assertEquals("TRACK123", shipment.getTrackingNumber());
        assertEquals("CREATED", shipment.getStatus());
        assertEquals("John Doe", shipment.getSenderName());
        assertEquals("Jane Smith", shipment.getReceiverName());
        assertEquals("New York", shipment.getPickupCity());
        assertEquals("Los Angeles", shipment.getDropCity());
        assertEquals("VH123", shipment.getVehicleNumber());
        assertNotNull(shipment.getExpectedDeliveryDate());
        assertNotNull(shipment.getCreatedAt());
        assertNotNull(shipment.getUpdatedAt());
    }

    @Test
    void testShipmentCreationWithNoArgs() {
        Shipment shipment = new Shipment();

        assertNull(shipment.getId());
        assertNull(shipment.getTrackingNumber());
        assertNull(shipment.getStatus());
        assertNull(shipment.getSenderName());
        assertNull(shipment.getReceiverName());
        assertNull(shipment.getPickupCity());
        assertNull(shipment.getDropCity());
        assertNull(shipment.getVehicleNumber());
        assertNull(shipment.getExpectedDeliveryDate());
        assertNull(shipment.getCreatedAt());
        assertNull(shipment.getUpdatedAt());
    }

    @Test
    void testShipmentSettersAndGetters() {
        Shipment shipment = new Shipment();
        LocalDateTime now = LocalDateTime.now();
        LocalDate expectedDate = LocalDate.now();

        shipment.setId(5L);
        shipment.setTrackingNumber("TRACK456");
        shipment.setStatus("IN_TRANSIT");
        shipment.setSenderName("Alice");
        shipment.setReceiverName("Bob");
        shipment.setPickupCity("Chicago");
        shipment.setDropCity("Miami");
        shipment.setVehicleNumber("VH456");
        shipment.setExpectedDeliveryDate(expectedDate);
        shipment.setCreatedAt(now);
        shipment.setUpdatedAt(now);

        assertEquals(5L, shipment.getId());
        assertEquals("TRACK456", shipment.getTrackingNumber());
        assertEquals("IN_TRANSIT", shipment.getStatus());
        assertEquals("Alice", shipment.getSenderName());
        assertEquals("Bob", shipment.getReceiverName());
        assertEquals("Chicago", shipment.getPickupCity());
        assertEquals("Miami", shipment.getDropCity());
        assertEquals("VH456", shipment.getVehicleNumber());
        assertEquals(expectedDate, shipment.getExpectedDeliveryDate());
        assertEquals(now, shipment.getCreatedAt());
        assertEquals(now, shipment.getUpdatedAt());
    }

    @Test
    void testOnCreateSetsTimestamps() {
        Shipment shipment = new Shipment();
        LocalDateTime beforeCreate = LocalDateTime.now();
        
        shipment.onCreate();
        
        LocalDateTime afterCreate = LocalDateTime.now();
        assertNotNull(shipment.getCreatedAt());
        assertNotNull(shipment.getUpdatedAt());
        assertTrue(shipment.getCreatedAt().isAfter(beforeCreate.minusSeconds(1)));
        assertTrue(shipment.getCreatedAt().isBefore(afterCreate.plusSeconds(1)));
        // Check both timestamps are within 10ms of each other (not exact equality)
        long timeDiff = java.time.temporal.ChronoUnit.MILLIS.between(
            shipment.getCreatedAt(), 
            shipment.getUpdatedAt()
        );
        assertTrue(Math.abs(timeDiff) <= 10, "CreatedAt and UpdatedAt should be within 10ms of each other");
    }

    @Test
    void testOnUpdateUpdatesTimestamp() {
        Shipment shipment = new Shipment();
        LocalDateTime originalCreatedAt = LocalDateTime.now().minusHours(1);
        shipment.setCreatedAt(originalCreatedAt);
        
        LocalDateTime beforeUpdate = LocalDateTime.now();
        shipment.onUpdate();
        LocalDateTime afterUpdate = LocalDateTime.now();
        
        assertEquals(originalCreatedAt, shipment.getCreatedAt());
        assertNotNull(shipment.getUpdatedAt());
        assertTrue(shipment.getUpdatedAt().isAfter(beforeUpdate.minusSeconds(1)));
        assertTrue(shipment.getUpdatedAt().isBefore(afterUpdate.plusSeconds(1)));
    }

    @Test
    void testShipmentEquality() {
        LocalDateTime now = LocalDateTime.now();
        LocalDate expectedDate = LocalDate.now();
        Shipment shipment1 = new Shipment(1L, "TRACK123", "CREATED", 
            "John Doe", "Jane Smith", "New York", "Los Angeles", 
            "VH123", expectedDate, now, now);
        
        Shipment shipment2 = new Shipment(1L, "TRACK123", "CREATED", 
            "John Doe", "Jane Smith", "New York", "Los Angeles", 
            "VH123", expectedDate, now, now);
        
        assertEquals(shipment1, shipment2);
    }

    @Test
    void testShipmentToString() {
        Shipment shipment = new Shipment();
        shipment.setId(1L);
        shipment.setTrackingNumber("TRACK123");
        
        String toString = shipment.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("Shipment") || toString.contains("1"));
    }
}
