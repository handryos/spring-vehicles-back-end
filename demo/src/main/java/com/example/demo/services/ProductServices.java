package com.example.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Product;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductServices {

    @Autowired
    private ProductRepository productRepository;

    public Product create(Product product) {
	return productRepository.save(product);
    };

    public void update(Product newProduct, Long id) {
	Product product = productRepository.findById(id)
		.orElseThrow(() -> new RuntimeException("Product not found: " + id));
	product.setName(newProduct.getName());
	product.setQuantity(newProduct.getQuantity());
	product.setValue(newProduct.getValue());
	productRepository.save(product);
    };

    public void delete(Long id) {
	productRepository.deleteById(id);

    };

    public Iterable<Product> getProducts() {
	return productRepository.findAll();
    };

    public Optional<Product> getProductById(Long id) {
	return productRepository.findById(id);
    };

}