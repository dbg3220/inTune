package estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the cart of a user
 * 
 * @author Damon Gonzalez
 */
public class Cart {

    /** The ids of the products in this cart */
    @JsonProperty("productIDS") private int[] productIDS;
    /** The quantites of the products in this cart, with corresponding indices to productIDS */
    @JsonProperty("quantities") private int[] quantities;

    /**
     * Public constructor for the Cart class
     */
    public Cart (@JsonProperty("productIDS") int[] productIDS, 
                 @JsonProperty("quantities") int[] quantities) {
        this.productIDS = productIDS;
        this.quantities = quantities;
    }

    /**
     * Retrieves the product ids of the cart
     * @return An arraylist of product ids
     */
    public int[] getProductIDS() {
        return productIDS;
    }

    /**
     * Retrieves the product quantites of the cart
     * @return The quantities of the products
     */
    public int[] getQuantities() {
        return quantities;
    }
}
