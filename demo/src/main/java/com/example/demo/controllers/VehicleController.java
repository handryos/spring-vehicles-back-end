package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.TirePosition;
import com.example.demo.models.Vehicle;
import com.example.demo.services.VehicleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class VehicleController {

    @Autowired
    VehicleService vehicleService;

    @Operation(summary = "Add a Vehicle", description = "Add a new Vehicle to the database")
    @ApiResponse(responseCode = "200", description = "Vehicle successfully created")
    @PostMapping("/vehicle")
    public Vehicle createVehicle() {
	Vehicle newVehicle = new Vehicle();
	return vehicleService.create(newVehicle);
    };

    @Operation(summary = "Add tire to Vehicle", description = "Recieve an array of tires")
    @ApiResponse(responseCode = "200", description = "Tires successfully added")
    @PutMapping("/addVehicle")
    public void addVehicle(@RequestParam Long id, @RequestBody List<TirePosition> tire) {
	vehicleService.addTireToVehicle(id, tire);
    }

    @Operation(summary = "Remove a tire by id", description = "Remove a tire by id and position from specific Vehicle")
    @ApiResponse(responseCode = "200", description = "Item successfully removed")
    @PutMapping("/removeItem")
    public void removeItens(@RequestParam Long id, Long tireId, String pos) {
	vehicleService.removeTireFromVehicle(id, tireId, pos);
    }

    @Operation(summary = "Remove a Vehicle by id", description = "Delete Vehicle")
    @ApiResponse(responseCode = "200", description = "Vehicle successfully deleted")
    @DeleteMapping("/vehicle")
    public void delete(@RequestParam Long id) {
	vehicleService.delete(id);
    }

    @Operation(summary = "Get all Vehicles", description = "Returns a list of Vehicles")
    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Successfully retrieved list", content = {
		    @Content(mediaType = "application/json", schema = @Schema(implementation = Vehicle.class)) }),
	    @ApiResponse(responseCode = "404", description = "Vehicles not found") })
    @GetMapping("/vehicle")
    public Iterable<Vehicle> getVehicles() {
	return vehicleService.getVehicles();
    }

    @Operation(summary = "Get Vehicle by ID", description = "Returns a specific Vehicle")
    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of vehicles", content = {
		    @Content(mediaType = "application/json", schema = @Schema(implementation = Vehicle.class)) }),
	    @ApiResponse(responseCode = "404", description = "Vehicles not found") })
    @GetMapping("/vehicles/{id}")
    public Optional<Vehicle> getVehicleById(@PathVariable Long id) {
	return vehicleService.getVehiclesById(id);
    }

}