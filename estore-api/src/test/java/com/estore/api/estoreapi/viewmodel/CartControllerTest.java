package com.estore.api.estoreapi.viewmodel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Hashtable;

import javax.swing.text.Caret;

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
import estoreapi.persistence.CartDAO;
import estoreapi.persistence.CartFileDAO;
import estoreapi.viewmodel.CartController;

/**
 * Test the Hero Controller class
 * 
 * @author Jonathan Zhu
 */

@Tag("Controller-tier")
public class CartControllerTest {
    private CartController cartController;
    private CartDAO mockCartDAO;

    /**
     * Before each test, create a new CartController object and inject
     * a mock Cart DAO
     */

     @BeforeEach
     public void setupCartController() {
        mockCartDAO = mock(CartDAO.class);
        cartController = new CartController(mockCartDAO);
     }

     @Test
     public void testGetCart() throws IOException{
        // Setup
        Cart cart = new Cart(99, 20); // need hashtable example 

        when(mockCartDAO.getCarts()).thenReturn(cart);
        // need to fix cart for this to work

        // Invoke
        ResponseEntity<Cart> response = cartController.getCart(cart.getId());

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
        doThrow(new IOException()).when(mockCartDAO).getCarts();

        // Invoke
        ResponseEntity<Cart> response = cartController.getCart(cartId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testCreateCart() throws IOException {  // createCart may throw IOException
        // Setup
        Cart cart = new Cart(99, 20); // need hashtable example 
        // when createCart is called, return true simulating successful
        // creation and save
        when(mockCartDAO.createCarts()).thenReturn(cart);
        // cart and user needs to be in this test for createCarts

        // Invoke
        ResponseEntity<Cart> response = cartController.createCarts(cart);
        // cart and user needs to be in this test for createCarts

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(cart,response.getBody());
    }

    @Test
    public void testCreateCartFailed() throws IOException {  // createCart may throw IOException
        // Setup
        Cart cart = new Cart(99, 20); // need hashtable example 
        // when createCart is called, return false simulating failed
        // creation and save
        when(mockCartDAO.createCart(cart)).thenReturn(null);
        // cart and user needs to be in this test for createCarts
        

        // Invoke
        ResponseEntity<Cart> response = cartController.createCart(cart);
        // cart and user needs to be in this test for createCarts

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }


    @Test
    public void testCreateCartHandleException() throws IOException {  // createCart may throw IOException
        // Setup
        Cart cart = new Cart(99, 20); // need hashtable example 

        // When createCart is called on the Mock Cart DAO, throw an IOException
        doThrow(new IOException()).when(mockCartDAO).createCart(cart);
        // cart and user needs to be in this test for createCarts

        // Invoke
        ResponseEntity<Cart> response = cartController.createCart(cart);
        // cart and user needs to be in this test for createCarts

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testUpdateCart() throws IOException { // updateCart may throw IOException
        // Setup
        Cart cart = new Cart(99, 20); // need hashtable example 
        // when updateCart is called, return true simulating successful
        // update and save
        when(mockCartDAO.addItem(cart,item,2)).thenReturn(cart);
        // need to create product item to add to this
        ResponseEntity<Cart> response = cartController.addItem(cart,item,2)
         // need to create product item to add to this;
        cart.setName("viola");

        // Invoke
        response = cartController.addItem(cart, item, 2);
        // need to create product item to add to this;

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(cart,response.getBody());
    }

    @Test
    public void testUpdateCartFailed() throws IOException { // updateCart may throw IOException
        // Setup
        Cart cart = new Cart(99, 20); // need hashtable example 
        // when updateCart is called, return true simulating successful
        // update and save
        when(mockCartDAO.addItem(cart,item,2)).thenReturn(null);
        // need to create product item to add to this;

        // Invoke
        ResponseEntity<Cart> response = cartController.addItem(cart,item,2);
        // need to create product item to add to this;

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testUpdateCartHandleException() throws IOException { // updateCart may throw IOException
        // Setup
        Cart cart = new Cart(99, 20); // need hashtable example 
        // When updateCart is called on the Mock Cart DAO, throw an IOException
        doThrow(new IOException()).when(mockCartDAO).addItem(cart, item, 2)

        // Invoke
        ResponseEntity<Cart> response = cartController.addItem(cart,item,2);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetHeroes() throws IOException { // getCart may throw IOException
        // Setup
        Cart[] carts = new Cart[2];
        carts[0] = new Cart(99, 20); // need hashtable example 
        carts[1] = new Cart(99, 20); // need hashtable example 
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
    public void testSearchCarts() throws IOException { // findCarts may throw IOException
        // Setup
        String searchString = "la";
        Cart[] carts = new Cart[2];
        carts[0] = new Cart(99, 20); // need hashtable example 
        carts[1] = new Cart(99, 20); // need hashtable example 
        // When findCarts is called with the search string, return the two
        /// carts above
        when(mockCartDAO.retrieveCart(searchString)).thenReturn(carts);

        // Invoke
        ResponseEntity<Cart[]> response = cartController.searchCart(searchString);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(carts,response.getBody());
    }

    @Test
    public void testSearchCartsHandleException() throws IOException { // findCarts may throw IOException
        // Setup
        String searchString = "an";
        // When createCarts called on the Mock Cart DAO, throw an IOException
        doThrow(new IOException()).when(mockCartDAO).retrieveCarts(searchString);

        // Invoke
        ResponseEntity<Cart[]> response = cartController.retrieveCarts(searchString);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testDeleteHero() throws IOException { // deleteCart may throw IOException
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
