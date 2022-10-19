package com.estore.api.estoreapi.viewmodel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Hashtable;

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
import estoreapi.model.Instrument;
import estoreapi.model.Product;
import estoreapi.model.User;
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

     private Hashtable<Product, Integer> generateProducts() {
        Hashtable<Product, Integer> products = new Hashtable<Product, Integer>();
        Product product1 = new Instrument(0, "viola", 1000, Product.Category.STRINGS,
                5, true, false, false, null);
        Product product2 = new Instrument(1, "violin", 2000, Product.Category.STRINGS,
                10, true, false, false, null);
        Product product3 = new Instrument(2, "guitar", 3000, Product.Category.STRINGS,
                3, true, false, false, "1/2");
        products.put(product1, product1.getId());
        products.put(product2, product2.getId());
        products.put(product3, product3.getId());
        return products;
    }

     @Test
     public void testGetCart() throws IOException{
        // Setup
        Cart cart = new Cart(99);

        when(mockCartDAO.retrieveCart(cart.getId())).thenReturn(cart);
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
        doThrow(new IOException()).when(mockCartDAO).retrieveCart(cartId);

        // Invoke
        ResponseEntity<Cart> response = cartController.getCart(cartId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testCreateCart() throws IOException {  // createCart may throw IOException
        // Setup
        Cart cart = new Cart(99);
        
        User user = new User(2, "clayton", null, null, null, null, null, 0, 0, cart, null, false);
        // when createCart is called, return true simulating successful
        // creation and save
        when(mockCartDAO.createCart(cart,user)).thenReturn(cart);
        // cart and user needs to be in this test for createCarts

        // Invoke
        ResponseEntity<Cart> response = cartController.createCart(cart,user);
        // cart and user needs to be in this test for createCarts

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(cart,response.getBody());
    }

    @Test
    public void testCreateCartFailed() throws IOException {  // createCart may throw IOException
        // Setup
        Cart cart = new Cart(99);
        User user = new User(2, null, null, null, null, null, null, 0, 0, cart, null, false);
        // creation and save
        when(mockCartDAO.createCart(cart,user)).thenReturn(null);
        // cart and user needs to be in this test for createCarts
        

        // Invoke
        ResponseEntity<Cart> response = cartController.createCart(cart,user);
        // cart and user needs to be in this test for createCarts

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }


    @Test
    public void testCreateCartHandleException() throws IOException {  // createCart may throw IOException
        // Setup
        Cart cart = new Cart(99);
        User user = new User(2, null, null, null, null, null, null, 0, 0, cart, null, false);

        // When createCart is called on the Mock Cart DAO, throw an IOException
        doThrow(new IOException()).when(mockCartDAO).createCart(cart,user);
        // cart and user needs to be in this test for createCarts

        // Invoke
        ResponseEntity<Cart> response = cartController.createCart(cart,user);
        // cart and user needs to be in this test for createCarts

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testUpdateCart() throws IOException { // updateCart may throw IOException
        // Setup
        Cart cart = new Cart(99);
        Product item = new Product(2,"viola",122.2,null,2,true,false,false) {};
        // when updateCart is called, return true simulating successful
        // update and save
        when(mockCartDAO.addItem(cart,item,2)).thenReturn(cart);
        // need to create product item to add to this
        ResponseEntity<Cart> response = cartController.addItem(cart,item,2);
         // need to create product item to add to this;
        cart.addProduct(item,2);

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
        Cart cart = new Cart(99);
        Product item = new Product(2,"viola",122.2,null,2,true,false,false) {};
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
        Cart cart = new Cart(99);
        Product item = new Product(2,"viola",122.2,null,2,true,false,false) {};
        // When updateCart is called on the Mock Cart DAO, throw an IOException
        doThrow(new IOException()).when(mockCartDAO).addItem(cart, item, 2);

        // Invoke
        ResponseEntity<Cart> response = cartController.addItem(cart,item,2);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetHeroes() throws IOException { // getCart may throw IOException
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
