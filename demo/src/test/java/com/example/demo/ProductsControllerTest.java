//package com.example.demo;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.Optional;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import com.example.demo.controllers.ProductsController;
//import com.example.demo.models.Product;
//import com.example.demo.services.ProductServices;
//
//public class ProductsControllerTest {
//
//    @InjectMocks
//    private ProductsController productsController;
//
//    @Mock
//    private ProductServices productServices;
//
//    @BeforeEach
//    public void setUp() {
//	MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testCreateProduct() {
//	Product product = new Product();
//	when(productServices.create(any(Product.class))).thenReturn(product);
//
//	Product createdProduct = productsController.createSell(product);
//
//	assertNotNull(createdProduct);
//	verify(productServices, times(1)).create(product);
//    }
//
//    @Test
//    public void testUpdateProduct() {
//	Product product = new Product();
//	Long id = (long) 1;
//
//	doNothing().when(productServices).update(any(Product.class), eq(id));
//
//	productsController.update(id, product);
//
//	verify(productServices, times(1)).update(product, id);
//    }
//
//    @Test
//    public void testDeleteProduct() {
//	Long id = (long) 1;
//
//	doNothing().when(productServices).delete(id);
//
//	productsController.delete(id);
//
//	verify(productServices, times(1)).delete(id);
//    }
//
//    @Test
//    public void testGetProducts() {
//	@SuppressWarnings("unchecked")
//	Iterable<Product> products = mock(Iterable.class);
//	when(productServices.getProducts()).thenReturn(products);
//
//	Iterable<Product> fetchedProducts = productsController.getProducts();
//
//	assertNotNull(fetchedProducts);
//	verify(productServices, times(1)).getProducts();
//    }
//
//    @Test
//    public void testGetProductById() {
//	Long id = (long) 1;
//	Product product = new Product();
//	when(productServices.getProductById(id)).thenReturn(Optional.of(product));
//
//	Optional<Product> fetchedProduct = productsController.getProductById(id);
//
//	assertTrue(fetchedProduct.isPresent());
//	assertEquals(product, fetchedProduct.get());
//	verify(productServices, times(1)).getProductById(id);
//    }
//}
