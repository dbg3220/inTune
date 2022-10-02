package estoreapi.model;

import java.util.Hashtable;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents an product
 * METHODS NEED TO BE UPDATED TO SEND INFORMATION TO THE SUPERCLASS
 * 
 * @author Hayden Cieniawski
 */
public class Cart {



    // Package private for tests
    static final String STRING_FORMAT = "Cart [id=%d]";

    @JsonProperty("id") private int id;
    @JsonProperty("items") private Product[] items;
    @JsonProperty("totalPrice") private double total;


    
    public Cart (@JsonProperty("id") int id, @JsonProperty("items") Product[] items, @JsonProperty("totalPrice") double total) {
        this.id = id;
        this.items = items;
        this.total = total;

    }

    /**
     * Retrieves the id of the cart
     * @return The id of the cart
     */
    public int getid() {
        return id;
    }

    /**
     * Retrieves the items in the cart
     * @return The items in the cart
     */
    public Product[] getItems() {
        return items;
    }

    /**
     * Retrieves the total price of the cart
     * @return The total price of the cart
     */
    public double getTotal() {
        return total;
    }

    /**
     * copys the current product array and adds the new product
     * then sets the current product array to the new one
     */
    public additem(Product item) {
        
    }

    /**
     * copys the current product array and removes the product
     * then sets the current product array to the new one
     */
    public removeitem(Product item) {

    }



    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id);
    }
}