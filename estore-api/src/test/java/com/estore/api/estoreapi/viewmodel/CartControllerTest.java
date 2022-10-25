package com.estore.api.estoreapi.viewmodel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/*
import com.heroes.api.heroesapi.persistence.HeroDAO;
import com.heroes.api.heroesapi.model.Hero;
*/

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import estoreapi.model.Cart;
import estoreapi.model.Product;
import estoreapi.model.Product.Category;
import estoreapi.persistence.CartDAO;
import estoreapi.persistence.CartFileDAO;
import estoreapi.persistence.ProductDAO;
import estoreapi.viewmodel.CartController;

/**
 * Test the Hero Controller class
 * 
 * @author Jonathan Zhu
 * @author Damon Gonzalez
 */

@Tag("Controller-tier")
public class CartControllerTest {
    private CartController cartController;
    private CartDAO mockCartDAO;
    private ProductDAO mockProductDAO;

    /**
     * Before each test, create a new CartController object and inject
     * a mock Cart DAO
     */

     @BeforeEach
     public void setupCartController() {
        mockCartDAO = mock(CartDAO.class);
        mockProductDAO = mock(ProductDAO.class);
        cartController = new CartController(mockCartDAO, mockProductDAO);
     }

     @Test
     public void testGetCart() throws IOException{
        // Setup
        int cartID = 99;
        Cart cart = new Cart(cartID);
        when(mockCartDAO.getCart(cart.getId())).thenReturn(cart);
        // Invoke
        ResponseEntity<Cart> response = cartController.getCart(cartID);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(cart,response.getBody());
     }

    @Test
    public void testGetCartNotFound() throws Exception { // createCart may throw IOException
        // Setup
        int cartId = 99;
        // When the same id is passed in, our mock Cart DAO will return null, simulating
        // no Cart found
        when(mockCartDAO.getCarts()).thenReturn(null);
        
        // Invoke
        ResponseEntity<Cart> response = cartController.getCart(cartId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetCartHandleException() throws Exception { // createCart may throw IOException
        // Setup
        int cartId = 99;
        // When getCart is called on the Mock Cart DAO, throw an IOException
        doThrow(new IOException()).when(mockCartDAO).getCart(cartId);

        // Invoke
        ResponseEntity<Cart> response = cartController.getCart(cartId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testUpdateCart() throws IOException { // updateCart may throw IOException
        // Setup
        Cart cart = new Cart(99);
        Product product = new Product(1, "Clarinet", 100, Category.WOODWINDS, 100,
                                        "a good instrument", "squidward.png");
        // when updateCart is called, return true simulating successful
        // update and save
        when(mockCartDAO.updateCart(cart)).thenReturn(cart);
        // need to create product item to add to this
        ResponseEntity<Cart> response = cartController.updateCart(cart);
        cart = new Cart(99, new ArrayList<>(Arrays.asList(1)), new ArrayList<>(Arrays.asList(5)));
        when(mockCartDAO.updateCart(cart)).thenReturn(cart);   
        when(mockProductDAO.getProduct(1)).thenReturn(product);
        // Invoke
        response = cartController.updateCart(cart);
        // need to create product item to add to this;

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(cart,response.getBody());
    }

    @Test
    public void testUpdateCartFailed() throws IOException { // updateCart may throw IOException
        // Setup
        Cart cart = new Cart(99);
        Product product = new Product(1, "Clarinet", 100, Category.WOODWINDS, 100,
                                        "a good instrument", "squidward.png");
        // when updateCart is called, return true simulating successful
        // update and save
        when(mockCartDAO.updateCart(cart)).thenReturn(cart);
        // need to create product item to add to this
        ResponseEntity<Cart> response = cartController.updateCart(cart);
        cart = new Cart(99, new ArrayList<>(Arrays.asList(1)), new ArrayList<>(Arrays.asList(5)));
        when(mockCartDAO.updateCart(cart)).thenReturn(null);   
        when(mockProductDAO.getProduct(1)).thenReturn(product);
        // Invoke
        response = cartController.updateCart(cart);
        // need to create product item to add to this;

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testUpdateCartHandleException() throws IOException { // updateCart may throw IOException
        // Setup
        Cart cart = new Cart(99);
        // When updateCart is called on the Mock Cart DAO, throw an IOException
        doThrow(new IOException()).when(mockCartDAO).updateCart(cart);

        // Invoke
        ResponseEntity<Cart> response = cartController.updateCart(cart);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetCarts() throws IOException { // getCart may throw IOException
        // Setup
        Cart[] carts = new Cart[2];
        carts[0] = new Cart(99);
        carts[1] = new Cart(100);
        // When getCart is called return the carts created above
        when(mockCartDAO.getCarts()).thenReturn(carts);

        // Invoke
        ResponseEntity<Cart[]> response = cartController.getCarts();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(carts,response.getBody());
    }
    
    @Test
    public void testGetCartsHandleException() throws IOException { // getCart may throw IOException
        // Setup
        // When getCarts is called on the Mock Cart DAO, throw an IOException
        doThrow(new IOException()).when(mockCartDAO).getCarts();

        // Invoke
        ResponseEntity<Cart[]> response = cartController.getCarts();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testDeleteCart() throws IOException { // deleteCart may throw IOException
        // Setup
        int cartId = 99;
        // when deleteCart is called return true, simulating successful deletion
        when(mockCartDAO.deleteCart(cartId)).thenReturn(true);

        // Invoke
        ResponseEntity<Cart> response = cartController.deleteCart(cartId);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteCartNotFound() throws IOException { // deleteCart may throw IOException
        // Setup
        int cartId = 99;
        // when deleteCartis called return false, simulating failed deletion
        when(mockCartDAO.deleteCart(cartId)).thenReturn(false);

        // Invoke
        ResponseEntity<Cart> response = cartController.deleteCart(cartId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteCartHandleException() throws IOException { // deleteCart may throw IOException
        // Setup
        int cartId = 99;
        // When deleteCartis called on the Mock Cart DAO, throw an IOException
        doThrow(new IOException()).when(mockCartDAO).deleteCart(cartId);

        // Invoke
        ResponseEntity<Cart> response = cartController.deleteCart(cartId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}
