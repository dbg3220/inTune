package estoreapi.model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.logging.Logger;

/**
 * Class for the functionality of a Cart within the model-tier
 * 
 * @author Damon Gonzalez
 */
public class Cart {

    protected static final String STRING_FORMAT = "Cart [id=%d]";

    private static final Logger LOG = Logger.getLogger(Cart.class.getName());

    @JsonProperty("id") private int id;
    @JsonProperty("products") private ArrayList<Integer> productIDS;
    @JsonProperty("quantities") private ArrayList<Integer> quantities;

    /**
     * Public constructor for the Cart class
     * @param id The id of the cart, corresponds to the id of the User whose cart
     *  this belongs to
     * @param productIDS A list of products by id that are present in the cart
     * @param quantities A list of quantities for each product where the index of
     * each quantity corresponds to the index of each product id
     */
    public Cart (@JsonProperty("id") int id, 
                 @JsonProperty("products") ArrayList<Integer> productIDS, 
                 @JsonProperty("quantities") ArrayList<Integer> quantities) {
        this.id = id;
        this.productIDS = productIDS;
        this.quantities = quantities;
    }
    
    /**
     * Secondary constructor for use when carts are created in real time by users
     * and they have no products in them
     * @param id The id of the cart, corresponds to the id of the user whose cart
     *  this belongs to
     */
    public Cart (int id){
        this(id, new ArrayList<>(), new ArrayList<>());
    }

    /**
     * Gives the id of the cart
     * @return The id of the cart
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of the cart
     * @param id The new id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gives all product ids present in the cart
     * @return An arraylist of product ids
     */
    public ArrayList<Integer> getProductIDS() {
        return productIDS;
    }

    /**
     * Gives the quantities of the products in the cart
     * @return The quantities of the products
     */
    public ArrayList<Integer> getQuantities() {
        return quantities;
    }

    /**
     * Gives the amount a user has of a certain product in their cart
     * @param id The id of the product to be looked up
     * @return If the product is in the cart than it returns the quantity of the product,
     * returns -1 otherwise
     */
    public int getQuantity(int id){
        int index = productIDS.indexOf(id);
        if(index == -1){
            return -1;
        }
        return quantities.get(index);
    }

    /**
     * Checks if the cart contains a certain product
     * @param id The id of the product to be looked up
     * @return true if present in the cart, false otherwise
     */
    public boolean containsProduct(int id){
        return productIDS.contains(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other){
        if(other instanceof Cart){
            Cart otherCart = (Cart) other;
            return this.id == otherCart.id &&
                   this.productIDS.equals(otherCart.productIDS) &&
                   this.quantities.equals(otherCart.quantities);
        }
        return false;
    }
}