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
import estoreapi.model.Product;
import estoreapi.model.User;
import estoreapi.persistence.DAO;

/**
 * Spring Controller to handle http requests for User objects
 * 
 * @author Damon Gonzalez
 */
@RestController
@RequestMapping("users")
public class UserController {

    private static final Logger LOG = Logger.getLogger(UserController.class.getName());
    private DAO<User> userDAO;
    private DAO<Product> productDAO;

    /**
     * Creates a REST API controller to respond to requests
     * 
     * @param userDAO The user data access object to perform CRUD operations
     * @param productDAO The product data access object to perform CRUD operations
     */
    public UserController(DAO<User> userDAO, DAO<Product> productDAO) {
        this.userDAO = userDAO;
        this.productDAO = productDAO;
    }

    /**
     * Handles GET request for a single user
     * @param id The id of the user
     * @return A response entity with the appropriate body and status
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id){
        LOG.info("GET /products/" + id);
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
}
