package com.example.demo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.implementations.VehicleServiceImplementation;
import com.example.demo.models.Tire;
import com.example.demo.models.TirePosition;
import com.example.demo.models.Vehicle;
import com.example.demo.repository.TirePositionRepository;
import com.example.demo.repository.TireRepository;
import com.example.demo.repository.VehicleRepository;

class VehicleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private TireRepository tireRepository;

    @Mock
    private TirePositionRepository tirePositionRepository;

    @InjectMocks
    private VehicleServiceImplementation vehicleService;

    @Captor
    private ArgumentCaptor<Vehicle> vehicleCaptor;

    @BeforeEach
    void setUp() {
	MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testa a criação de um veículo")
    void testCreateVehicle() {
	Vehicle mockVehicle = new Vehicle();
	mockVehicle.setId(1L);
	mockVehicle.setBrand("Toyota");
	mockVehicle.setPlate("ABC1234");
	mockVehicle.setTire(new ArrayList<>());

	when(vehicleRepository.save(any(Vehicle.class))).thenReturn(mockVehicle);

	Vehicle result = vehicleService.create(mockVehicle);

	assertNotNull(result);
	assertEquals("Toyota", result.getBrand());
	assertEquals("ABC1234", result.getPlate());
	assertNotNull(result.getTire());
	verify(vehicleRepository, times(1)).save(mockVehicle);
    }

    @Test
    @DisplayName("Testa a adição de um pneu a um veículo")
    void testAddTireToVehicle() {
	Long vehicleId = 1L;
	Vehicle vehicle = new Vehicle();
	vehicle.setId(vehicleId);
	vehicle.setTire(new ArrayList<>());

	Tire tire = new Tire();
	tire.setId(1L);
	tire.setFireNumber(123);

	TirePosition tirePosition = new TirePosition();
	tirePosition.setPosition("FRONT_LEFT");
	tirePosition.setTire(tire);
	tirePosition.setVehicle(vehicle);

	when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(vehicle));
	when(tireRepository.findByFireNumber(123)).thenReturn(Optional.of(tire));
	when(tirePositionRepository.findByTireAndVehicleAndPosition(any(), any(), anyString()))
		.thenReturn(Optional.empty());

	vehicleService.addTireToVehicle(vehicleId, List.of(tirePosition));

	verify(vehicleRepository, times(1)).save(vehicle);
	verify(tirePositionRepository, times(1)).save(any(TirePosition.class));
    }

    @Test
    @DisplayName("Testa a remoção de um pneu de um veículo")
    void testRemoveTireFromVehicle() {
	Long vehicleId = 1L;
	Long tireId = 1L;

	Vehicle vehicle = new Vehicle();
	vehicle.setId(vehicleId);
	vehicle.setTire(new ArrayList<>());
	Tire tire = new Tire();
	tire.setId(tireId);

	TirePosition tirePosition = new TirePosition();
	tirePosition.setVehicle(vehicle);
	tirePosition.setTire(tire);
	tirePosition.setPosition("FRONT_LEFT");
	vehicle.getTire().add(tirePosition);

	when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(vehicle));
	when(tireRepository.findById(tireId)).thenReturn(Optional.of(tire));
	when(tirePositionRepository.findByTireAndVehicleAndPosition(any(), any(), anyString()))
		.thenReturn(Optional.of(tirePosition));

	vehicleService.removeTireFromVehicle(vehicleId, tireId, "FRONT_LEFT");

	verify(tirePositionRepository, times(1)).delete(any(TirePosition.class));
	verify(vehicleRepository, times(1)).save(vehicle);
    }

    @Test
    void testUpdateVehicle() {
	Vehicle existingVehicle = new Vehicle();
	existingVehicle.setId(1L);
	existingVehicle.setBrand("Old Brand");

	Vehicle updatedVehicle = new Vehicle();
	updatedVehicle.setId(1L);
	updatedVehicle.setBrand("New Brand");

	when(vehicleRepository.findById(1L)).thenReturn(Optional.of(existingVehicle));
	when(vehicleRepository.save(any(Vehicle.class))).thenReturn(updatedVehicle);

	verify(vehicleRepository, times(1)).save(vehicleCaptor.capture());

	Vehicle savedVehicle = vehicleCaptor.getValue();
	assertNotNull(savedVehicle);
	assertEquals("New Brand", savedVehicle.getBrand());
	assertEquals(1L, savedVehicle.getId());
    }

    @Test
    @DisplayName("Testa a remoção de um veículo")
    void testDeleteVehicle() {
	Long vehicleId = 1L;

	Vehicle vehicle = new Vehicle();
	vehicle.setId(vehicleId);

	when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(vehicle));

	vehicleService.delete(vehicleId);

	verify(vehicleRepository, times(1)).deleteById(vehicleId);
    }

    @Test
    @DisplayName("Testa a criação de um veículo já existente")
    void testCreateVehicleAlreadyExists() {
	Vehicle mockVehicle = new Vehicle();
	mockVehicle.setId(1L);
	mockVehicle.setBrand("Toyota");
	mockVehicle.setPlate("ABC1234");

	when(vehicleRepository.findByPlate(anyString())).thenReturn(Optional.of(mockVehicle));

	IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
	    vehicleService.create(mockVehicle);
	});

	assertEquals("Veículo já cadastrado, verifique!", thrown.getMessage());
    }
}
