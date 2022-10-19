package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;

import estoreapi.model.Cart;
import estoreapi.model.Instrument;
import estoreapi.model.Lesson;
import estoreapi.model.Product;
import estoreapi.model.Equipment;

/**
 * The unit test suite for the Cart class
 * 
 * @author Damon Gonzalez
 */
@Tag("Model-tier")
public class CartTest {

    private ArrayList<Product> generateProducts() {
        ArrayList<Product> products = new ArrayList<>();
        Product product1 = new Instrument(0, "Clarinet", 1000, Product.Category.WOODWINDS,
                5, true, false, false, null);
        Product product2 = new Instrument(1, "Trumpet", 2000, Product.Category.BRASS,
                10, true, false, false, null);
        Product product3 = new Instrument(2, "Cello", 3000, Product.Category.STRINGS,
                3, true, false, false, "1/2");
        products.add(product1);
        products.add(product2);
        products.add(product3);
        return products;
    }

    @Test
    public void testGetId() {
        int expected_id = 99;
        Cart cart = new Cart(99);

        int result = cart.getId();

        assertEquals(expected_id, result);
    }

    @Test
    public void testGetProducts() {
        ArrayList<Product> expectedProducts = generateProducts();
        Cart cart = new Cart(0);
        for(Product product : expectedProducts){
            cart.addProduct(product, 1);
        }

        ArrayList<Product> result = cart.getProducts();
        for(int i = 0; i < expectedProducts.size(); i++){
            assertEquals(expectedProducts.get(i), result.get(i));
        }
    }

    @Test
    public void testGetQuantities() {
        ArrayList<Integer> expectedQuantities = new ArrayList<>(Arrays.asList(1, 2, 3));
        ArrayList<Product> products = generateProducts();
        Cart cart = new Cart(0);
        for(int i = 0; i < products.size(); i++){
            cart.addProduct(products.get(i), expectedQuantities.get(i));
        }

        ArrayList<Integer> result = cart.getQuantities();

        for(int i = 0; i < result.size(); i++){
            assertEquals(expectedQuantities.get(i), result.get(i));
        }
    }

    @Test
    public void testGetTotal() {
        double expected_total = 6000;

        ArrayList<Product> products = generateProducts();
        Cart cart = new Cart(0);

        for(Product product : products){
            cart.addProduct(product, 1);
        }

        double result = cart.getTotal();

        assertEquals(expected_total, result);
    }

    @Test
    public void testAddItem() {
        Product expectedProduct = new Instrument(0, "Bass", 0, Product.Category.STRINGS, 
                                                100, true, false, false, "1/3");
        Cart cart = new Cart(0);
        cart.addProduct(expectedProduct, 1);
        
        assertEquals(expectedProduct, cart.getProducts().get(0));
    }

    @Test
    public void testRemoveItem() {
        Product expected_value = new Instrument(10, "Bass", 0, Product.Category.STRINGS, 100, true, false, false,
                "1/3");
        Cart cart = new Cart(0);
        cart.addProduct(expected_value, 1);

        cart.removeProduct(expected_value, 1);

        assertFalse(cart.containsProduct(expected_value));
    }

    @Test
    public void testToString() {
        Cart cart = new Cart(99);
        String expected_string = "Cart [id=99]";

        String result = cart.toString();

        assertTrue(expected_string.equals(result));
    }
}
