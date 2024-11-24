package com.example.demo.controllers;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.models.TirePosition;
import com.example.demo.models.Vehicle;
import com.example.demo.services.VehicleService;

@ExtendWith(MockitoExtension.class)
class VehicleControllerTest {

    @Mock
    private VehicleService vehicleService;

    @InjectMocks
    private VehicleController vehicleController;

    @Test
    @DisplayName("Testa a criação de um veículo")
    void testCreateVehicle() {
	Vehicle mockVehicle = new Vehicle();
	mockVehicle.setId(1L);
	mockVehicle.setBrand("Toyota");

	when(vehicleService.create(any(Vehicle.class))).thenReturn(mockVehicle);

	Vehicle result = vehicleController.createVehicle(mockVehicle);

	assertNotNull(result);
	assertEquals("Toyota", result.getBrand());
	verify(vehicleService, times(1)).create(mockVehicle);
    }

    @Test
    @DisplayName("Testa a adição de pneus a um veículo")
    void testAddTire() {
	List<TirePosition> tirePositions = List.of(new TirePosition(), new TirePosition());

	doNothing().when(vehicleService).addTireToVehicle(any(Long.class), any(List.class));

	vehicleController.addTire(1L, tirePositions);

	verify(vehicleService, times(1)).addTireToVehicle(1L, tirePositions);
    }

    @Test
    @DisplayName("Testa a remoção de um pneu de um veículo")
    void testRemoveTire() {
	Long vehicleId = 1L;
	Long tireId = 2L;
	String position = "front-left";

	doNothing().when(vehicleService).removeTireFromVehicle(vehicleId, tireId, position);

	vehicleController.removeItens(vehicleId, tireId, position);

	verify(vehicleService, times(1)).removeTireFromVehicle(vehicleId, tireId, position);
    }

    @Test
    @DisplayName("Testa a exclusão de um veículo")
    void testDeleteVehicle() {
	Long vehicleId = 1L;

	doNothing().when(vehicleService).delete(vehicleId);

	assertDoesNotThrow(() -> vehicleController.delete(vehicleId));

	verify(vehicleService, times(1)).delete(vehicleId);
    }

    @Test
    @DisplayName("Testa a atualização de um veículo")
    void testUpdateVehicle() {
	Vehicle mockVehicle = new Vehicle();
	mockVehicle.setId(1L);
	mockVehicle.setBrand("Honda");

	when(vehicleService.update(any(Long.class), any(Vehicle.class))).thenReturn(mockVehicle);

	Vehicle result = vehicleController.updateVehicle(1L, mockVehicle);

	assertNotNull(result);
	assertEquals("Honda", result.getBrand());
	verify(vehicleService, times(1)).update(1L, mockVehicle);
    }

    @Test
    @DisplayName("Testa a recuperação de todos os veículos sem pneus")
    void testGetVehiclesWithoutTires() {
	List<Vehicle> mockVehicles = List.of(new Vehicle(), new Vehicle());

	when(vehicleService.getVehicles(Boolean.FALSE)).thenReturn(mockVehicles);

	Iterable<Vehicle> result = vehicleController.getVehicles(Boolean.FALSE);

	assertNotNull(result);
	assertEquals(2, ((List<Vehicle>) result).size());
	verify(vehicleService, times(1)).getVehicles(Boolean.FALSE);
    }

    @Test
    @DisplayName("Testa a recuperação de todos os veículos com pneus")
    void testGetVehiclesWithTires() {
	List<Vehicle> mockVehicles = List.of(new Vehicle(), new Vehicle());

	when(vehicleService.getVehicles(Boolean.TRUE)).thenReturn(mockVehicles);

	Iterable<Vehicle> result = vehicleController.getVehicles(Boolean.TRUE);

	assertNotNull(result);
	assertEquals(2, ((List<Vehicle>) result).size());
	verify(vehicleService, times(1)).getVehicles(Boolean.TRUE);
    }

    @Test
    @DisplayName("Testa a recuperação de um veículo por ID")
    void testGetVehicleById() {
	Vehicle mockVehicle = new Vehicle();
	mockVehicle.setId(1L);
	mockVehicle.setBrand("Honda");

	when(vehicleService.getVehiclesById(1L)).thenReturn(Optional.of(mockVehicle));

	Optional<Vehicle> result = vehicleController.getVehicleById(1L);

	assertTrue(result.isPresent());
	assertEquals("Honda", result.get().getBrand());
	verify(vehicleService, times(1)).getVehiclesById(1L);
    }
}
