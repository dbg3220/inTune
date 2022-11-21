package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

import estoreapi.model.Cart;
import estoreapi.model.User;
import estoreapi.persistence.UserDAO;
import estoreapi.persistence.UserFileDAO;

/**
 * Testing Suite for the UserDAO
 * 
 * @author Damon Gonzalez
 */
@Tag("persistence-tier")
public class UserFileDAOTest {
    UserDAO userDAO;
    User[] testUsers;
    Cart[] testCarts;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * 
     * @throws IOException
     */
    @BeforeEach
    public void setupUserFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testUsers = new User[3];
        ArrayList products = new ArrayList<>();
        ArrayList quantities = new ArrayList<>();
        testCarts = new Cart[3];
        testCarts[0] = new Cart( 0, products, quantities);
        testCarts[1] = new Cart( 1, products, quantities);
        testCarts[2] = new Cart( 2, products, quantities);
        testUsers[0] = new User(0, "Damon", testCarts[0], null);
        testUsers[1] = new User(1, "Tristen", testCarts[1], null);
        testUsers[2] = new User(2, "Matthew", testCarts[2], null);

        when(mockObjectMapper
                    .readValue(new File(""), User[].class))
                    .thenReturn(testUsers);

        userDAO = new UserFileDAO("", mockObjectMapper);
    }

    @Test
    public void testGetUsers() throws Exception{
        User[] users = userDAO.getUsers();

        assertEquals(testUsers.length, users.length);
        for(int i = 0; i < users.length; i++){
            assertEquals(testUsers[i], users[i]);
        }
    }

    @Test
    public void testGetCarts() throws Exception{
        Cart[] carts = userDAO.getCarts();

        assertEquals(testCarts.length, carts.length);
        for(int i = 0; i < carts.length; i++){
            assertEquals(testCarts[i], carts[i]);
        }
    }


    @Test
    public void testGetUser() throws Exception{
        User user = userDAO.getUser(0);

        assertEquals(testUsers[0], user);
    }

    @Test
    public void testGetUserNotFound() throws Exception{
        User user = userDAO.getUser(101);

        assertEquals(null, user);
    }

    @Test
    public void testFindUserNull() throws Exception{
        User user = userDAO.findUser(null);

        assertEquals(null, user);
    }

    @Test
    public void testFindUserNotFound() throws Exception{
        User user = userDAO.findUser("Usernamedoesn'texist");

        assertEquals(null, user);
    }

    @Test
    public void testFindUser() throws Exception{
        User user = userDAO.findUser("Damon");

        assertEquals(testUsers[0], user);
    }

    @Test
    public void testFindUsers() throws Exception{
        User[] users = userDAO.findUsers("Dam");

        assertEquals(1, users.length);
        assertEquals(testUsers[0], users[0]);
    }

    @Test
    public void testCreateUser() throws Exception{
        int givenID = 99;
        String givenName = "Douglas Gonzalez";

        User newUser = new User(givenID, givenName, null, null);
        userDAO.createUser(newUser);

        assertNotNull(userDAO.findUser(givenName));
        assertNotEquals(givenID, userDAO.findUser(givenName).getId());

        User result = userDAO.createUser(new User(100, givenName, null, null));

        assertNull(result);
    }

    @Test
    public void testDeleteUser() throws Exception{
        boolean result1 = userDAO.deleteUser(0);
        boolean result2 = userDAO.deleteUser(100);

        assertTrue(result1);
        assertFalse(result2);
    }


    @Test
    public void testGetCart() throws Exception{
        Cart cart = userDAO.getCart(0);

        assertEquals(testUsers[0].getCart(), cart);
    }

    @Test
    public void testGetCartNotFound() throws Exception{
        Cart cart = userDAO.getCart(101);

        assertEquals(null, cart);
    }

    @Test
    public void testUpdateCart() throws Exception{
        ArrayList products = new ArrayList<>();
        products.add(1);
        products.add(2);
        products.add(3);
        ArrayList quantities = new ArrayList<>();
        Cart cart = new Cart(0,products, quantities);
       
        
        userDAO.updateCart(userDAO.getUser(0), cart);

        assertEquals(products, userDAO.getCart(0).getProductIDS());
    }

    @Test
    public void testUpdateCartNotFound() throws Exception{
        ArrayList products = new ArrayList<>();
        products.add(1);
        products.add(2);
        products.add(3);
        ArrayList quantities = new ArrayList<>();
        Cart cart = new Cart(0,products, quantities);
        User newUser = new User(101, null, cart, null);
        Cart result = userDAO.updateCart( newUser, cart);

        assertEquals(null, result);
    }

    @Test
    public void testUpdateUser() throws Exception{
        
        User user = new User(0, "Updated",null , null);
        userDAO.updateUser(user);

        assertEquals(user, userDAO.getUser(0));
    }

    @Test
    public void testUpdateUserNotFound() throws Exception{
        
        User user = new User(101, "Updated",null , null);
        User response = userDAO.updateUser(user);

        assertEquals(null, response);
    }




}
