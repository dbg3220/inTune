package estoreapi.viewmodel;

import java.io.IOException;

import org.springframework.http.HttpStatus;
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
     * Searches for a user in the database with a matching username. Returns
     * that user if it exists, null otherwise
     * @param userDAO The DAO for users
     * @param username The username to search against
     * @return A user if found, null otherwise
     */
    protected User searchUser(DAO<User> userDAO, String username) throws IOException{
        User[] users = userDAO.getItems();
        for(User user : users){
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    /**
     * Handles the business logic behind updating a user
     * 
     * -When a user is updated its cart must be checked to ensure
     * the products in it exist and the quantities in it do not exceed that of the inventory.
     * @param userDAO The DAO for users
     * @param productDAO The DAO for products
     * @param user The user to update
     * @return the http status representing the operation's outcome
     * @throws IOException
     */
    protected HttpStatus updateUser(DAO<User> userDAO, DAO<Product> productDAO, User user) throws IOException{
        if(userDAO.getItem(user.getId()) == null){
            return HttpStatus.NOT_FOUND;
        }
        Cart cart = user.getCart();
        int[] productIDS = cart.getProductIDS();
        int[] quantities = cart.getQuantities();
        for(int i = 0; i < productIDS.length; i++){
            int thisID = productIDS[i];
            int thisQuantity = quantities[i];
            Product product;
            if((product = productDAO.getItem(thisID)) == null || product.getQuantity() > thisQuantity)
                return HttpStatus.BAD_REQUEST;
        }
        userDAO.updateItem(user);
        return HttpStatus.OK;
    }

    /**
     * Handles the business logic behind deleting a user
     * 
     * -lessons are associated with users by id so when a user is deleted any
     * lessons that the user were connected to must be reverted to a free lesson
     * to ensure other users can schedule them.
     * @param userDAO The DAO for users
     * @param lessonDAO The DAO for lessons
     * @param user The user to delete
     * @return the http status representing the operation's outcome
     * @throws IOException
     */
    protected HttpStatus deleteUser(DAO<User> userDAO, DAO<Lesson> lessonDAO, int id) throws IOException{
        if(userDAO.getItem(id) == null){
            return HttpStatus.NOT_FOUND;
        }
        Lesson[] lessons = lessonDAO.getItems();
        for(Lesson lesson : lessons){
            if(lesson.getUserID() == id)
                lesson.setUserID(-1);
        }
        return HttpStatus.OK;
    }
}
