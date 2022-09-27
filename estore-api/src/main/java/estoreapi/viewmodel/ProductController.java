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

import estoreapi.model.Product;
import estoreapi.persistence.ProductDAO;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles the REST API requests for the product resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST
 * API
 * method handler to the Spring framework
 * 
 * @author Hayden Cieniawski
 */

@RestController
@RequestMapping("products")
public class ProductController {
    private static final Logger LOG = Logger.getLogger(ProductController.class.getName());
    private ProductDAO productDao;

    /**
     * Creates a REST API controller to respond to requests
     * 
     * @param ProductDao The {@link ProductDAO Product Data Access Object} to
     *                   perform CRUD operations
     *                   <br>
     *                   This dependency is injected by the Spring Framework
     */
    public ProductController(ProductDAO productDao) {
        this.productDao = productDao;
    }

   /**
    * Handles the HTTP GET request for the product resource
    * @param id The id of the product to retrieve
    * @return The product with the specified id
    */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id) {
        LOG.info("GET /Products/" + id);   
        try {
            Product product = productDao.getProduct(id);
            if (product != null)
                return new ResponseEntity<>(product, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * Handles the HTTP GET request for the product resource
     * @return All products
     */
    @GetMapping("")
    public ResponseEntity<Product[]> getProducts() {
    LOG.info("GET /products");
    try {
        return new ResponseEntity<>(productDao.getProducts(), HttpStatus.OK);
    } catch (IOException e) {
        LOG.log(Level.SEVERE, "Error getting products", e);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    }
    /**
     * Handles the HTTP POST request for the product resource
     * @param name The name of the product(s) to retrieve
     * @return The products with the specified name
     */
    @GetMapping("/")
    public ResponseEntity<Product[]> searchProductsByName(@RequestParam String name) {
        LOG.info("GET /products/?name=" + name);

        try {
            Product[] searchProductsByName = productDao.findProducts(name);
            if (searchProductsByName!= null)
                return new ResponseEntity<Product[]>(searchProductsByName, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @GetMapping("/{category}")
    // public ResponseEntity<Product[]> searchProductsByCategory(@RequestParam String category) {
    //     LOG.info("GET /products/?category=" + category);

    //     try {
    //         Product[] searchProductsByCategory = productDao.findProducts(category);
    //         if (searchProductsByCategory!= null)
    //             return new ResponseEntity<Product[]>(searchProductsByCategory, HttpStatus.OK);
    //         else
    //             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //     } catch (IOException e) {
    //         LOG.log(Level.SEVERE, e.getLocalizedMessage());
    //         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }

        /**
     * Handles the HTTP POST request to create a new product
     * 
     * @param product The product to create
     * @return The HTTP response
     */
    @PostMapping("")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        LOG.info("POST /products " + product);
        try {
            Product newProduct = productDao.createProduct(product);
            if (newProduct != null)
                return new ResponseEntity<Product>(newProduct, HttpStatus.CREATED);
            else
                return new ResponseEntity<>(HttpStatus.CONFLICT);
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
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        LOG.info("PUT /products " + product);
        try {
            if(product.getPrice() < 0){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if(product.getQuantity() < 0){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            Product product2 = productDao.updateProduct(product);
            if (product2 != null)
                return new ResponseEntity<Product>(product2,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    // @PutMapping("")
    // public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
    //     LOG.info("PUT /Productes " + product);
    // }

   /**
    * Handles the HTTP DELETE request to delete an existing product
    * @param id The id of the product to delete
    * @return The HTTP response
    */
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable int id) {
        LOG.info("DELETE /Products/" + id);
        try {
            boolean result = productDao.deleteProduct(id);
            if (result)
                return new ResponseEntity<>(HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
