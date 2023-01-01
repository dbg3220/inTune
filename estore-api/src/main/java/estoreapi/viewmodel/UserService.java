package estoreapi.viewmodel;

import java.io.IOException;

import org.springframework.stereotype.Component;

import estoreapi.model.User;
import estoreapi.model.Cart;
import estoreapi.model.Lesson;
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
     * 
     * -When a user is updated its cart must be checked to ensure
     * the products in it exist and the quantities in it do not exceed that of the inventory.
     * @param userDAO The DAO for users
     * @param productDAO The DAO for products
     * @param user The user to update
     * @return true if the operation was successful, false otherwise
     * @throws IOException
     */
    protected boolean updateUser(DAO<User> userDAO, DAO<Product> productDAO, User user) throws IOException{
        Cart cart = user.getCart();
        int[] productIDS = cart.getProductIDS();
        int[] quantities = cart.getQuantities();
        for(int i = 0; i < productIDS.length; i++){
            int thisID = productIDS[i];
            int thisQuantity = quantities[i];
            Product product;
            if((product = productDAO.getItem(thisID)) == null)
                return false;
            if(product.getQuantity() > thisQuantity)
                return false;
        }
        userDAO.updateItem(user);
        return true;
    }

    /**
     * Handles the business logic behind deleting a user
     * 
     * -lessons are associated with users by id so when a user is deleted any
     * lessons that the user were connected must be reverted to a free lesson
     * to ensure other users can schedule them.
     * @param userDAO The DAO for users
     * @param lessonDAO The DAO for lessons
     * @param user The user to delete
     * @return true if the operation was successful, false otherwise
     * @throws IOException
     */
    protected boolean deleteUser(DAO<User> userDAO, DAO<Lesson> lessonDAO, User user) throws IOException{
        int userID = user.getId();
        Lesson[] lessons = lessonDAO.getItems();
        for(Lesson lesson : lessons){
            if(lesson.getUserID() == userID)
                lesson.setUserID(-1);
        }
        return true;
    }
}
