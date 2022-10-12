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
import estoreapi.persistence.CartDAO;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles the REST API requests for the Cart resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST
 * API
 * method handler to the Spring framework
 * 
 * @author Hayden Cieniawski
 */

@RestController
@RequestMapping("Carts")
public class CartController {
    private static final Logger LOG = Logger.getLogger(CartController.class.getName());
    private CartDAO CartDao;

    /**
     * Creates a REST API controller to respond to requests
     * 
     * @param CartDao The {@link CartDAO Cart Data Access Object} to
     *                   perform CRUD operations
     *                   <br>
     *                   This dependency is injected by the Spring Framework
     */
    public CartController(CartDAO CartDao) {
        this.CartDao = CartDao;
    }

   /**
    * Handles the HTTP GET request for the Cart resource
    * @param id The id of the Cart to retrieve
    * @return The Cart with the specified id
    */
    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCart(@PathVariable int id) {
        LOG.info("GET /Carts/" + id);   
        try {
            Cart Cart = CartDao.retrieveCart(id);
            if (Cart != null)
                return new ResponseEntity<>(Cart, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * Handles the HTTP GET request for the Cart resource
     * @return All Carts
     */
    @GetMapping("")
    public ResponseEntity<Cart[]> getCarts() {
    LOG.info("GET /Carts");
    try {
        return new ResponseEntity<>(CartDao.getCarts(), HttpStatus.OK);
    } catch (IOException e) {
        LOG.log(Level.SEVERE, "Error getting Carts", e);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    }
    /**
     * Handles the HTTP PUT request to update an existing Cart
     * @param Cart The Cart to update
     * @return The HTTP response
     */
    @PutMapping("")
    public ResponseEntity<Cart> removeItem(@RequestBody Cart Cart, @RequestParam Product item, @RequestParam int quantity) {
        LOG.info("PUT /Carts " + Cart);
        try {
            Cart Cart2 = CartDao.removeItem(Cart, item, quantity);
            if (Cart2 != null)
                return new ResponseEntity<Cart>(Cart2,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles the HTTP PUT request to update an existing Cart
     * @param Cart The Cart to update
     * @return The HTTP response
     */
    @PutMapping("")
    public ResponseEntity<Cart> addItem(@RequestBody Cart Cart, @RequestParam Product item, @RequestParam int quantity) {
        LOG.info("PUT /Carts " + Cart);
        try {
            Cart Cart2 = CartDao.addItem(Cart, item, quantity);
            if (Cart2 != null)
                return new ResponseEntity<Cart>(Cart2,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    // @PutMapping("")
    // public ResponseEntity<Cart> updateCart(@RequestBody Cart Cart) {
    //     LOG.info("PUT /Cartes " + Cart);
    // }

   /**
    * Handles the HTTP DELETE request to delete an existing Cart
    * @param id The id of the Cart to delete
    * @return The HTTP response
    */
    @DeleteMapping("/{id}")
    public ResponseEntity<Cart> deleteCart(@PathVariable int id) {
        LOG.info("DELETE /Carts/" + id);
        try {
            boolean result = CartDao.deleteCart(id);
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