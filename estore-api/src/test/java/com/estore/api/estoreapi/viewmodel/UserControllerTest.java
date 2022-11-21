package com.estore.api.estoreapi.viewmodel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import estoreapi.model.Cart;
import estoreapi.model.User;
import estoreapi.persistence.UserDAO;
import estoreapi.viewmodel.UserController;

@Tag("Controller-tier")
public class UserControllerTest {
    
    UserDAO mockDao;
    UserController userController;

    @BeforeEach
    public void setupUserController() throws IOException{
        mockDao = mock(UserDAO.class);
        userController = new UserController(mockDao);
        User User1 = new User(1, "Damon", null, null);
        User User2 = new User(2, "Tristen", null, null);
        User User3 = new User(3, "Matthew", null, null);
        User[] testUsers = {User1, User2, User3};
        for (User user : testUsers) {
            mockDao.createUser(user);
        }

    }

    @Test
    public void testGetUser() throws Exception{
        User user = new User(1, "Damon", null, null);
        when(mockDao.getUser(1)).thenReturn(user);

        ResponseEntity<User> response = userController.getUser(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testGetUserNotFound() throws Exception{
        when(mockDao.getUser(1)).thenReturn(null);

        ResponseEntity<User> response = userController.getUser(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetUserHandleException() throws Exception{
        doThrow(new IOException()).when(mockDao).getUser(1);

        ResponseEntity<User> response = userController.getUser(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetUsers() throws Exception{
        User[] testUsers = new User[3];
        testUsers[0] = new User(1, "Damon", null, null);
        testUsers[1] = new User(2, "Tristen", null, null);
        testUsers[2] = new User(3, "Matthew", null, null);
        when(mockDao.getUsers()).thenReturn(testUsers);

        ResponseEntity<User[]> response = userController.getUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testUsers, response.getBody());
    }

    @Test
    public void testGetUsersHandleException() throws Exception{
        doThrow(new IOException()).when(mockDao).getUsers();

        ResponseEntity<User[]> response = userController.getUsers();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testCreateUser() throws Exception{
        User testUser = new User(1, "Douglas Smith", null, null);
        when(mockDao.createUser(testUser)).thenReturn(testUser);
        ResponseEntity<User> response = userController.createUser(testUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testUser, response.getBody());
    }

    @Test
    public void testCreateUserNull() throws Exception{
        User testUser = new User(1, "Douglas Smith", null, null);
        when(mockDao.createUser(testUser)).thenReturn(null);
        
        ResponseEntity<User> response = userController.createUser(testUser);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testCreateUserFailed() throws Exception{
        User admin = new User(0, "admin", null, null);//creating an admin is illegal

        ResponseEntity<User> response = userController.createUser(admin);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testCreateUserHandleException() throws Exception{
        User user = new User(1, "Rafi", null, null);
        doThrow(new IOException()).when(mockDao).createUser(user);

        ResponseEntity<User> response = userController.createUser(user);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testDeleteUser() throws Exception{
        when(mockDao.deleteUser(1)).thenReturn(true);

        ResponseEntity<User> response = userController.deleteUser(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteUserFailed() throws Exception{
        ResponseEntity<User> response = userController.deleteUser(0);//deleting an admin is illegal

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testDeleteUserNull() throws Exception{
        ResponseEntity<User> response = userController.deleteUser(99);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testDeleteUserHandleException() throws Exception{
        User user = new User(1, "Meg", null, null);
        doThrow(new IOException()).when(mockDao).deleteUser(user.getId());

        ResponseEntity<User> response = userController.deleteUser(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testSearchForUser() throws Exception{
        User user = new User(1, "Damon", null, null);
        when(mockDao.findUser("Damon")).thenReturn(user);

        ResponseEntity<User> response = userController.searchForUser("Damon");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }


    @Test
    public void testSearchForUserNotFound() throws Exception{  

        ResponseEntity<User> response = userController.searchForUser(null);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        
    }

    @Test
    public void testSearchForUserError() throws Exception{  
        User user = new User(1, "Damon", null, null);
        doThrow(new IOException()).when(mockDao).findUser(user.getUsername());
        ResponseEntity<User> response = userController.searchForUser(user.getUsername());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        
    }

    @Test
    public void testUpdateUser() throws Exception{  
        User user = new User(1, "updated", null, null);
        when(mockDao.updateUser(user)).thenReturn(user);
        ResponseEntity<User> response = userController.updateUser(user);

        assertEquals(user, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        
    }

    @Test
    public void testUpdateUserNotFound() throws Exception{  
        User user = new User(1, "updated", null, null);
        when(mockDao.updateUser(user)).thenReturn(null);

        ResponseEntity<User> response = userController.updateUser(user);

        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        
    }

    @Test
    public void testUpdateUserError() throws Exception{  
        User user = new User(1, "updated", null, null);
        doThrow(new IOException()).when(mockDao).updateUser(user);

        ResponseEntity<User> response = userController.updateUser(user);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        
    }
}
