package estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a user
 * 
 * @author Damon Gonzalez
 */
public class User {

    /** Format string for a User */
    static final String STRING_FORMAT = "User [id=%d, username=%s, cart=%s]";

    /** The id of the user */
    @JsonProperty("id") private int id;
    /** The username of the user */
    @JsonProperty("username") private String username;
    /** The cart of this user */
    @JsonProperty("cart") private Cart cart;

    /**
     * Public constructor to deserialize user objects from a json file
     */
    public User(@JsonProperty("id") int id, 
                @JsonProperty("username") String username, 
                @JsonProperty("cart") Cart cart) {
        this.id = id;
        this.username = username;
        this.cart = cart;
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

    @Override
    public String toString() {
        return String.format(STRING_FORMAT, id, username, cart.toString());
    }

    /**
     * This equals() disregards the id and cart attributes for comparison.
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof User){
            User other = (User) obj;
            return this.username.equals(other.username);
        }
        return false;
    }
}
