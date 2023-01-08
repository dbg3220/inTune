package estoreapi.viewmodel;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import estoreapi.model.Cart;
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
     * 
     * -When a product is updated to have a smaller quantity in the inventory
     * than all of that product's appearances in a users' carts is removed.
     * @param productDAO The DAO for products
     * @param userDAO The DAO for users
     * @param product The product to update
     * @return the http status representing the operation's outcome
     * @throws IOException
     */
    protected HttpStatus updateProduct(DAO<Product> productDAO, DAO<User> userDAO, Product product) throws IOException{
        int id = product.getId();
        Product existing = productDAO.getItem(id);
        if(existing == null){
            return HttpStatus.NOT_FOUND;
        }
        if(existing.getQuantity() < product.getQuantity()){
            User[] users = userDAO.getItems();
            for(User user : users){
                Cart cart = user.getCart();
                int[] productIDS = cart.getProductIDS();
                if(Arrays.asList(productIDS).contains(id)){
                    int[] quantities = cart.getQuantities();
                    int[] newProductIDS = new int[productIDS.length - 1];
                    int[] newQuantities = new int[productIDS.length - 1];
                    int offset = 0;
                    for(int i = 0; i < productIDS.length - 1; i++){
                        if(productIDS[i] == id) offset++;
                        newProductIDS[i + offset] = productIDS[i];
                        newQuantities[i + offset] = quantities[i]; 
                    }
                    Cart newCart = new Cart(newProductIDS, newQuantities);
                    user.setCart(newCart);
                }
            }
        }
        productDAO.updateItem(product);
        return HttpStatus.OK;
    }

    /**
     * Handles the business logic behind deleting a product
     * 
     * -When a product is deleted than all of that product's appearances in
     * users' carts is removed.
     * @param productDAO The DAO for products
     * @param userDAO The DAO for users
     * @param product The product to delete
     * @return the http status representing the operation's outcome
     * @throws IOException
     */
    protected HttpStatus deleteProduct(DAO<Product> productDAO, DAO<User> userDAO, int id) throws IOException{
        Product existing = productDAO.getItem(id);
        if(existing == null){
            return HttpStatus.NOT_FOUND;
        }
        User[] users = userDAO.getItems();
        for(User user : users){
            Cart cart = user.getCart();
            int[] productIDS = cart.getProductIDS();
            if(Arrays.asList(productIDS).contains(id)){
                int[] quantities = cart.getQuantities();
                int[] newProductIDS = new int[productIDS.length - 1];
                int[] newQuantities = new int[productIDS.length - 1];
                int offset = 0;
                for(int i = 0; i < productIDS.length - 1; i++){
                    if(productIDS[i] == id) offset++;
                    newProductIDS[i + offset] = productIDS[i];
                    newQuantities[i + offset] = quantities[i]; 
                }
                Cart newCart = new Cart(newProductIDS, newQuantities);
                user.setCart(newCart);
            }
        }
        productDAO.deleteItem(id);
        return HttpStatus.OK;
    }
}
