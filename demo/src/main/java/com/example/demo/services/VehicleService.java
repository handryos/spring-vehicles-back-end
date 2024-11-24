package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import com.example.demo.models.TirePosition;
import com.example.demo.models.Vehicle;

public interface VehicleService {
    Vehicle create(Vehicle vehicle);

    Iterable<Vehicle> getVehicles(Boolean getTires);

    Optional<Vehicle> getVehiclesById(Long id);

    void addTireToVehicle(Long vehicleId, List<TirePosition> tirePositions);

    Vehicle update(Long id, Vehicle updatedVehicle);

    void removeTireFromVehicle(Long vehicleId, Long tireId, String position);

    void delete(Long id);
}
