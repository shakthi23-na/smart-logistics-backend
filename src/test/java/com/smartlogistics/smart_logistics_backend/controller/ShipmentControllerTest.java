package com.smartlogistics.smart_logistics_backend.controller;

import com.smartlogistics.smart_logistics_backend.model.Shipment;
import com.smartlogistics.smart_logistics_backend.repo.ShipmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings("null")
@WebMvcTest(ShipmentController.class)
class ShipmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ShipmentRepository shipmentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Shipment testShipment;

    @BeforeEach
    void setUp() {
        testShipment = new Shipment();
        testShipment.setId(1L);
        testShipment.setTrackingNumber("TRACK123");
        testShipment.setStatus("CREATED");
        testShipment.setSenderName("John Doe");
        testShipment.setReceiverName("Jane Smith");
        testShipment.setPickupCity("New York");
        testShipment.setDropCity("Los Angeles");
        testShipment.setVehicleNumber("VH123");
        testShipment.setExpectedDeliveryDate(LocalDate.now().plusDays(5));
        testShipment.setCreatedAt(LocalDateTime.now());
        testShipment.setUpdatedAt(LocalDateTime.now());
    }

    // ✅ CREATE TESTS
    @Test
    void testCreateShipmentSuccess() throws Exception {
        Shipment newShipment = new Shipment();
        newShipment.setTrackingNumber("TRACK456");
        newShipment.setSenderName("Alice");
        newShipment.setReceiverName("Bob");

        when(shipmentRepository.save(any())).thenReturn(testShipment);

        mockMvc.perform(post("/api/shipments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newShipment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.trackingNumber", is("TRACK123")))
                .andExpect(jsonPath("$.status", is("CREATED")));

        verify(shipmentRepository, times(1)).save(any());
    }

    @Test
    void testCreateShipmentWithNullStatusSetsDefault() throws Exception {
        Shipment shipmentWithoutStatus = new Shipment();
        shipmentWithoutStatus.setTrackingNumber("TRACK789");
        shipmentWithoutStatus.setStatus(null);

        when(shipmentRepository.save(any())).thenReturn(testShipment);

        mockMvc.perform(post("/api/shipments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(shipmentWithoutStatus)))
                .andExpect(status().isOk());

        verify(shipmentRepository, times(1)).save(any());
    }

    @Test
    void testCreateShipmentWithBlankStatusSetsDefault() throws Exception {
        Shipment shipmentWithBlankStatus = new Shipment();
        shipmentWithBlankStatus.setTrackingNumber("TRACK999");
        shipmentWithBlankStatus.setStatus("   ");

        when(shipmentRepository.save(any())).thenReturn(testShipment);

        mockMvc.perform(post("/api/shipments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(shipmentWithBlankStatus)))
                .andExpect(status().isOk());

        verify(shipmentRepository, times(1)).save(any());
    }

    // ✅ READ ALL TESTS
    @Test
    void testGetAllShipments() throws Exception {
        List<Shipment> shipments = Arrays.asList(testShipment);
        when(shipmentRepository.findAll()).thenReturn(shipments);

        mockMvc.perform(get("/api/shipments")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].trackingNumber", is("TRACK123")));

        verify(shipmentRepository, times(1)).findAll();
    }

    @Test
    void testGetAllShipmentsEmpty() throws Exception {
        when(shipmentRepository.findAll()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/shipments")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(shipmentRepository, times(1)).findAll();
    }

    // ✅ READ ONE BY ID TESTS
    @Test
    void testGetShipmentById() throws Exception {
        when(shipmentRepository.findById(1L)).thenReturn(Optional.of(testShipment));

        mockMvc.perform(get("/api/shipments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.trackingNumber", is("TRACK123")));

        verify(shipmentRepository, times(1)).findById(1L);
    }

    @Test
    void testGetShipmentByIdNotFound() throws Exception {
        when(shipmentRepository.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/shipments/999"))
                .andExpect(status().isNotFound());

        verify(shipmentRepository, times(1)).findById(999L);
    }

    // ✅ READ BY TRACKING NUMBER TESTS
    @Test
    void testGetShipmentByTrackingNumber() throws Exception {
        when(shipmentRepository.findByTrackingNumber("TRACK123")).thenReturn(Optional.of(testShipment));

        mockMvc.perform(get("/api/shipments/tracking/TRACK123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.trackingNumber", is("TRACK123")))
                .andExpect(jsonPath("$.senderName", is("John Doe")));

        verify(shipmentRepository, times(1)).findByTrackingNumber("TRACK123");
    }

    @Test
    void testGetShipmentByTrackingNumberNotFound() throws Exception {
        when(shipmentRepository.findByTrackingNumber("INVALID")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/shipments/tracking/INVALID"))
                .andExpect(status().isNotFound());

        verify(shipmentRepository, times(1)).findByTrackingNumber("INVALID");
    }

    // ✅ UPDATE TESTS
    @Test
    void testUpdateShipmentSuccess() throws Exception {
        Shipment updatedShipment = new Shipment();
        updatedShipment.setTrackingNumber("TRACK123_UPDATED");
        updatedShipment.setSenderName("Updated Sender");
        updatedShipment.setReceiverName("Updated Receiver");
        updatedShipment.setPickupCity("Chicago");
        updatedShipment.setDropCity("Miami");
        updatedShipment.setStatus("IN_TRANSIT");
        updatedShipment.setVehicleNumber("VH456");
        updatedShipment.setExpectedDeliveryDate(LocalDate.now().plusDays(3));

        when(shipmentRepository.findById(1L)).thenReturn(Optional.of(testShipment));
        when(shipmentRepository.save(any())).thenReturn(testShipment);

        mockMvc.perform(put("/api/shipments/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedShipment)))
                .andExpect(status().isOk());

        verify(shipmentRepository, times(1)).findById(1L);
        verify(shipmentRepository, times(1)).save(any());
    }

    @Test
    void testUpdateShipmentNotFound() throws Exception {
        Shipment updatedShipment = new Shipment();
        updatedShipment.setTrackingNumber("TRACK123_UPDATED");

        when(shipmentRepository.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/shipments/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedShipment)))
                .andExpect(status().isNotFound());

        verify(shipmentRepository, times(1)).findById(999L);
        verify(shipmentRepository, never()).save(any());
    }

    // ✅ DELETE TESTS
    @Test
    void testDeleteShipmentSuccess() throws Exception {
        when(shipmentRepository.existsById(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/shipments/1"))
                .andExpect(status().isNoContent());

        verify(shipmentRepository, times(1)).existsById(1L);
        verify(shipmentRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteShipmentNotFound() throws Exception {
        when(shipmentRepository.existsById(999L)).thenReturn(false);

        mockMvc.perform(delete("/api/shipments/999"))
                .andExpect(status().isNotFound());

        verify(shipmentRepository, times(1)).existsById(999L);
        verify(shipmentRepository, never()).deleteById(any());
    }
}
