package com.example.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Tire;
import com.example.demo.models.TirePosition;
import com.example.demo.repository.TirePositionRepository;
import com.example.demo.repository.TireRepository;

@Service
public class TireService {

    @Autowired
    private TirePositionRepository tirePositionRepository;

    @Autowired
    private TireRepository tireRepository;

    public Tire create(Tire tire) {
	return tireRepository.save(tire);
    }

    public Iterable<Tire> getTires() {
	return tireRepository.findAll();
    }

    public Optional<Tire> getTiresById(Long id) {
	return tireRepository.findById(id);
    };

    public Tire update(Long id, Tire updatedTire) {

	Optional<Tire> existingTireOptional = tireRepository.findById(id);
	if (existingTireOptional.isEmpty()) {
	    throw new RuntimeException("Pneu com id: " + id + " não encontrado.");
	}

	Tire existingTire = existingTireOptional.get();

	existingTire.setPressure(updatedTire.getPressure());
	existingTire.setStatus(updatedTire.getStatus());

	return tireRepository.save(existingTire);
    }

    public void delete(Long id) {
	Optional<Tire> existingTire = tireRepository.findById(id);
	if (existingTire.isEmpty()) {
	    throw new RuntimeException("Pneu com id: " + id + " não encontrado.");
	}

	Tire tire = existingTire.get();

	Optional<TirePosition> tirePosition = tirePositionRepository.findByTire(tire);
	if (tirePosition.isPresent()) {
	    Long vehicleId = tirePosition.get().getVehicle().getId();
	    throw new IllegalArgumentException("O pneu com id: " + id + " está associado ao veículo com id: "
		    + vehicleId + " e não pode ser excluído. Remova-o do veículo e tente novamente.");
	}
	tireRepository.deleteById(id);
    }

}