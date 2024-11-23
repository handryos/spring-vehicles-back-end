package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Tire;

public interface TireRepository extends JpaRepository<Tire, Long> {
    Optional<Tire> findByFireNumber(Integer fireNumber);

}