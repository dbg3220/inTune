package estoreapi.persistence;

import java.io.IOException;
import estoreapi.model.Product;

/**
 * Defines the operations for product object persistence
 * 
 * @author Damon Gonzalez
 */
public interface ProductDAO {
    /**
     * Retrieves all products
     * @return An array of products, may be empty
     * @throws IOException if an issue with underlying storage
     */
    Product[] getProducts() throws IOException;

    /**
     * Retrieves a product with the given id
     * @param id The id of the product to get
     * @return The product with the matching id, null if no product found
     * @throws IOException if an issue with underlying storage
     */
    Product getProduct(int id) throws IOException;

    /**
     * Creates and saves a product
     * @param product The product to create
     * @return The newly created product if successful, null otherwise 
     * @throws IOException if an issue with underlying storage
     */
    Product createProduct(Product product) throws IOException;

    /**
     * Updates and saves a product
     * @param product The product to be updated and saved
     * @return The updated product if successful, null if
     * product could not be found
     * @throws IOException if underlying storage cannot be accessed
     */
    Product updateProduct(Product product) throws IOException;

    /**
     * Deletes a product with the given id
     * @param id The id of the product
     * @return true if the product was deleted
     * false if product with the given id does not exist
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteProduct(int id) throws IOException;
}
