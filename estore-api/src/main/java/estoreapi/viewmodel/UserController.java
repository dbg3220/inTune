package estoreapi.viewmodel;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.catalina.connector.Response;
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
     * @param UserDAO The {@link UserDAO User Data Access Object} to
     *                   perform CRUD operations
     *                   <br>
     *                   This dependency is injected by the Spring Framework
     */
    public UserController(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    /**
    * Handles the HTTP GET request for the user resource
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
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles the HTTP GET request for the user resource
     * @return All users
     */
    @GetMapping("")
    public ResponseEntity<User[]> getUsers(){
        try {
            User[] users = userDAO.getUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles the HTTP GET request for searching for a user
     * @param username The string to match against
     * @return The user if found, null otherwise
     */
    @GetMapping("/{username}")
    public ResponseEntity<User> searchForUser(@RequestParam String username){
        LOG.info("GET /users/?username=" + username);
        try {
            User user = userDAO.findUser(username);
            if(user != null){
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
     * @param user The user to create
     * @return The HTTP response
     */
    @PostMapping("")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        LOG.info("POST /users " + user);
        try {
            if(user.getccnum().length() != 16
                || user.isAdmin()){//checks valid credit card number length and if the user is admin
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            User newUser = userDAO.createUser(user); // use conditionals to check the is
            if (newUser != null)
                return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
            else
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles the HTTP PUT request to update an existing user
     * @param user The user to update
     * @return The HTTP response
     */
    @PutMapping("")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        LOG.info("POST /users " + user);
        try {
            if(user.getccnum().length() != 16
                || user.isAdmin()){//checks valid credit card number length and if the user is admin
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            User newUser = userDAO.updateUser(user); // use conditionals to check the is
            if (newUser != null)
                return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
            else
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles the HTTP DELETE request
     * @param id The id of the user to delete
     * @return The HTTP response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable int id) {
        LOG.info("DELETE /users/ " + id);
        try {
            boolean result = userDAO.deleteUser(id);
            if(result){
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
