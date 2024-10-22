package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Product;
import com.example.demo.models.Sale;
import com.example.demo.models.SaleProduct;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.SaleRepository;

@Service
public class SalesServices {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    ProductRepository productRepository;

    public Sale create(Sale sale) {
	return saleRepository.save(sale);
    };

    public void addProductsToSale(Long saleId, List<Product> productIds) {
	Optional<Sale> existingSale = saleRepository.findById(saleId);
	if (existingSale.isPresent()) {
	    Sale sale = existingSale.get();
	    for (Product product : productIds) {
		Optional<Product> actualProduct = productRepository.findById(product.getId());
		actualProduct.ifPresent(p -> {
		    Float currentQuantity = p.getQuantity();
		    Float quantityToDecrease = product.getQuantity();
		    if (currentQuantity >= quantityToDecrease) {
			p.setQuantity(currentQuantity - quantityToDecrease);
		    } else {
			throw new IllegalArgumentException(
				"Insuficient ammout for the product: " + "Name" + p.getName() + "id:" + p.getId());
		    }
		    SaleProduct saleProduct = new SaleProduct();
		    saleProduct.setProduct(p);
		    saleProduct.setSale(sale);
		    saleProduct.setQuantitySold(quantityToDecrease);
		    saleProduct.setPriceAtSale(p.getValue());
		    sale.getSaleProducts().add(saleProduct);

		    productRepository.save(p);
		});
	    }
	    double totalSum = productIds.stream().mapToDouble(productx -> productx.getQuantity() * productx.getValue())
		    .sum();
	    sale.setTotal(totalSum + sale.getTotal());
	    saleRepository.save(sale);
	}
    }

    public void removeItemFromSale(Long saleId, Long productId) {
	Optional<Sale> existingSale = saleRepository.findById(saleId);
	if (existingSale.isPresent()) {
	    Sale sale = existingSale.get();
	    sale.getSaleProducts().removeIf(saleProduct -> saleProduct.getProduct().getId().equals(productId));
	    double updatedTotal = sale.getSaleProducts().stream()
		    .mapToDouble(saleProduct -> saleProduct.getPriceAtSale() * saleProduct.getQuantitySold()).sum();
	    sale.setTotal(updatedTotal);

	    saleRepository.save(sale);
	} else {
	    throw new IllegalArgumentException("Sale not found with ID: " + saleId);
	}
    }

    public void delete(Long id) {
	Optional<Sale> existingSale = saleRepository.findById(id);
	if (existingSale.isPresent()) {
	    saleRepository.deleteById(id);
	}
    };

    public Iterable<Sale> getSales() {
	return saleRepository.findAll();
    };

    public Optional<Sale> getSalesById(Long id) {
	return saleRepository.findById(id);
    };

}