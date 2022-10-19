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
    @JsonProperty("products") private ArrayList<Product> products;
    @JsonProperty("quantities") private ArrayList<Integer> quantities;

    /**
     * Public constructor for the Cart class
     * @param id The id of the cart, corresponds to the id of the User whose cart
     *  this belongs to
     */
    public Cart (@JsonProperty("id") int id) {
        this.id = id;
        products = new ArrayList<>();
        quantities = new ArrayList<>();
    }

    /**
     * Gives the id of the cart
     * @return The id of the cart
     */
    public int getId() {
        return id;
    }

    /**
     * Gives all products present in the cart
     * @return An arraylist of products
     */
    public ArrayList<Product> getProducts() {
        return products;
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
     * @param product The product in question
     * @return If the product is in the cart than it returns that quantity, 
     *  otherwise it returns -1
     */
    public int getQuantity(Product product){
        for(int i = 0; i < products.size(); i++){
            if(products.get(i).equals(product)){
                return quantities.get(i);
            }
        }
        return -1;
    }

    /**
     * Retrieves the total price of the cart
     * @return The total price of the cart
     */
    public double getTotal() {
        double total = 0;

        for(int i = 0; i < products.size(); i++){
            Product product = products.get(i);
            int quantity = quantities.get(i);
            total += product.getPrice() * quantity;
        }

        return total;
    }

    /**
     * Checks if the cart contains a certain product
     * @param product The product 
     * @return true if the product is in the cart, fale otherwise
     */
    public boolean containsProduct(Product product){
        return products.contains(product);
    }

    /**
     * Adds a product to the cart with the specified new quantity, does
     * not take into consideration how many of the products exist in 
     * the inventory
     * @param product The product to be added
     * @param quantity The quantity of that product to be added, assumed to be > 0
     */
    public void addProduct(Product product, Integer quantity) {
        if(products.contains(product)){
            int index = products.indexOf(product);
            int initialQuantity = quantities.get(index);
            quantities.set(index, initialQuantity + quantity);
        } else {
            products.add(product);
            quantities.add(quantity);
        }
    }

    /**
     * Removes a product from the cart
     * @param product The product to be removed
     * @param quantity The quantity of the product to be removed, assumed to be > 0
     * @return true if the operation was performed successfully, false otherwise
     */
    public boolean removeProduct(Product product, int quantity) {
        if(!products.contains(product))
            return false;

        int index = products.indexOf(product);
        int initialQuantity = quantities.get(index);

        if(quantity > initialQuantity)
            return false;

        quantities.set(index, initialQuantity - quantity);

        if(quantities.get(index) == 0){
            quantities.remove(index);
            products.remove(index);
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id);
    }
}