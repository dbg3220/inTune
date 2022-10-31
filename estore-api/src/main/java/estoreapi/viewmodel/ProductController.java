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
 * Handles the REST API requests for the product resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST
 * API
 * method handler to the Spring framework
 * 
 * @author Hayden Cieniawski
 * @author Clayton Acheson
 * @author Damon Gonzalez
 */
@RestController
@RequestMapping("products")
public class ProductController {
    private static final Logger LOG = Logger.getLogger(ProductController.class.getName());
    private ProductDAO productDao;
    private CartDAO cartDao;

    /**
     * Creates a REST API controller to respond to requests
     * 
     * @param productDao The {@link ProductDAO Product Data Access Object} to
     *                   perform CRUD operations
     *                   <br>
     * @param cartDao    The {@link CartDAO Cart Data Access Object} to perform
     *                   perform CRUD operations
     *                   
     * These dependencies are injected by the spring framework
     */
    public ProductController(ProductDAO productDao, CartDAO cartDao) {
        this.productDao = productDao;
        this.cartDao = cartDao;
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
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
            Product[] products = productDao.getProducts();
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    }
    /**
     * Handles the HTTP GET request for the product resource
     * @param name The text to search against
     * @return All products whose name includes the specified parameter
     */
    @GetMapping("/")
    public ResponseEntity<Product[]> findProducts(@RequestParam String name) {
        LOG.info("GET /products/?name=" + name);
        try {
            Product[] products = productDao.findProducts(name);
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles the HTTP POST request for the product resource
     * @param product The product to create
     * @return The product that was created including a unique identifier id that
     *  was assigned by the server
     */
    @PostMapping("")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        LOG.info("POST /products " + product);
        try {
            Product newProduct = productDao.createProduct(product);
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
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            if(product.getQuantity() < 0){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Product newProduct = productDao.updateProduct(product);
            if (newProduct != null){
                int productID = newProduct.getId();
                Cart[] carts = cartDao.getCarts();
                for(Cart cart : carts){
                    boolean adjustCart = false;//becomes true if the cart needs to be changed by the dao
                    ArrayList<Integer> productIDS = cart.getProductIDS();
                    ArrayList<Integer> quantities = cart.getQuantities();
                    for(int i = 0; i < productIDS.size(); i++){
                        if(productIDS.get(i) == productID && quantities.get(i) > newProduct.getQuantity()){
                            adjustCart = true;
                            quantities.set(i, newProduct.getQuantity());
                        }
                    }
                    if(adjustCart){
                        Cart newCart = new Cart(cart.getId(), productIDS, quantities);
                        cartDao.updateCart(newCart);
                    }
                }
                return new ResponseEntity<Product>(newProduct,HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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
            if (result){
                Cart[] carts = cartDao.getCarts();
                for(Cart cart : carts){
                    boolean adjustCart = false;//becomes true if the cart needs to be changed by the dao
                    ArrayList<Integer> productIDS = cart.getProductIDS();
                    ArrayList<Integer> quantities = cart.getQuantities();
                    for(int i = 0; i < productIDS.size(); i++){
                        if(productIDS.get(i) == id){
                            adjustCart = true;
                            productIDS.remove(i);
                            quantities.remove(i);
                        }
                    }
                    if(adjustCart){
                        Cart newCart = new Cart(cart.getId(), productIDS, quantities);
                        cartDao.updateCart(newCart);
                    }
                }
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
