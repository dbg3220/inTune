package estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the cart of a user
 * 
 * @author Damon Gonzalez
 */
public class Cart {

    /** The ids of the products in this cart */
    @JsonProperty("products") private int[] productIDS;
    /** The quantites of the products in this cart */
    @JsonProperty("quantities") private int[] quantities;

    /**
     * Public constructor for the Cart class
     * @param productIDS An array of products by id that are present in the cart
     * @param quantities An array of quantities for each product where the index of
     * each quantity corresponds to the index of each product id
     */
    public Cart (@JsonProperty("products") int[] productIDS, 
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
