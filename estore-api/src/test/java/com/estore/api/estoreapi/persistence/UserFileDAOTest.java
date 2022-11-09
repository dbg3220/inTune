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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

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
        testUsers[0] = new User(0, "Damon", null, null);
        testUsers[1] = new User(1, "Tristen", null, null);
        testUsers[2] = new User(2, "Matthew", null, null);

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
    public void testGetUser() throws Exception{
        User user = userDAO.getUser(0);

        assertEquals(testUsers[0], user);
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
}
