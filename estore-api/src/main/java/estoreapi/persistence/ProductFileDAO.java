package estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import estoreapi.model.Product;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
* Implements methods described in ProductDAO
* 
* @author Damon Gonzalez
*/
@Component
public class ProductFileDAO implements ProductDAO {

    /** The map storing products that are mapped from their id */
    Map<Integer, Product> products;
    /** Class used to write java objects to json files */
    private ObjectMapper objectMapper;
    /** The next id of a product, to be only accessed through nextId() */
    private static int nextId;
    /** The file to read/write to */
    private String filename;

    /**
     * Public constructor for ProductFileDAO
     * @param filename The path of the file to read/write
     * @param objectMapper Provides JSON Object serialization and deserialization
     * @throws IOException when file cannot be accessed or read from
     */
    public ProductFileDAO(@Value("${products.file}") String filename,
                          ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    /**
     * Provides the next id for a product to be created with in order
     * to preserve uniqueness.
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Saves product objects in the map of products to file storage
     * @throws IOException when file cannot be accessed or written to
     */
    private void save() throws IOException {
        Product[] productArray = getProductsArray();
        objectMapper.writeValue(new File(filename),productArray);
    }

    /**
     * Loads product objects from file storage
     * @throws IOException when file cannot be accessed or read from
     */
    private void load() throws IOException {
        products = new TreeMap<>();
        nextId = 0;
        Product[] productArray = objectMapper.readValue(new File(filename),Product[].class);
        for (Product product : productArray) {
            products.put(product.getId(), product);
            if (product.getId() > nextId)
                nextId = product.getId();
        }
        ++nextId;//make the next id greater than the last
    }
    
    /**
     * Generates an array of products from the map of products
     * 
     * @return The array of products, may be empty
     */
    private Product[] getProductsArray() {
        ArrayList<Product> productArrayList = new ArrayList<>();
        for (Product product : products.values()) {
            productArrayList.add(product);
        }
        Product[] productArray = new Product[productArrayList.size()];
        productArrayList.toArray(productArray);
        return productArray;
    }

    @Override
    public Product[] getProducts() {
        synchronized(products) {
            return getProductsArray();
        }
    }

    @Override
    public Product getProduct(int id) {
        synchronized(products) {
            if (products.containsKey(id))
                return products.get(id);
            else
                return null;
        }
    }

    @Override
    public Product createProduct(Product product) throws IOException {
        synchronized(products) {
            // We create a new Product object because the id field is immutable
            // and we need to assign the next unique id
            Product newProduct = new Product(nextId(), product.getName(), product.getPrice(), product.getCategory(),
                                             product.getQuantity(), product.getDescription(), product.getImage(),
                                             product.getReviews());
            products.put(newProduct.getId(),newProduct);
            save(); // may throw an IOException
            return newProduct;
        }
    }

    @Override
    public Product updateProduct(Product product) throws IOException {
        synchronized(products) {
            if (products.containsKey(product.getId()) == false)
                return null;  // product does not exist

            products.put(product.getId(),product);
            save(); // may throw an IOException
            return product;
        }
    }

    @Override
    public boolean deleteProduct(int id) throws IOException {
        synchronized(products) {
            if (products.containsKey(id)) {
                products.remove(id);
                save();
                return true;
            }
            else
                return false;
        }
    }
}
