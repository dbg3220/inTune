package viewmodel;

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

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.persistence.ProductDAO;
import model.Product;

/**
 * Handles the REST API requests for the product resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
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
     * @param ProductDao The {@link ProductDAO Instrument Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public ProductController(ProductDAO productDao) {
        this.productDao = productDao;
    }

   
    // @GetMapping("/{id}")
    // public ResponseEntity<Instrument> getInstrument(@PathVariable int id) {
    //     LOG.info("GET /Instruments/" + id);        
    // }

   
    @GetMapping("")
    public ResponseEntity<Product[]> getProducts() {
        LOG.info("GET /products");
        try {
            Product[] products = productDao.getProducts();
            if (products != null)
                return new ResponseEntity<Product[]>(products,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

       
    }


    // @GetMapping("/")
    // public ResponseEntity<Instrument[]> searchInstrumentes(@RequestParam String name) {
    //     LOG.info("GET /Instrumentes/?name="+name);
       
    // }

   
    @PostMapping("")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        LOG.info("POST /products " + product);
        try {
            Product newProduct= productDao.createProduct(product);
            if (newProduct != null)
                return new ResponseEntity<Product>(newProduct,HttpStatus.CREATED);
            else
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    // @PutMapping("")
    // public ResponseEntity<Instrument> updateInstrument(@RequestBody Instrument Instrument) {
    //     LOG.info("PUT /Instrumentes " + Instrument);
    // }

   
    // @DeleteMapping("/{id}")
    // public ResponseEntity<Instrument> deleteInstrument(@PathVariable int id) {
    //     LOG.info("DELETE /Instrumentes/" + id);
    // }
}

     