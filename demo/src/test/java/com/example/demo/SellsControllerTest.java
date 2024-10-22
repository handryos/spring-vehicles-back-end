//package com.example.demo;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyList;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import com.example.demo.controllers.SellsController;
//import com.example.demo.models.Product;
//import com.example.demo.models.Sell;
//import com.example.demo.services.SellsServices;
//
//public class SellsControllerTest {
//
//    @InjectMocks
//    private SellsController sellsController;
//
//    @Mock
//    private SellsServices sellsServices;
//
//    @BeforeEach
//    public void setUp() {
//	MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testCreateSell() {
//	Sell newSell = new Sell();
//	when(sellsServices.create(any(Sell.class))).thenReturn(newSell);
//	Sell createdSell = sellsController.createSell();
//	assertNotNull(createdSell);
//	verify(sellsServices, times(1)).create(any(Sell.class));
//    }
//
//    @Test
//    public void testAddItems() {
//	Long id = (long) 1;
//	Product product = new Product();
//	product.setId(1L);
//
//	doNothing().when(sellsServices).addItens(eq(id), anyList());
//
//	sellsController.addItens(id, List.of(product));
//
//	verify(sellsServices, times(1)).addItens(eq(id), anyList());
//    };
//
//    @Test
//    public void testRemoveItems() {
//	Long id = (long) 1;
//	Product product = new Product();
//	product.setId((long) 1);
//
//	doNothing().when(sellsServices).removeItemFromSell(eq(id), eq(product.getId()));
//
//	sellsController.removeItens(id, product);
//
//	verify(sellsServices, times(1)).removeItemFromSell(eq(id), eq(product.getId()));
//    }
//
//    @Test
//    public void testDeleteSell() {
//	Long id = (long) 1;
//
//	doNothing().when(sellsServices).delete(eq(id));
//
//	sellsController.deleteSell(id);
//
//	verify(sellsServices, times(1)).delete(eq(id));
//    }
//
//    @Test
//    public void testGetSells() {
//	@SuppressWarnings("unchecked")
//	Iterable<Sell> sells = mock(Iterable.class);
//	when(sellsServices.getSells()).thenReturn(sells);
//
//	Iterable<Sell> fetchedSells = sellsController.getSells();
//
//	assertNotNull(fetchedSells);
//	verify(sellsServices, times(1)).getSells();
//    }
//
//    @Test
//    public void testGetSellById() {
//	Long id = (long) 1;
//	Sell sell = new Sell();
//	when(sellsServices.getSellById(eq(id))).thenReturn(Optional.of(sell));
//
//	Optional<Sell> fetchedSell = sellsController.getSellById(id);
//
//	assertTrue(fetchedSell.isPresent());
//	assertEquals(sell, fetchedSell.get());
//	verify(sellsServices, times(1)).getSellById(eq(id));
//    }
//}
