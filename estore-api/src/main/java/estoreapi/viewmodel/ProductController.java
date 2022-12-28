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

import estoreapi.model.User;
import estoreapi.model.Cart;
import estoreapi.model.Product;
import estoreapi.persistence.DAO;
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
@RequestMapping("/products")
public class ProductController {

    /** Logger object user for this controller */
    private static final Logger LOG = Logger.getLogger(ProductController.class.getName());
    /** DAO used to access product objects */
    private DAO<Product> productDAO;
    /** DAO used to access user objects */
    private DAO<User> userDAO;

    /**
     * Creates a REST API controller to respond to requests
     * 
     * @param productDAO The product data access object to perform CRUD operations, injected by spring
     * @param userDAO The user data access object to perform CRUD operations, injected by spring
     */
    public ProductController(DAO<Product> productDAO, DAO<User> userDAO) {
        this.productDAO = productDAO;
        this.userDAO = userDAO;
    }

    /**
     * Handles GET request for all products
     * @return A response entity with a body of all the products in the inventory
     */
    @GetMapping
    public ResponseEntity<Product[]> getProducts(){
        LOG.info("GET /products");
        try {
            Product[] products = productDAO.getItems();
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (IOException e){
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles GET request for a single product
     * @param id The id of the product
     * @return A response entity with the appropriate body and status
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id){
        LOG.info("GET /products/" + id);
        try {
            Product product = productDAO.getItem(id);
            if(product == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (IOException e){
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles the POST request for a single product
     * @param product The product to be created
     * @return A response entity with the product as body and status of OK
     * if successful, status of BAD_REQUEST otherwise
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        LOG.info("POST /products " + product);
        try {//TODO implement this further with logical checks
            Product result = productDAO.createItem(product);
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
     * Handles the PUT request for a single product
     * @param product The product to be updated, containing its unique identifier
     * @return A response entity with a body of the product and a status of OK
     * if successful, gives status of NOT_FOUND otherwise
     */
    @PutMapping
    public ResponseEntity<Product> updateProduct(@RequestBody Product product){
        LOG.info("PUT /products " + product);
        try {//TODO implement this further with logical checks
            Product result = productDAO.updateItem(product);
            if(result == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (IOException e){
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles DELETE request for a single product
     * @param id The id of the product
     * @return A response entity with code OK if succesful, NOT_FOUND if 
     * unsuccessful
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable int id ){
        LOG.info("DELETE /products/" + id);
        try {//TODO implement this further with logical checks
            if(!productDAO.deleteItem(id)){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e){
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
