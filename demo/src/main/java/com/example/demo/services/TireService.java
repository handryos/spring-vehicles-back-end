package com.example.demo.services;

import java.util.Optional;

import com.example.demo.models.Tire;

public interface TireService {
    Tire create(Tire tire);

    Iterable<Tire> getTires();

    Optional<Tire> getTireById(Long id);

    Tire update(Long id, Tire updatedTire);

    void delete(Long id);
}
