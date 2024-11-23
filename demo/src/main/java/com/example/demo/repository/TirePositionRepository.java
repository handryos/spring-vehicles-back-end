package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Tire;
import com.example.demo.models.TirePosition;
import com.example.demo.models.Vehicle;

public interface TirePositionRepository extends JpaRepository<TirePosition, Long> {
    Optional<TirePosition> findByTireAndVehicleAndPosition(Tire tire, Vehicle vehicle, String position);

    Optional<TirePosition> findByVehicleAndPosition(Vehicle vehicle, String position);

    Optional<TirePosition> findByTire(Tire tire);
}