package estoreapi.persistence;

import java.io.IOException;

import estoreapi.model.Cart;
import estoreapi.model.Product;

/**
 * Defines the interface for Cart object persistence
 * 
 * @author Clayton Acheson
 * 
 */
public interface CartDAO {
    /**
     * Retrieves all {@linkplain Carts}
     * @return An array of {@link Cart} objects, may be empty
     * @throws IOException if an issue with underlying storage
     */
    Cart[] getCarts() throws IOException;

    /**
     * Retrieves a {@linkplain Cart} with the given id
     * @param id The id of the {@link Cart} to get
     * @return a {@link Cart} object with the matching id
     * null if no {@link Cart} with a matching id is found
     * @throws IOException if an issue with underlying storage
     */
    Cart retrieveCart(int id) throws IOException;

    /**
     * Updates and saves a {@linkplain Cart}
     * @param {@link Cart} object to be updated and saved
     * @return updated {@link Cart} if successful, null if
     * {@link Cart} could not be found
     * @throws IOException if underlying storage cannot be accessed
     */
    Cart addItem(Cart Cart, Product item, Integer quantity) throws IOException;

    /**
     * Updates and saves a {@linkplain Cart}
     * @param {@link Cart} object to be updated and saved
     * @return updated {@link Cart} if successful, null if
     * {@link Cart} could not be found
     * @throws IOException if underlying storage cannot be accessed
     */
    Cart removeItem(Cart Cart, Product item, Integer quantity) throws IOException;


    /**
     * Deletes a {@linkplain Cart} with the given id
     * @param id The id of the {@link Cart}
     * @return true if the {@link Cart} was deleted
     * false if cart with the given id does not exist
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteCart(int id) throws IOException;
}
