package com.estore.api.estoreapi.model;

import estoreapi.model.User;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

        User user = new User(expectedID, expectedUsername);

        assertEquals(expectedID, user.getId());
        assertEquals(expectedUsername, user.getUsername());
    }

    @Test
    public void testToString(){
        int expectedID = 99;
        String expectedUsername = "user123";

        String expectedToString = "User [id=99, username=user123]";

        User user = new User(expectedID, expectedUsername);

        assertEquals(expectedToString, user.toString());
    }
}
