package com.example.demo.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import com.example.demo.enums.TireStatus;
import com.example.demo.implementations.TireServiceImplementation;
import com.example.demo.models.Tire;
import com.example.demo.models.TirePosition;
import com.example.demo.models.Vehicle;
import com.example.demo.repository.TirePositionRepository;
import com.example.demo.repository.TireRepository;

class TireServiceTest {

    @Mock
    private TireRepository tireRepository;

    @Mock
    private TirePositionRepository tirePositionRepository;

    @InjectMocks
    private TireServiceImplementation tireService;

    @Captor
    private ArgumentCaptor<Tire> tireCaptor;

    @BeforeEach
    void setUp() {
	MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testa a criação de um novo pneu com status NEW")
    void testCreateTire() {
	Tire tire = new Tire();
	tire.setStatus(TireStatus.NEW);

	when(tireRepository.save(any(Tire.class))).thenReturn(tire);

	Tire createdTire = tireService.create(tire);

	assertNotNull(createdTire);
	assertEquals(TireStatus.NEW, createdTire.getStatus());
	verify(tireRepository, times(1)).save(tire);
    }

    @Test
    @DisplayName("Testa a recuperação de todos os pneus")
    void testGetTires() {
	Tire tire1 = new Tire();
	Tire tire2 = new Tire();
	when(tireRepository.findAll()).thenReturn(List.of(tire1, tire2));

	Iterable<Tire> tires = tireService.getTires();

	assertNotNull(tires);
	assertTrue(tires.spliterator().getExactSizeIfKnown() == 2);
	verify(tireRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Testa a recuperação de um pneu pelo ID")
    void testGetTiresById() {
	Tire tire = new Tire();
	tire.setId(1L);

	when(tireRepository.findById(1L)).thenReturn(Optional.of(tire));

	Optional<Tire> foundTire = tireService.getTireById(1L);

	assertTrue(foundTire.isPresent());
	assertEquals(1L, foundTire.get().getId());
	verify(tireRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Testa a atualização de um pneu")
    void testUpdateTire() {
	Tire existingTire = new Tire();
	existingTire.setId(1L);
	existingTire.setPressure((float) 30.0);
	existingTire.setStatus(TireStatus.NEW);

	Tire updatedTire = new Tire();
	updatedTire.setPressure((float) 32.0);
	updatedTire.setStatus(TireStatus.USED);

	when(tireRepository.findById(1L)).thenReturn(Optional.of(existingTire));
	when(tireRepository.save(any(Tire.class))).thenReturn(existingTire);

	Tire updatedTireResult = tireService.update(1L, updatedTire);

	assertNotNull(updatedTireResult);
	assertEquals((float) 32.0, updatedTireResult.getPressure());
	assertEquals(TireStatus.USED, updatedTireResult.getStatus());

	verify(tireRepository, times(1)).save(tireCaptor.capture());

	Tire capturedTire = tireCaptor.getValue();

	assertEquals((float) 32.0, capturedTire.getPressure());
	assertEquals(TireStatus.USED, capturedTire.getStatus());
	assertEquals(1L, capturedTire.getId());
    }

    @Test
    @DisplayName("Testa a exclusão de um pneu sem associações a veículos")
    void testDeleteTire() {
	Tire tire = new Tire();
	tire.setId(1L);

	when(tireRepository.findById(1L)).thenReturn(Optional.of(tire));
	when(tirePositionRepository.findByTire(tire)).thenReturn(Optional.empty());

	doNothing().when(tireRepository).deleteById(1L);

	assertDoesNotThrow(() -> tireService.delete(1L));
	verify(tireRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Testa a tentativa de exclusão de um pneu com associações a um veículo")
    void testDeleteTireWithTirePosition() {
	Tire tire = new Tire();
	tire.setId(1L);
	TirePosition tirePosition = new TirePosition();
	tirePosition.setVehicle(new Vehicle());

	when(tireRepository.findById(1L)).thenReturn(Optional.of(tire));
	when(tirePositionRepository.findByTire(tire)).thenReturn(Optional.of(tirePosition));

	IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> tireService.delete(1L));

	assertEquals(
		"O pneu com id: 1 está associado ao veículo com id: null e não pode ser excluído. Remova-o do veículo e tente novamente.",
		exception.getMessage());
	verify(tireRepository, times(0)).deleteById(1L);
    }
}
