package com.example.demo.controllers;

import java.util.Optional;

import org.apache.logging.log4j.util.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Tire;
import com.example.demo.services.TireService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class TireController {

    @Autowired
    TireService tireService;

    @Operation(summary = "Add a Tire", description = "Add a new Tire to the database")
    @ApiResponse(responseCode = "200", description = "Tire successfully created")
    @PostMapping("/tire")
    public Tire createTire(@RequestBody Tire tire) {
	try {
	    return tireService.create(tire);
	} catch (Exception e) {
	    throw new InternalException(e.getMessage());
	}

    };

    @Operation(summary = "Remove a Tire by id", description = "Delete Tire")
    @ApiResponse(responseCode = "200", description = "Tire successfully deleted")
    @DeleteMapping("/tire")
    public void delete(@RequestParam Long id) {
	tireService.delete(id);
    }

    @Operation(summary = "Update a Tire", description = "Update Tire")
    @ApiResponse(responseCode = "200", description = "Tire successfully update")
    @PutMapping("/tire")
    public Tire updateTire(@RequestParam Long id, @RequestBody Tire tire) {
	try {
	    return tireService.update(id, tire);
	} catch (Exception e) {
	    throw new InternalException(e.getMessage());
	}

    };

    @Operation(summary = "Get all Tires", description = "Returns a list of Tires")
    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Successfully retrieved list", content = {
		    @Content(mediaType = "application/json", schema = @Schema(implementation = Tire.class)) }),
	    @ApiResponse(responseCode = "404", description = "Tires not found") })

    @GetMapping("/tire")
    public Iterable<Tire> getTires() {
	return tireService.getTires();
    }

    @Operation(summary = "Get Tire by ID", description = "Returns a specific Tire")
    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of tires", content = {
		    @Content(mediaType = "application/json", schema = @Schema(implementation = Tire.class)) }),
	    @ApiResponse(responseCode = "404", description = "Tires not found") })
    @GetMapping("/tire/{id}")
    public Optional<Tire> getTireById(@PathVariable Long id) {
	return tireService.getTiresById(id);
    }

}