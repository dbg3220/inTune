package estoreapi.model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class for the functionality of a Cart within the model-tier
 * 
 * @author Damon Gonzalez
 */
public class Cart {

    protected static final String STRING_FORMAT = "Cart [id=%d]";

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
    public Cart (@JsonProperty("id") int id, ArrayList<Integer> productIDS, ArrayList<Integer> quantities) {
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
     * Adds a product to the cart with the specified new quantity, does
     * not take into consideration how many of the products exist in 
     * the inventory
     * @param id The id of the product to be added
     * @param quantity The quantity of the product to be added, assumed to be > 0
     */
    public void addProduct(int id, int quantity) {
        int index = productIDS.indexOf(id);
        if(index != -1){
            int initialQuantity = quantities.get(index);
            quantities.set(index, initialQuantity + quantity);
        } else {
            productIDS.add(id);
            quantities.add(quantity);
        }
    }

    /**
     * Removes a product from the cart
     * @param id The id of the product to be removed
     * @param quantity The quantity of the product to be removed, assumed to be > 0
     * @return true if the operation was performed successfully, false otherwise
     */
    public boolean removeProduct(Product product, int quantity) {
        //TODO
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id);
    }
}