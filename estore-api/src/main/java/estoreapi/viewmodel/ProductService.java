package estoreapi.viewmodel;

import org.springframework.stereotype.Component;

import estoreapi.model.Product;
import estoreapi.model.User;
import estoreapi.persistence.DAO;

/**
 * Component class to handle the business logic of the product resource
 * 
 * @author Damon Gonzalez
 */
@Component
public class ProductService {
    
    /**
     * Handles the business logic behind updating a product
     * @param productDAO The DAO for products
     * @param userDAO The DAO for users
     * @param product The product to update
     * @return true if the operation was successful, false otherwise
     */
    protected boolean updateProduct(DAO<Product> productDAO, DAO<User> userDAO, Product product){
        //TODO
        return false;
    }

    /**
     * Handles the business logic behind deleting a product
     * @param productDAO The DAO for products
     * @param userDAO The DAO for users
     * @param product The product to delete
     * @return true if the operation was successful, false otherwise
     */
    protected boolean deleteProduct(DAO<Product> productDAO, DAO<User> userDAO, Product product){
        //TODO
        return false;
    }
}
