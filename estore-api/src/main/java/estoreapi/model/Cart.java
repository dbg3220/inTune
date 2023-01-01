package estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the cart of a user
 * 
 * @author Damon Gonzalez
 */
public class Cart {

    /** Format string for a Cart */
    static final String STRING_FORMAT = "[productIDS=%s, quantities=%s]";

    /** The ids of the products in this cart */
    @JsonProperty("productIDS") private int[] productIDS;
    /** The quantites of the products in this cart, with corresponding indices to productIDS */
    @JsonProperty("quantities") private int[] quantities;

    /**
     * Public constructor for the Cart class
     * @param productIDS An array of product ids this cart contains
     * @param quantities An array of quantities of the corresponding productIDS
     * array
     */
    public Cart (@JsonProperty("productIDS") int[] productIDS, 
                 @JsonProperty("quantities") int[] quantities) {
        if(productIDS.length != quantities.length)
            throw new IllegalCartException("the productIDS and quantities of a cart must be the same length");
        for(int i = 0; i < productIDS.length; i++){
            if(productIDS[i] < 0)
                throw new IllegalCartException("a cart cannot contain negative productIDS because all products have nonnegative ids");
            if(quantities[i] < 0)
                throw new IllegalCartException("a quantity of a product cannot be negative");
        }
        this.productIDS = productIDS;
        this.quantities = quantities;
    }

    /**
     * Private exception class to halt program when a cart is attempted to be
     * instantiated with values that are illegal under the logic of this
     * program.
     */
    private class IllegalCartException extends RuntimeException {
        public IllegalCartException(String message){
            super("Cart: " + message);
        }
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

    @Override
    public String toString() {
        return String.format(STRING_FORMAT, productIDS.toString(), quantities.toString());
    }
}
