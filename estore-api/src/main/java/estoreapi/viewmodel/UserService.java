package estoreapi.viewmodel;

import org.springframework.stereotype.Component;

import estoreapi.model.User;
import estoreapi.model.Product;
import estoreapi.persistence.DAO;

/**
 * Component class to handle the business logic of the user resource
 * 
 * @author Damon Gonzalez
 */
@Component
public class UserService {
    
    /**
     * Handles the business logic behind updating a user
     * @param userDAO The DAO for users
     * @param productDAO The DAO for products
     * @param user The user to update
     * @return true if the operation was successful, false otherwise
     */
    protected boolean updateUser(DAO<User> userDAO, DAO<Product> productDAO, User user){
        //TODO
        return false;
    }
}
