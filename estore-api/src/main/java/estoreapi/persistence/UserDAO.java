package estoreapi.persistence;

import java.io.IOException;

import estoreapi.model.Cart;
import estoreapi.model.User;

/**
 * Defines the interface for user object persistence
 * 
 * @author Damon Gonzalez
 * 
 */
public interface UserDAO {

    /**
     * Gets all users
     * 
     * @return An array of {@link User} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    User[] getUsers() throws IOException;

    /**
     * Gets a {@linkplain User} with the given id
     * 
     * @param id The id of the {@link User} to get
     * 
     * @return a {@link User} object with the matching id
     * 
     * null if no {@link User} with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    User getUser(int id) throws IOException;
    
    /**
     * Finds a {@linkplain User} with the given username
     * 
     * @param username The text to match against
     * 
     * @return A single {@link User} object with the matching username
     * 
     * null if no {@link User} with a matching username is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    User findUser(String username) throws IOException;

    /**
     * Finds all users whose username contains the given text
     * 
     * @param username The text to match against
     * 
     * @return An array of users whose usernames contains the given text, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    User[] findUsers(String containsText) throws IOException;

    /**
     * Creates and saves a {@linkplain User}
     * 
     * @param user {@linkplain User} object to be created and saved

     * The id of the user object is ignored and a new uniqe id is assigned
     *
     * @return new {@link User} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    User createUser(User user) throws IOException;

    /**
     * Deletes a {@linkplain User} with the given id
     * 
     * @param id The id of the {@link User}
     * 
     * @return true if the {@link User} was deleted
     *
     * false if user with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteUser(int id) throws IOException;

      /**
     * Retrieves a {@linkplain Cart} with the given id
     * @param id The id of the {@link Cart} to get
     * @return a {@link Cart} object with the matching id
     * null if no {@link Cart} with a matching id is found
     * @throws IOException if an issue with underlying storage
     */
    Cart getCart(int id) throws IOException;

    /**
     * Updates a {@linkplain Cart}
     * @param cart The cart with the updated values
     * @return The new updated {@linkplain Cart}, null if no {@link Cart}
     *  with a matching id is found
     * @throws IOException
     */
    Cart updateCart(User user, Cart cart) throws IOException;

    User updateUser(User user) throws IOException;

    Cart[] getCarts() throws IOException;

}
