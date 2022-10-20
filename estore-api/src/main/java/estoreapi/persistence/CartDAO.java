package estoreapi.persistence;

import java.io.IOException;

import estoreapi.model.Cart;
import estoreapi.model.Product;
import estoreapi.model.User;

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
     * Creates and saves a {@linkplain lesson}
     * 
     * @param Cart {@linkplain Cart} object to be created and saved

     * The id of the Cart object is ignored and a new uniqe id is assigned
     *
     * @return new {@link cart} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    Cart createCart(Cart cart) throws IOException;


    /**
     * Updates and saves a {@linkplain Cart}
     * @param {@link Cart} object to be updated and saved
     * @return updated {@link Cart} if successful, null if
     * {@link Cart} could not be found or invalid quantity given
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

/**
     * Updates and saves a {@linkplain Cart}
     * 
     * @param {@link Cart} object to be updated and saved
     * 
     * @return updated {@link Cart} if successful, null if
     * {@link Cart} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Cart updateCart(Cart Cart) throws IOException;


}


