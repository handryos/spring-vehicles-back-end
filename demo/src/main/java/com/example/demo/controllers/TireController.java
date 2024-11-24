package com.example.demo.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.models.Tire;
import com.example.demo.services.TireService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/tires")
public class TireController {

    @Autowired
    private TireService tireService;

    @Operation(summary = "Add a Tire", description = "Add a new Tire to the database")
    @ApiResponse(responseCode = "201", description = "Tire successfully created")
    @PostMapping
    public Tire createTire(@RequestBody Tire tire) {
	try {
	    return tireService.create(tire);
	} catch (Exception e) {
	    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating tire", e);
	}
    }

    @Operation(summary = "Remove a Tire by ID", description = "Delete a Tire")
    @ApiResponse(responseCode = "200", description = "Tire successfully deleted")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
	try {
	    tireService.delete(id);
	} catch (IllegalArgumentException e) {
	    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
	} catch (Exception e) {
	    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting tire", e);
	}
    }

    @Operation(summary = "Update a Tire", description = "Update a Tire")
    @ApiResponse(responseCode = "200", description = "Tire successfully updated")
    @PutMapping("/{id}")
    public Tire updateTire(@PathVariable Long id, @RequestBody Tire tire) {
	try {
	    return tireService.update(id, tire);
	} catch (IllegalArgumentException e) {
	    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
	} catch (Exception e) {
	    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating tire", e);
	}
    }

    @Operation(summary = "Get all Tires", description = "Returns a list of Tires")
    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Successfully retrieved list", content = {
		    @Content(mediaType = "application/json", schema = @Schema(implementation = Tire.class)) }),
	    @ApiResponse(responseCode = "404", description = "Tires not found") })
    @GetMapping
    public Iterable<Tire> getTires() {
	return tireService.getTires();
    }

    @Operation(summary = "Get Tire by ID", description = "Returns a specific Tire")
    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Successfully retrieved tire", content = {
		    @Content(mediaType = "application/json", schema = @Schema(implementation = Tire.class)) }),
	    @ApiResponse(responseCode = "404", description = "Tire not found") })
    @GetMapping("/tire/{id}")
    public ResponseEntity<Tire> getTireById(@PathVariable Long id) {
	Optional<Tire> tire = tireService.getTireById(id);
	if (tire.isPresent()) {
	    return ResponseEntity.ok(tire.get());
	} else {
	    return ResponseEntity.notFound().build();
	}
    }
}
