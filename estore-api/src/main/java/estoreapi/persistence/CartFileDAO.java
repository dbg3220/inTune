package estoreapi.persistence;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import estoreapi.model.Cart;
import estoreapi.model.Product;

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
    Map<Integer,Cart> Carts;   // Provides a local cache of the Cart objects
                                           // so that we don't need to read from the file
                                           // each time
    private ObjectMapper objectMapper;  // Provides conversion between Cart
                                        // objects and JSON text format written
                                        // to the file
    private static int nextId;  // The next Id to assign to a new Cart
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
     * Generates the next id for a new {@linkplain Cart Cart}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of {@linkplain Cart carts} from the tree map
     * 
     * @return  The array of {@link Cart carts}, may be empty
     */
    private Cart[] getCartArray() {
        return getCartsArray(null);
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
    private Cart[] getCartsArray(String containsText) { // if containsText == null, no filter
        ArrayList<Cart> CartArrayList = new ArrayList<>();

        for (Cart Cart : Carts.values()) {
            if (containsText == null || Cart.getId() == Integer.parseInt(containsText)) {
                CartArrayList.add(Cart);
            }
        }

        Cart[] CartArray = new Cart[CartArrayList.size()];
        CartArrayList.toArray(CartArray);
        return CartArray;
    }

    /**
     * Saves the {@linkplain Cart Carts} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Cart Carts} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Cart[] CartArray = getCartArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),CartArray);
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
        Carts = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of Carts
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Cart[] CartArray = objectMapper.readValue(new File(filename),Cart[].class);

        // Add each Cart to the tree map and keep track of the greatest id
        for (Cart Cart : CartArray) {
            Carts.put(Cart.getId(), Cart);
            if (Cart.getId() > nextId)
                nextId = Cart.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Cart[] getCarts() {
        synchronized(Carts) {
            return getCartArray();
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Cart retrieveCart(int id) {
        synchronized(Carts) {
            if (Carts.containsKey(id))
                return Carts.get(id);
            else
                return null;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Cart addItem(Cart Cart, Product item, Integer quantity) throws IOException {
        synchronized(Carts) {
            if (Carts.containsKey(Cart.getId()) == false){
                return null;  // Cart does not exist
            }
            
            if (Carts.get(Cart.getId()).containsKey(item)){
                if(quantity > item.getQuantity()){
                    System.out.println("Invalid item adding");
                    return null;
                }
                double y = Cart.getQuantity(item) + (double) quantity;
                double x = item.getPrice() * y;
                Cart.setTotal(Cart.getTotal() - x);
                save();
                return Cart;
            }

            Cart.additem(item, quantity);
            save();
            return Cart;
            
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Cart removeItem(Cart Cart, Product item, Integer quantity) throws IOException {
        synchronized(Carts) {
            if (Carts.containsKey(Cart.getId()) == false){
                return null;  // Cart does not exist
            }
            
            if (Carts.get(Cart.getId()).containsKey(item)){
                double y = Cart.getQuantity(item) - (double) quantity;
                if( y < 0){
                    System.out.println("Invalid removal request");
                    return null;
                }
                double x = item.getPrice() * y;
                Cart.setTotal(Cart.getTotal() - x);
                Cart.removeitem(item, quantity);
                save();
                return Cart;
            }
            return null;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteCart(int id) throws IOException {
        synchronized(Carts) {
            if (Carts.containsKey(id)) {
                Carts.remove(id);
                return save();
            }
            else
                return false;
        }
    }
}
