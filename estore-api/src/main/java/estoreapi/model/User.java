package estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a user
 * 
 * @author Damon Gonzalez
 */
public class User {

    static final String STRING_FORMAT = "User [id=%d, username=%s]";

    @JsonProperty("id") private int id;
    @JsonProperty("username") private String username;
    @JsonProperty("cart") private Cart cart;
    @JsonProperty("productsPurchased") private int[] productsPurchased;

    /**
     * Create a user with the given id and username
     * @param id The id of the user
     * @param username The username of the user
     *  
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public User(@JsonProperty("id") int id, 
                @JsonProperty("username") String username, 
                @JsonProperty("cart") Cart cart, 
                @JsonProperty("productsPurchased") int[] productsPurchased) {
        this.id = id;
        this.username = username;
        this.cart = cart;
        this.productsPurchased = productsPurchased;
    }

    /**
     * Retrieves the id of the user
     * @return The id of the user
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the username of the user
     * @return The username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Retrieves the cart of the user
     * @return The cart of the user
     */
    public Cart getCart() {
        return cart;
    }

    /**
     * Retrieves a list of the product ID's the user has purchased
     * @return a list of the product ID's the user has purchased
     */
    public int[] getProductsPurchased() {
        return productsPurchased;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id,username,cart,productsPurchased);
    }
}