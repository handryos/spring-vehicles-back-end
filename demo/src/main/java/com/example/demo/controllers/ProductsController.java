package com.example.demo.controllers;

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
import com.example.demo.services.ProductServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class ProductsController {

    @Autowired
    ProductServices productServices;

    @Operation(summary = "Add a sale", description = "Add a new products to the database")
    @ApiResponse(responseCode = "200", description = "Product successfully created")
    @PostMapping("/products")
    public Product create(@RequestBody Product product) {
	return productServices.create(product);
    };

    @Operation(summary = "Add a sale", description = "Update a already existing products to the database")
    @ApiResponse(responseCode = "200", description = "Product successfully updated")
    @PutMapping("/products")
    public void update(@RequestParam Long id, Product product) {
	productServices.update(product, id);
    }

    @Operation(summary = "Add a sale", description = "Delete product from the database")
    @ApiResponse(responseCode = "200", description = "Product successfully deleted")
    @DeleteMapping("/products")
    public void delete(@RequestParam Long id) {
	productServices.delete(id);
    }

    @Operation(summary = "Get all products created", description = "Returns all products")
    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Successfully retrieved list", content = {
		    @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }),
	    @ApiResponse(responseCode = "404", description = "Product not found") })
    @GetMapping("/products")
    public Iterable<Product> getProducts() {
	return productServices.getProducts();
    }

    @Operation(summary = "Get product by ID", description = "Returns a specific product")
    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Successfully retrieved list", content = {
		    @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }),
	    @ApiResponse(responseCode = "404", description = "Product not found") })
    @GetMapping("/products/{id}")
    public Optional<Product> getProductById(@PathVariable Long id) {
	return productServices.getProductById(id);
    }

}