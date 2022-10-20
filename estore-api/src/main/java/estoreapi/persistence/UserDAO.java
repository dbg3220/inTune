package estoreapi.persistence;

import java.io.IOException;

import estoreapi.model.User;

/**
 * Defines the interface for user object persistence
 * 
 * @author Damon Gonzalez
 * 
 */
public interface UserDAO {

    /**
     * Retrieves all {@linkplain users}
     * 
     * @return An array of {@link user} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    User[] getUsers() throws IOException;

    /**
     * Retrieves a {@linkplain user} with the given id
     * 
     * @param id The id of the {@link user} to get
     * 
     * @return a {@link user} object with the matching id
     * 
     * null if no {@link user} with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    User getUser(int id) throws IOException;
    
    /**
     * Finds a {@linkplain user} with the given username
     * 
     * @param username The text to match against
     * 
     * @return A single {@link user} object with the matching username
     * 
     * null if no {@link user} with a matching username is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    User findUser(String username) throws IOException;

    /**
     * Finds all {@linkplain users} whose username contains the given text
     * 
     * @param username The text to match against
     * 
     * @return An array of {@link users} whose usernames contains the given text, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    User[] findUsers(String username) throws IOException;

    /**
     * Creates and saves a {@linkplain user}
     * 
     * @param user {@linkplain user} object to be created and saved

     * The id of the user object is ignored and a new uniqe id is assigned
     *
     * @return new {@link user} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    User createUser(User user) throws IOException;
    
    /**
     * Updates and saves a {@linkplain user}
     * 
     * @param {@link user} object to be updated and saved
     * 
     * @return updated {@link user} if successful, null if
     * {@link user} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    User updateUser(User user) throws IOException;

    /**
     * Deletes a {@linkplain user} with the given id
     * 
     * @param id The id of the {@link user}
     * 
     * @return true if the {@link user} was deleted
     *
     * false if user with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteUser(int id) throws IOException;

}
