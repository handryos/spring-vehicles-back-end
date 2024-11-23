package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Tire;
import com.example.demo.models.TirePosition;
import com.example.demo.models.Vehicle;
import com.example.demo.repository.TirePositionRepository;
import com.example.demo.repository.TireRepository;
import com.example.demo.repository.VehicleRepository;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private TirePositionRepository tirePositionRepository;

    @Autowired
    private TireRepository tireRepository;

    public Vehicle create(Vehicle vehicle) {
	for (TirePosition tirePosition : vehicle.getTire()) {
	    Tire tire = tirePosition.getTire();
	    if (tire.getId() == null) {
		tireRepository.save(tire);
	    }
	    tirePosition.setVehicle(vehicle);
	}
	return vehicleRepository.save(vehicle);
    }

    public Iterable<Vehicle> getVehicles(Boolean getTires) {
	List<Vehicle> vehicles = vehicleRepository.findAll();
	if (!Boolean.TRUE.equals(getTires)) {
	    vehicles.forEach(vehicle -> vehicle.setTire(null));
	}
	return vehicles;
    }

    public Optional<Vehicle> getVehiclesById(Long id) {
	return vehicleRepository.findById(id);
    };

    public void addTireToVehicle(Long vehicleId, List<TirePosition> tirePositions) {

	Optional<Vehicle> existingVehicle = vehicleRepository.findById(vehicleId);
	if (existingVehicle.isEmpty()) {
	    throw new IllegalArgumentException("Veículo com id: " + vehicleId + " não encontrado");
	}

	Vehicle vehicle = existingVehicle.get();

	for (TirePosition tirePosition : tirePositions) {

	    Tire tire = tirePosition.getTire();

	    Optional<Tire> existingTire = tireRepository.findByFireNumber(tire.getFireNumber());
	    if (existingTire.isPresent()) {
		tire = existingTire.get();
	    } else {

		tire = tireRepository.save(tire);
	    }

	    tirePosition.setTire(tire);

	    Optional<TirePosition> existingTirePosition = tirePositionRepository
		    .findByTireAndVehicleAndPosition(tirePosition.getTire(), vehicle, tirePosition.getPosition());
	    if (existingTirePosition.isPresent()) {
		throw new IllegalArgumentException("O pneu de número de fogo " + tire.getFireNumber()
			+ " já está associado à alguma posição, retire-o e tente novamente.");
	    }
	    tirePosition.setVehicle(vehicle);
	    tirePositionRepository.save(tirePosition);
	}

	vehicleRepository.save(vehicle);
    }

    public Vehicle update(Long id, Vehicle updatedVehicle) {

	Optional<Vehicle> existingVehicleOptional = vehicleRepository.findById(id);
	if (existingVehicleOptional.isEmpty()) {
	    throw new RuntimeException("Veículo com id: " + id + " não encontrado.");
	}

	Vehicle existingVehicle = existingVehicleOptional.get();

	existingVehicle.setBrand(updatedVehicle.getBrand());
	existingVehicle.setKm(updatedVehicle.getKm());
	existingVehicle.setStatus(updatedVehicle.getStatus());
	existingVehicle.setPlate(updatedVehicle.getPlate());

	if (updatedVehicle.getTire() != null) {
	    this.addTireToVehicle(id, updatedVehicle.getTire());
	}

	return vehicleRepository.save(existingVehicle);
    }

    public void removeTireFromVehicle(Long vehicleId, Long tireId, String position) {

	Optional<Vehicle> existingVehicle = vehicleRepository.findById(vehicleId);
	if (existingVehicle.isEmpty()) {
	    throw new RuntimeException("Veículo com id:" + vehicleId + " não encontrado.");
	}

	Optional<Tire> existingTire = tireRepository.findById(tireId);

	if (existingTire.isEmpty()) {
	    throw new RuntimeException("Pneu com id:" + tireId + " não encontrado.");
	}

	Vehicle vehicle = existingVehicle.get();
	Tire tire = existingTire.get();

	Optional<TirePosition> existingTirePosition = tirePositionRepository.findByTireAndVehicleAndPosition(tire,
		vehicle, position);

	if (existingTirePosition.isEmpty()) {
	    throw new IllegalArgumentException(
		    "O pneu com id:" + tireId + " não está associado à posição " + position + " neste veículo.");
	}

	TirePosition tirePosition = existingTirePosition.get();

	vehicle.getTire().remove(tirePosition);

	tirePositionRepository.delete(tirePosition);

	vehicleRepository.save(vehicle);
    }

    public void delete(Long id) {
	Optional<Vehicle> existingVehicle = vehicleRepository.findById(id);
	if (existingVehicle.isPresent()) {
	    vehicleRepository.deleteById(id);
	}
    };

}