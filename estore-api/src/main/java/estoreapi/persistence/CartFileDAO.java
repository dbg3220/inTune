package estoreapi.persistence;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import estoreapi.model.Cart;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Implements the functionality for JSON file-based peristance for Carts
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 * 
 * @author Clayton Acheson
 */
@Component
public class CartFileDAO implements CartDAO {
    private static final Logger LOG = Logger.getLogger(CartFileDAO.class.getName());
    Map<Integer,Cart> carts;   // Provides a local cache of the Cart objects
                                           // so that we don't need to read from the file
                                           // each time
    private ObjectMapper objectMapper;  // Provides conversion between Cart
                                        // objects and JSON text format written
                                        // to the file
    private String filename;    // Filename to read from and write to

    /**
     * Creates a Cart File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public CartFileDAO(@Value("${Carts.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the Carts from the file
    }

    /**
     * Generates an array of {@linkplain Cart Carts} from the tree map for any
     * {@linkplain Cart Carts} that contains the text specified by containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain Cart Carts}
     * in the tree map
     * 
     * @return  The array of {@link Cart Carts}, may be empty
     */
    private Cart[] getCartsArray() { // if containsText == null, no filter
        ArrayList<Cart> cartList = new ArrayList<>();

        for (Cart cart : carts.values()) {
            cartList.add(cart);
        }

        Cart[] cartArray = new Cart[cartList.size()];
        cartList.toArray(cartArray);
        return cartArray;
    }

    /**
     * Saves the {@linkplain Cart Carts} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Cart Carts} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Cart[] cartArray = getCartsArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename), cartArray);
        return true;
    }

    /**
     * Loads {@linkplain Cart Carts} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        carts = new TreeMap<>();

        // Deserializes the JSON objects from the file into an array of Carts
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Cart[] cartArray = objectMapper.readValue(new File(filename),Cart[].class);

        // Add each Cart to the tree map
        for (Cart cart : cartArray) {
            carts.put(cart.getId(), cart);
        }
        // Make the next id one greater than the maximum from the file
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Cart[] getCarts() {
        synchronized(carts) {
            return getCartsArray();
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Cart getCart(int id) {
        synchronized(carts) {
            if (carts.containsKey(id))
                return carts.get(id);
            else
                return null;
        }
    }

    /**
    ** {@inheritDoc} 
     */
    @Override
    public Cart updateCart(Cart cart) throws IOException {
        synchronized(carts){
            if(!carts.containsKey(cart.getId())){
                return null;
            }
            carts.put(cart.getId(), cart);
            save();
            return cart;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Cart createCart(int id) throws IOException {
        synchronized(carts) {
            // Checks if a cart with the same id was created
            if(carts.containsKey(id)){
                return null;
            }
            // We create a new Cart object because the id field is immutable
            // and we need to assign the next unique id
            Cart newCart = new Cart(id);
            carts.put(newCart.getId(),newCart);
            save(); // may throw an IOException
            return newCart;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteCart(int id) throws IOException {
        synchronized(carts) {
            if (carts.containsKey(id)) {
                carts.remove(id);
                return save();
            }
            else
                return false;
        }
    }
}
