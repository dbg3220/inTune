package estoreapi.persistence;

import java.io.IOException;

import estoreapi.model.Instrument;
import estoreapi.model.Product;
import estoreapi.model.Lesson;

/**
 * Defines the interface for product object persistence
 * 
 * @author Hayden Cieniawski
 * 
 */
public interface ProductDAO {
    /**
     * Retrieves all {@linkplain products}
     * 
     * @return An array of {@link product} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Product[] getProducts() throws IOException;

    /**
     * Finds all {@linkplain products} whose name contains the given text
     * 
     * @param containsText The text to match against
     * 
     * @return An array of {@link products} whose names contains the given text, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Product[] findProducts(String containsText) throws IOException;

    /**
     * Retrieves a {@linkplain product} with the given id
     * 
     * @param id The id of the {@link product} to get
     * 
     * @return a {@link product} object with the matching id
     * 
     * null if no {@link product} with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Product getProduct(int id) throws IOException;

    
    /**
     * Retrieves a {@linkplain product} with the given id
     * 
     * @param id The id of the {@link product} to get
     * 
     * @return a {@link product} object with the matching id
     * 
     * null if no {@link product} with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Product findProductCategory(int id) throws IOException;

    Product findProductSubcategory(int id) throws IOException;

    /**
     * Creates and saves a {@linkplain lesson}
     * 
     * @param product {@linkplain product} object to be created and saved

     * The id of the product object is ignored and a new uniqe id is assigned
     *
     * @return new {@link product} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    Product createInstrument(Instrument instrument) throws IOException;


    /**
     * Creates and saves a {@linkplain lesson}
     * 
     * @param product {@linkplain product} object to be created and saved

     * The id of the product object is ignored and a new uniqe id is assigned
     *
     * @return new {@link product} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    Product createLesson(Lesson lesson) throws IOException;

    /**
     * Updates and saves a {@linkplain product}
     * 
     * @param {@link product} object to be updated and saved
     * 
     * @return updated {@link product} if successful, null if
     * {@link product} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Product updateProduct(Product product) throws IOException;

    /**
     * Deletes a {@linkplain product} with the given id
     * 
     * @param id The id of the {@link product}
     * 
     * @return true if the {@link product} was deleted
     *
     * false if product with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteProduct(int id) throws IOException;


}
