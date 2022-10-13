package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import estoreapi.model.Cart;
import estoreapi.model.Instrument;
import estoreapi.model.Product;

/**
 * The unit test suite for the Cart class
 * 
 * @author Damon Gonzalez
 */
@Tag("Model-tier")
public class CartTest {

    @BeforeEach
    private Hashtable<Product, Integer> generateProducts() {
        Hashtable<Product, Integer> products = new Hashtable<Product, Integer>();
        Product product1 = new Instrument(0, "clarinet", 1000, Product.Category.WOODWINDS,
                5, true, false, false, null);
        Product product2 = new Instrument(1, "Trumpet", 2000, Product.Category.BRASS,
                10, true, false, false, null);
        Product product3 = new Instrument(2, "Cello", 3000, Product.Category.STRINGS,
                3, true, false, false, "1/2");
        products.put(product1, product1.getId());
        products.put(product2, product2.getId());
        products.put(product3, product3.getId());
        return products;
    }

    @Test
    public void testGetId() {
        int expected_id = 99;
        Cart cart = new Cart(99, generateProducts(), 34000);

        int result = cart.getid();

        assertEquals(expected_id, result);
    }

    @Test
    public void testGetItems() {
        Hashtable<Product, Integer> expected_items = generateProducts();
        Cart cart = new Cart(99, generateProducts(), 34000);

        Set<Product> result = cart.getItems();

        assertEquals(expected_items, result);
    }

    @Test
    public void getQuantities() {
        ArrayList<Integer> expected_quantities = new ArrayList<Integer>(Arrays.asList(5, 10, 3));
        Cart cart = new Cart(99, generateProducts(), 34000);

        ArrayList<Integer> result = cart.getQuantities();

        assertEquals(expected_quantities, result);
    }

    @Test
    public void testGetTotal() {
        double expected_total = 34000;
        Cart cart = new Cart(99, generateProducts(), 34000);

        double result = cart.getTotal();

        assertEquals(expected_total, result);
    }

    @Test
    public void testAddItem() {
        Product expected_product = new Instrument(10, "Bass", 0, Product.Category.STRINGS, 1, true, false, false,
                "1/3");
        Cart cart = new Cart(99, generateProducts(), 34000);
        cart.additem(expected_product, 1);

        Boolean result = cart.getItems().contains(expected_product);

        assertTrue(result);
    }

    @Test
    public void testRemoveItem() {
        Product expected_product = new Instrument(10, "Bass", 0, Product.Category.STRINGS, 1, true, false, false,
                "1/3");
        Cart cart = new Cart(99, generateProducts(), 34000);
        cart.additem(expected_product, 1);
        cart.removeitem(expected_product, 0);

        Boolean result = cart.getItems().contains(expected_product);

        assertFalse(result);
    }

    @Test
    public void testToString() {
        Cart cart = new Cart(99, generateProducts(), 34000);
        String expected_string = "Cart [id=99]";

        String result = cart.toString();

        assertTrue(expected_string.equals(result));
    }
}
