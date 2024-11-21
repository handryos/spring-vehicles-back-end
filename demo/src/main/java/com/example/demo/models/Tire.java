package com.example.demo.models;

import enums.TireStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tire")
public class Tire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 255)
    private String tireBrand;

    @Column(nullable = false)
    private Float pressure;

    @Column(nullable = false)
    private Integer fireNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TireStatus status;
}
