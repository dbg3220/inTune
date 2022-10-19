package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
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

    private Hashtable<Product, Integer> generateProducts() {
        Hashtable<Product, Integer> products = new Hashtable<Product, Integer>();
        Product product1 = new Instrument(0, "clarinet", 1000, Product.Category.WOODWINDS,
                5, true, false, false, null);
        Product product2 = new Instrument(1, "Trumpet", 2000, Product.Category.BRASS,
                10, true, false, false, "1/2");
        Product product3 = new Instrument(2, "Cello", 3000, Product.Category.STRINGS,
                3, true, false, false, null);
        products.put(product1, product1.getQuantity());
        products.put(product2, product2.getQuantity());
        products.put(product3, product3.getQuantity());
        return products;
    }

    @Test
    public void testGetId() {
        int expected_id = 99;
        Cart cart = new Cart(99, generateProducts(), 34000);

        int result = cart.getId();

        assertEquals(expected_id, result);
    }

    @Test
    public void testGetItems() {
        Hashtable<Product, Integer> expected_items = generateProducts();
        Cart cart = new Cart(99, generateProducts(), 34000);

        // Hashtable<Product,Integer> result = cart.getItems();
        
        Object[] testArray = expected_items.keySet().toArray();
        Object[] resultArray = cart.getItems().keySet().toArray();
        assertArrayEquals(testArray, resultArray);
        for(int i = 0; i < resultArray.length; i++){
            if(resultArray[i] instanceof Lesson){
                Lesson resultLesson = (Lesson)resultArray[i];
                Lesson testLesson = (Lesson)testArray[i];
                assertEquals(resultLesson, testLesson);
            }
            if(resultArray[i] instanceof Instrument){
                Instrument resultInstrument = (Instrument)resultArray[i];
                Instrument testInstrument = (Instrument)testArray[i];
                assertEquals(resultInstrument,testInstrument);
            }
            if(resultArray[i] instanceof Equipment){
                Equipment resultEquipment = (Equipment)resultArray[i];
                Equipment testEquipment = (Equipment)testArray[i];
                assertEquals(resultEquipment, testEquipment);
            }
        }
    }

    @Test
    public void getQuantities() {
        ArrayList<Integer> expected_quantities = new ArrayList<Integer>(Arrays.asList(5, 10, 3));
        Cart cart = new Cart(99, generateProducts(), 34000);
        assertEquals(3, cart.getItems().size());

        ArrayList<Integer> result = cart.getQuantities();

        for(int i = 0; i < result.size(); i++){
            int x = expected_quantities.get(i);
            int y = result.get(i);
            assertEquals(x,y);
        }
       
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
        Product expected_product = new Instrument(10, "Bass", 0, Product.Category.STRINGS, 100, true, false, false,
                "1/3");
        Cart cart = new Cart(99, generateProducts(), 34000);
        cart.additem(expected_product, 1);
        int x = cart.getItems().keySet().size();
        assertEquals(4,x);
        Boolean result = cart.getItems().keySet().contains(expected_product);

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
