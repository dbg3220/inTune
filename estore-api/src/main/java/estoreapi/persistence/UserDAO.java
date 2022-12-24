package estoreapi.persistence;

import java.io.IOException;
import estoreapi.model.User;

/**
 * Defines the operations for user object persistence
 * 
 * @author Damon Gonzalez
 */
public interface UserDAO {
    /**
     * Retrieves all users
     * @return An array of users, may be empty
     * @throws IOException if an issue with underlying storage
     */
    User[] getUsers() throws IOException;

    /**
     * Retrieves a user with the given id
     * @param id The id of the user to get
     * @return The user with the matching id, null if no user found
     * @throws IOException if an issue with underlying storage
     */
    User getUser(int id) throws IOException;

    /**
     * Creates and saves a user
     * @param user The user to create
     * @return The newly created user if successful, null otherwise 
     * @throws IOException if an issue with underlying storage
     */
    User createUser(User user) throws IOException;

    /**
     * Updates and saves a user
     * @param user The user to be updated and saved
     * @return The updated user if successful, null if
     * user could not be found
     * @throws IOException if underlying storage cannot be accessed
     */
    User updateUser(User user) throws IOException;

    /**
     * Deletes a user with the given id
     * @param id The id of the user
     * @return true if the user was deleted
     * false if user with the given id does not exist
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteUser(int id) throws IOException;
}
