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
import estoreapi.persistence.ProductDAO;

/**
 * Spring Controller to handle http requests for User objects
 * 
 * @author Damon Gonzalez
 */
@RestController
@RequestMapping("users")
public class UserController {
    private static final Logger LOG = Logger.getLogger(UserController.class.getName());

    private UserDAO userDAO;
    private ProductDAO productDAO;

    /**
     * Creates a REST API controller to respond to requests
     * 
     * @param userDAO The user data access object to perform CRUD operations
     * @param productDAO The product data access object to perform CRUD operations
     */
    public UserController(UserDAO userDAO, ProductDAO productDAO) {
        this.userDAO = userDAO;
        this.productDAO = productDAO;
    }
}
