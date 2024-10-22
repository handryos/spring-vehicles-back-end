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

import com.example.demo.models.Product;
import com.example.demo.models.Sale;
import com.example.demo.services.SalesServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class SalesController {

    @Autowired
    SalesServices salesService;

    @Operation(summary = "Add a sale", description = "Add a new sale to the database, is recommended to always create a new sell before add itens")
    @ApiResponse(responseCode = "200", description = "Sale successfully created")
    @PostMapping("/newSale")
    public Sale createSale() {
	Sale newSale = new Sale();
	return salesService.create(newSale);
    };

    @Operation(summary = "Add itens to some sale", description = "Recieve an array of products")
    @ApiResponse(responseCode = "200", description = "Itens successfully added")
    @PutMapping("/addItens")
    public void addItens(@RequestParam Long id, @RequestBody List<Product> products) {
	salesService.addProductsToSale(id, products);
    }

    @Operation(summary = "Remove a item by id", description = "Remove a item by id from specific sale")
    @ApiResponse(responseCode = "200", description = "Item successfully removed")
    @PutMapping("/removeItem")
    public void removeItens(@RequestParam Long id, Product products) {
	salesService.removeItemFromSale(id, products.getId());
    }

    @Operation(summary = "Remove a sale by id", description = "Remove a sale by id from specific sale")
    @ApiResponse(responseCode = "200", description = "Sale successfully deleted")
    @DeleteMapping("/sales")
    public void deleteSell(@RequestParam Long id) {
	salesService.delete(id);
    }

    @Operation(summary = "Get all sales", description = "Returns a list of sales")
    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Successfully retrieved list", content = {
		    @Content(mediaType = "application/json", schema = @Schema(implementation = Sale.class)) }),
	    @ApiResponse(responseCode = "404", description = "Sales not found") })
    @GetMapping("/sales")
    public Iterable<Sale> getSales() {
	return salesService.getSales();
    }

    @Operation(summary = "Get sale by ID", description = "Returns a specific sale")
    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Successfully retrieved list", content = {
		    @Content(mediaType = "application/json", schema = @Schema(implementation = Sale.class)) }),
	    @ApiResponse(responseCode = "404", description = "Sales not found") })
    @GetMapping("/sales/{id}")
    public Optional<Sale> getSaleById(@PathVariable Long id) {
	return salesService.getSalesById(id);
    }

}