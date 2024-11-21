package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Tire;

public interface TireRepository extends JpaRepository<Tire, Long> {

}