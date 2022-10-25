package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import estoreapi.model.Cart;
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
        testCarts[0] = new Cart(0);
        testCarts[1] = new Cart(1);

        when(mockObjectMapper
                .readValue(new File("doesn't_matter.txt"), Cart[].class))
                    .thenReturn(testCarts);
        cartFileDAO = new CartFileDAO("doesn't_matter.txt", mockObjectMapper);
    }
    
    @Test
    public void testGetCarts() {
        Cart[] carts = new Cart[2];
        carts[0] = new Cart(0);
        carts[1] = new Cart(1);

        Cart[] result = cartFileDAO.getCarts();
        for(int i = 0; i < carts.length; i++){
            assertEquals(carts[i], result[i]);
        }
    }

    @Test
    public void testGetCart() {
        assertNotNull(cartFileDAO.getCart(0));
    }

    @Test
    public void testCreateCart() throws Exception{
        Cart result = cartFileDAO.createCart(0);

        assertNull(result);

        result = cartFileDAO.createCart(3);

        assertNotNull(result);
    }

    @Test
    public void updateCart() throws Exception{
        ArrayList<Integer> productIDS = new ArrayList<>(Arrays.asList(1, 2));
        ArrayList<Integer> quantities = new ArrayList<>(Arrays.asList(5, 5));
        Cart cart = new Cart(0, productIDS, quantities);

        Cart result = cartFileDAO.updateCart(cart);

        assertNotNull(result);
        assertEquals(cart, result);
    }

    @Test
    public void testDeleteCart() throws Exception{
        boolean result = cartFileDAO.deleteCart(20);

        assertFalse(result);

        result = cartFileDAO.deleteCart(0);

        assertTrue(result);
    }
}
