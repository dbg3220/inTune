package estoreapi.persistence;

import java.io.IOException;

/**
 * Defines the operations for model object persistence. Is generic so
 * that multiple types can be implemented with this interface.
 * 
 * In the context of this module the keyword 'item' is used to represent
 * any object that needs to persist in file storage.
 * 
 * @author Damon Gonzalez
 */
public interface DAO<T> {
    /**
     * Retrieves all items
     * @return An array of items, may be empty
     * @throws IOException if an issue with underlying storage
     */
    T[] getItems() throws IOException;

    /**
     * Retrieves an item with the given id
     * @param id The id of the item to get
     * @return The item with the matching id, null if no item found
     * @throws IOException if an issue with underlying storage
     */
    T getItem(int id) throws IOException;

    /**
     * Creates and saves an item. When creating an item certain attributes
     * of the item passed in as a parameter may be ignored due to the logic of
     * the domain of the item.
     * @param item The item to create
     * @return The newly created item if successful, null otherwise 
     * @throws IOException if an issue with underlying storage
     */
    T createItem(T item) throws IOException;

    /**
     * Updates and saves an item
     * @param item The item to be updated and saved
     * @return The updated item if successful, null if item with matching
     * id could not be found
     * @throws IOException if underlying storage cannot be accessed
     */
    T updateItem(T item) throws IOException;

    /**
     * Deletes an item with the given id
     * @param id The id of the item
     * @return true if the item was deleted
     * false if item with the given id does not exist
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteItem(int id) throws IOException;
}
