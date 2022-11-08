package estoreapi.persistence;

import java.io.IOException;

import estoreapi.model.Cart;

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
    Cart getCart(int id) throws IOException;

    /**
     * Creates and saves a {@linkplain Cart}
     * @return The new created {@linkplain Cart}
     * @throws IOException if an issue with underlying storage
     */
    Cart createCart(int id) throws IOException;

    /**
     * Updates a {@linkplain Cart}
     * @param cart The cart with the updated values
     * @return The new updated {@linkplain Cart}, null if no {@link Cart}
     *  with a matching id is found
     * @throws IOException
     */
    Cart updateCart(Cart cart) throws IOException;

    /**
     * Deletes a {@linkplain Cart} with the given id
     * @param id The id of the {@link Cart}
     * @return true if the {@link Cart} was deleted
     * false if cart with the given id does not exist
     * @throws IOException if an issue with underlying storage
     */
    boolean deleteCart(int id) throws IOException;
}
