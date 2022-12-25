package estoreapi.viewmodel;

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
import estoreapi.persistence.ProductDAO;
import estoreapi.persistence.UserDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Spring Controller to handle http requests for Product objects
 * 
 * @author Damon Gonzalez
 */
@RestController
@RequestMapping("products")
public class ProductController {
    private static final Logger LOG = Logger.getLogger(ProductController.class.getName());
    private ProductDAO productDao;
    private UserDAO userDao;

    /**
     * Creates a REST API controller to respond to requests
     * 
     * @param productDAO The product data access object to perform CRUD operations
     * @param userDAO The user data access object to perform CRUD operations
     */
    public ProductController(ProductDAO productDao, UserDAO userDao) {
        this.productDao = productDao;
        this.userDao = userDao;
    }
}
