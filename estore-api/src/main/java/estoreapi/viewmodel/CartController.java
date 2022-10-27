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
import estoreapi.persistence.ProductDAO;

import java.io.IOException;
import java.util.ArrayList;
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
@RequestMapping("carts")
public class CartController {
    private static final Logger LOG = Logger.getLogger(CartController.class.getName());
    private CartDAO cartDao;
    private ProductDAO productDao;

    /**
     * Creates a REST API controller to respond to requests
     * 
     * @param cartDao The {@link CartDAO Cart Data Access Object} to
     *                   perform CRUD operations
     * @param productDAO
     */
    public CartController(CartDAO cartDao, ProductDAO productDao) {
        this.cartDao = cartDao;
        this.productDao = productDao;
    }

   /**
    * Handles the HTTP GET request for the Cart resource
    * @param id The id of the Cart to retrieve
    * @return The Cart with the specified id
    */
    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCart(@PathVariable int id) {
        LOG.info("GET /carts/" + id);   
        try {
            Cart cart = cartDao.getCart(id);
            if (cart != null)
                return new ResponseEntity<>(cart, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } 
        catch (IOException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles the HTTP GET request for the Cart resource
     * @return All Carts
     */
    @GetMapping("")
    public ResponseEntity<Cart[]> getCarts() {
        LOG.info("GET /carts");
        try {
            return new ResponseEntity<>(cartDao.getCarts(), HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles the HTTP PUT request for the Cart resource, checks the new cart
     * to ensure that it does not violate the inventory
     * @param cart The updated cart
     * @return The new cart where it is updated
     */
    @PutMapping("")
    public ResponseEntity<Cart> updateCart(@RequestBody Cart cart){
        LOG.info("PUT /carts" + cart);
        try {
            ArrayList<Integer> newProductIDS = cart.getProductIDS();
            ArrayList<Integer> newQuantities = cart.getQuantities();
            for(int i = 0; i < newProductIDS.size(); i++){
                int productID = newProductIDS.get(i);
                int quantity = newQuantities.get(i);
                Product product = productDao.getProduct(productID);
                if(quantity > product.getQuantity()){
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
            Cart newCart = cartDao.updateCart(cart);
            if(newCart != null)
                return new ResponseEntity<Cart>(newCart, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

   /**
    * Handles the HTTP DELETE request to delete an existing Cart
    *
    * WARNING: This is a dangerous method to invoke because it deletes a cart
    *  without deleting the user whose cart it belongs to, used at the admin's
    *  discretion.
    * @param id The id of the Cart to delete
    * @return The HTTP response
    */
    @DeleteMapping("/{id}")
    public ResponseEntity<Cart> deleteCart(@PathVariable int id) {
        LOG.info("DELETE /carts/" + id);
        try {
            boolean result = cartDao.deleteCart(id);
            if (result)
                return new ResponseEntity<>(HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
