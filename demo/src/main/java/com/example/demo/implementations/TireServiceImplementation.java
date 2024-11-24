package com.example.demo.implementations;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Tire;
import com.example.demo.repository.TirePositionRepository;
import com.example.demo.repository.TireRepository;
import com.example.demo.services.TireService;

@Service
public class TireServiceImplementation implements TireService {

    @Autowired
    private TirePositionRepository tirePositionRepository;

    @Autowired
    private TireRepository tireRepository;

    @Override
    public Tire create(Tire tire) {
	return tireRepository.save(tire);
    }

    @Override
    public Iterable<Tire> getTires() {
	return tireRepository.findAll();
    }

    @Override
    public Optional<Tire> getTireById(Long id) {
	return tireRepository.findById(id);
    }

    @Override
    public Tire update(Long id, Tire updatedTire) {
	Tire existingTire = tireRepository.findById(id)
		.orElseThrow(() -> new RuntimeException("Pneu com id: " + id + " não encontrado."));

	existingTire.setPressure(updatedTire.getPressure());
	existingTire.setStatus(updatedTire.getStatus());

	return tireRepository.save(existingTire);
    }

    @Override
    public void delete(Long id) {
	Tire tire = tireRepository.findById(id)
		.orElseThrow(() -> new RuntimeException("Pneu com id: " + id + " não encontrado."));

	if (tirePositionRepository.findByTire(tire).isPresent()) {
	    Long vehicleId = tirePositionRepository.findByTire(tire).get().getVehicle().getId();
	    throw new IllegalArgumentException("O pneu com id: " + id + " está associado ao veículo com id: "
		    + vehicleId + " e não pode ser excluído. Remova-o do veículo e tente novamente.");
	}

	tireRepository.deleteById(id);
    }
}
