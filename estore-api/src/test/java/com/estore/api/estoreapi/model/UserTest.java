package com.estore.api.estoreapi.model;

import estoreapi.model.Cart;
import estoreapi.model.User;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
/**
 * The unit test for the user model class
 * 
 * @author Donovan Cataldo
 */
@Tag("Model-tier")
public class UserTest {

    @Test
    public void testCtor(){
        int expectedID = 99;
        String expectedUsername = "user123";

        User user = new User(expectedID, expectedUsername, null, null);

        assertEquals(expectedID, user.getId());
        assertEquals(expectedUsername, user.getUsername());
    }

    @Test
    public void testToString(){
        int expectedID = 99;
        String expectedUsername = "user123";

        String expectedToString = "User [id=99, username=user123]";

        User user = new User(expectedID, expectedUsername, null, null);

        assertEquals(expectedToString, user.toString());
    }

    @Test
    public void getCart(){
        int expectedID = 99;
        String expectedUsername = "user123";
        String expectedToString = "User [id=99, username=user123]";
        ArrayList<Integer> arrayList = new ArrayList<> ();            

        Cart cart = new Cart(0, arrayList, arrayList);

        User user = new User(expectedID, expectedUsername, cart, null);

        assertEquals(cart, user.getCart());
    }

    @Test
    public void getProductsPurchased(){
        int expectedID = 99;
        String expectedUsername = "user123";
        String expectedToString = "User [id=99, username=user123]";
        ArrayList<Integer> arrayList = new ArrayList<> ();            

        int array[] = new int[0];

        User user = new User(expectedID, expectedUsername, null, array);

        assertEquals(array, user.getProductsPurchased());
    }
}

