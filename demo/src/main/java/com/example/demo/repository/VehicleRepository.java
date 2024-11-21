package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

}