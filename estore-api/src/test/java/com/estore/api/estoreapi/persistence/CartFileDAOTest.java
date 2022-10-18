package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import estoreapi.model.Cart;
import estoreapi.model.Instrument;
import estoreapi.model.Lesson;
import estoreapi.model.Product;
import estoreapi.model.User;
import estoreapi.persistence.CartFileDAO;

/**
 * The junit test suite for the CartFileDAO class
 * 
 * @author Damon Gonzalez
 */
public class CartFileDAOTest {
    CartFileDAO cartFileDAO;
    Cart[] testCarts;
    ObjectMapper mockObjectMapper;
    
    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupCartFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testCarts = new Cart[2];
        testCarts[0] = new Cart(0, generateProducts(), 4100);
        testCarts[1] = new Cart(1, generateProducts(), 3400);

        when(mockObjectMapper
                .readValue(new File("doesn't_matter.txt"), Cart[].class))
                    .thenReturn(testCarts);
        cartFileDAO = new CartFileDAO("doesn't_matter.txt", mockObjectMapper);
    }

    private Hashtable<Product, Integer> generateProducts(){
        Product product1 = new Lesson(0, "Clarinet Lesson", 100, Product.Category.WOODWINDS, 1
                                    , false, false, true, "Dr Marcy Bacon", null);
        Product product2 = new Instrument(1, "Harp", 4000, Product.Category.STRINGS, 1
                                    , true, false, false, null);
        Hashtable<Product, Integer> hashtable = new Hashtable<Product, Integer>();
        hashtable.put(product1, 1);
        hashtable.put(product2, 1);
        return hashtable;
    }
    
    @Test
    public void testGetCarts() {
        Cart[] carts = cartFileDAO.getCarts();

        assertEquals(carts.length, testCarts.length);
        for (int i = 0; i < testCarts.length; i++){
            assertEquals(carts[i], testCarts[i]);
        }
    }

    @Test
    public void testRetrieveCarts() {
        Cart result = cartFileDAO.retrieveCart(0);

        assertEquals(result, testCarts[0]);
    }

    @Test
    public void testAddItem() {
        Product newProduct = new Lesson(3, null, 0, null, 0, 
                                        false, false, true, null, null);

        assertDoesNotThrow(() -> cartFileDAO.addItem(testCarts[0], newProduct, 5));
        Boolean result = cartFileDAO.retrieveCart(0).containsKey(newProduct);

        assertTrue(result);
    }

    @Test
    public void testRemoveItem() {
        Product newProduct = new Lesson(0, "Clarinet Lesson", 100, Product.Category.WOODWINDS, 1
                                    , false, false, true, "Dr Marcy Bacon", null);

        assertDoesNotThrow(() -> cartFileDAO.removeItem(testCarts[0], newProduct, 1));
        Boolean result = cartFileDAO.retrieveCart(0).containsKey(newProduct);

        assertFalse(result);
    }

    @Test
    public void testCreateCart() {
        Cart cart = new Cart(5, null, 0);
        User user = new User(10, null, null, null, null, 
                                null, 0, 0, 0, false, null, new User[0]);

        assertDoesNotThrow(() -> cartFileDAO.createCart(cart, user));
        Cart result1 = cartFileDAO.retrieveCart(10);
        assertNotNull(result1);
        //look for a cart that doesn't exist
        Cart result2 = cartFileDAO.retrieveCart(200);
        assertNull(result2);
    }

    @Test
    public void testDeleteCart() {
        Cart cart = new Cart(5, null, 0);
        User user = new User(10, null, null, null, null, 
                                null, 0, 0, 0, false, null, new User[0]);

        assertDoesNotThrow(() -> cartFileDAO.createCart(cart, user));
        assertDoesNotThrow(() -> cartFileDAO.deleteCart(5));

        Cart result = cartFileDAO.retrieveCart(5);
        assertNull(result);
    }
}
