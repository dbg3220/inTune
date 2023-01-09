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

import estoreapi.model.Lesson;
import estoreapi.model.Product;
import estoreapi.model.User;
import estoreapi.persistence.DAO;

/**
 * Spring Controller to handle http requests for User objects
 * 
 * @author Damon Gonzalez
 */
@RestController
@RequestMapping("/users")
public class UserController {
    
    /** Logger object user for this controller */
    private static final Logger LOG = Logger.getLogger(UserController.class.getName());
    /** DAO used to access user objects */
    private DAO<User> userDAO;
    /** DAO used to access product objects, will not modify product object persistence here */
    private DAO<Product> productDAO;
    /** DAO used to access lesson objects */
    private DAO<Lesson> lessonDAO;
    /** Service used by this controller to handle the business logic of the
     * user resource. This service will be capable of viewing product objects
     * when a request to change a user is made.
     */
    private UserService eService;

    /**
     * Creates a REST API controller to respond to requests
     * 
     * @param userDAO The user data access object to perform CRUD operations
     * @param productDAO The product data access object to perform CRUD operations
     */
    public UserController(DAO<User> userDAO, DAO<Product> productDAO, DAO<Lesson> lessonDAO,
                            UserService eService) {
        this.userDAO = userDAO;
        this.productDAO = productDAO;
        this.lessonDAO = lessonDAO;
        this.eService = eService;
    }

    /**
     * Handles GET request for all users
     * @return A response entity with a body of all the users in the inventory
     * 
     * WARNING: do not use on frontend, exists now for testing purposes,
     * will be deleted
     */
    @GetMapping
    public ResponseEntity<User[]> getUsers(){
        LOG.info("GET /users");
        try {
            User[] users = userDAO.getItems();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (IOException e){
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles GET request for a single user
     * @param id The id of the user
     * @return A response entity with the appropriate body and status
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id){
        LOG.info("GET /users/" + id);
        try {
            User user = userDAO.getItem(id);
            if(user == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (IOException e){
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles GET request for searching for a user
     * @param username The username of the user
     * @return A response entity with the user and a status of OK if found,
     * status NOT_FOUND otherwise
     */
    @GetMapping("/search")
    public ResponseEntity<User> searchUser(@RequestParam String username){
        LOG.info("SEARCH /users/" + username);
        try{
            User user = eService.searchUser(userDAO, username);
            if(user == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (IOException e){
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles the POST request for a single user
     * @param user The user to be created
     * @return A response entity with the user as body and status of OK
     * if successful, status of BAD_REQUEST otherwise
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        LOG.info("POST /users " + user);
        try {
            User result = userDAO.createItem(user);
            if(result == null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (IOException e){
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles the PUT request for a single user
     * @param user The user to be updated, containing its unique identifier
     * @return A response entity with a body of the user and a status of OK
     * if successful, gives status of NOT_FOUND otherwise
     */
    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user){
        LOG.info("PUT /users " + user);
        try {
            return new ResponseEntity<>(eService.updateUser(userDAO, productDAO, user));
        } catch (IOException e){
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles DELETE request for a single user
     * @param id The id of the user
     * @return A response entity with code OK if succesful, NOT_FOUND if 
     * unsuccessful
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable int id ){
        LOG.info("DELETE /users/" + id);
        try {
            return new ResponseEntity<>(eService.deleteUser(userDAO, lessonDAO, id));
        } catch (IOException e){
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
