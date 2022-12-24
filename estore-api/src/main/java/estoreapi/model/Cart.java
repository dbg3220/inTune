package estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class for the functionality of a Cart within the model-tier
 * 
 * @author Damon Gonzalez
 */
public class Cart {

    protected static final String STRING_FORMAT = "Cart [id=%d]";

    @JsonProperty("id") private int id;
    @JsonProperty("products") private int[] productIDS;
    @JsonProperty("quantities") private int[] quantities;

    /**
     * Public constructor for the Cart class
     * @param id The id of the cart, corresponds to the id of the User whose cart
     *  this belongs to
     * @param productIDS A list of products by id that are present in the cart
     * @param quantities A list of quantities for each product where the index of
     * each quantity corresponds to the index of each product id
     */
    public Cart (@JsonProperty("id") int id, 
                 @JsonProperty("products") int[] productIDS, 
                 @JsonProperty("quantities") int[] quantities) {
        this.id = id;
        this.productIDS = productIDS;
        this.quantities = quantities;
    }

    /**
     * Gives the id of the cart
     * @return The id of the cart
     */
    public int getId() {
        return id;
    }

    /**
     * Gives all product ids present in the cart
     * @return An arraylist of product ids
     */
    public int[] getProductIDS() {
        return productIDS;
    }

    /**
     * Gives the quantities of the products in the cart
     * @return The quantities of the products
     */
    public int[] getQuantities() {
        return quantities;
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