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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.example.demo.models.Tire;
import com.example.demo.services.TireService;

class TireControllerTest {

    @Mock
    private TireService tireService;

    @InjectMocks
    private TireController tireController;

    public TireControllerTest() {
	MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testa a criação de um pneu")
    void testCreateTire() {
	Tire mockTire = new Tire();
	mockTire.setId(1L);
	mockTire.setFireNumber(3333);

	when(tireService.create(any(Tire.class))).thenReturn(mockTire);

	Tire result = tireController.createTire(mockTire);

	assertNotNull(result);
	assertEquals(3333, result.getFireNumber());
	verify(tireService, times(1)).create(mockTire);
    }

    @Test
    @DisplayName("Testa a exclusão de um pneu")
    void testDeleteTire() {
	Long id = 1L;

	doNothing().when(tireService).delete(id);

	assertDoesNotThrow(() -> tireController.delete(id));

	verify(tireService, times(1)).delete(id);
    }

    @Test
    @DisplayName("Testa a recuperação de todos os pneus")
    void testGetTires() {
	List<Tire> mockTires = List.of(new Tire(), new Tire());
	when(tireService.getTires()).thenReturn(mockTires);

	Iterable<Tire> result = tireController.getTires();

	assertNotNull(result);
	assertEquals(2, ((List<Tire>) result).size());
	verify(tireService, times(1)).getTires();
    }

    @Test
    @DisplayName("Testa a recuperação de um pneu por ID")
    void testGetTireById() {
	Tire mockTire = new Tire();
	mockTire.setId(1L);
	mockTire.setFireNumber(99922);

	when(tireService.getTireById(1L)).thenReturn(Optional.of(mockTire));

	ResponseEntity<Tire> result = tireController.getTireById(1L);

	assertTrue(result.getStatusCode().is2xxSuccessful());
	assertEquals(99922, result.getBody().getFireNumber());
	verify(tireService, times(1)).getTireById(1L);
    }
}
