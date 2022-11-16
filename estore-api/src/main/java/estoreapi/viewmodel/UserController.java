package estoreapi.viewmodel;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import estoreapi.model.Cart;
import estoreapi.model.User;
import estoreapi.persistence.UserDAO;

/**
 * Handles the REST API requests for the user resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST
 * API method handler to the Spring framework
 * 
 * @author Damon Gonzalez
 */

@RestController
@RequestMapping("users")
public class UserController {
    private static final Logger LOG = Logger.getLogger(UserController.class.getName());

    private UserDAO userDAO;

    /**
     * Creates a REST API controller to respond to requests
     * 
     * @param userDAO The {@link UserDAO User Data Access Object} to
     *                perform CRUD operations
     * @param cartDAO The {@link CartDAO Cart Data Access Object} to
     *                perform CRUD operations
     */
    public UserController(UserDAO userDAO) {
        this.userDAO = userDAO;
        }

    /**
     * Handles the HTTP GET request for the user resource
     * 
     * @param id The id of the user to retrieve
     * @return The user with the specified id
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        LOG.info("GET /users/" + id);
        try {
            User user = userDAO.getUser(id);
            if (user != null)
                return new ResponseEntity<>(user, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles the HTTP GET request for the user resource
     * 
     * @return All users
     */
    @GetMapping("")
    public ResponseEntity<User[]> getUsers() {
        LOG.info("GET /users");
        try {
            User[] users = userDAO.getUsers();
            System.out.println("fetching user data " + users);
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles the HTTP GET request for searching for a user
     * 
     * @param username The string to match against
     * @return The user if found
     */
    @GetMapping("/")
    public ResponseEntity<User> searchForUser(@RequestParam String username) {
        LOG.info("GET /users/?username=" + username);
        try {
            User user = userDAO.findUser(username);
            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles the HTTP POST request to create a new user
     * 
     * If a user with the name 'admin' is requested than the request is
     * rejected because there can be only 1 admin for the API.
     * 
     * When a user is created a cart is also created with a corresponding id
     * 
     * @param user The user to create
     * @return The HTTP response along with a body of the user
     * if successful
     */
    @PostMapping("")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        LOG.info("POST /users " + user);
        try {
            if("admin".equals(user.getUsername())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            User newUser = userDAO.createUser(user); 
            if(newUser == null){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            return new ResponseEntity<User>(newUser, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles the HTTP DELETE request
     * 
     * If a user is deleted than their corresponding cart is also deleted
     * 
     * If a request for the user admin is made than the request is rejected
     * 
     * @param id The id of the user to delete
     * @return The HTTP response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable int id) {
        LOG.info("DELETE /users/ " + id);
        try {
            if(id == 0){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            boolean userResult = userDAO.deleteUser(id);
            if(!userResult){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

     /**
     * Handles the HTTP PUT request to update an existing product
     * @param product The product to update
     * @return The HTTP response
     */
    @PutMapping("")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        LOG.info("PUT /users " + user);
        try {
            User newUser = userDAO.updateUser(user);
            if (newUser != null){
                newUser.setCart(user.getCart());
                return new ResponseEntity<User>(newUser,HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
