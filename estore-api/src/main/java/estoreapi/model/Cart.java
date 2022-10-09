package estoreapi.model;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents an product
 * METHODS NEED TO BE UPDATED TO SEND INFORMATION TO THE SUPERCLASS
 * 
 * @author Hayden Cieniawski
 * @author Clayton Acheson
 */
public class Cart {



    // Package private for tests
    static final String STRING_FORMAT = "Cart [id=%d]";

    @JsonProperty("id") private int id;
    @JsonProperty("items") private Hashtable<Product, Integer> items = new Hashtable<Product, Integer>();
    @JsonProperty("totalPrice") private double total;


    
    public Cart (@JsonProperty("id") int id, @JsonProperty("items") Hashtable<Product, Integer> items, @JsonProperty("totalPrice") double total) {
        this.id = id;
        this.items = items;
        this.total = total;

    }

    /**
     * Retrieves the id of the cart
     * @return The id of the cart
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the items in the cart
     * @return The items in the cart
     */
    public Set<Product> getItems() {
        if(!items.isEmpty()){
            return items.keySet(); 
        }
        else{
            System.out.println("Cart is Empty");
            return items.keySet();  
        }
    }

    /**
     * Retrieves the items in the cart
     * @return The items in the cart
     */
    public ArrayList<Integer> getQuantities() {
       ArrayList<Integer> quantities = new ArrayList<Integer>();
        if (!items.isEmpty()){
        for(int i = 0; i < items.size(); i++){
            Integer value = items.get(items.keySet().toArray()[i]);
            quantities.add(value);
        }
        return quantities;
       }
       else{
        System.out.println("Cart is Empty");
        return quantities;
       }
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
     * Validates that the product is in stock
     */
    public void additem(Product item, Integer quantity) {
        if(item.getQuantity() > 0){
            if(quantity > item.getQuantity()){
                System.out.println("Unforunately, we do not have the requested quantity");
            }
            else{
            double x = item.getPrice();
            items.put(item, quantity);
            this.total += (x * (double)quantity);
            item.setQuantity(item.getQuantity()-quantity);
            }
        }
        else{
            System.out.println("Product is currently unavailable");
        }
    }

    /**
     * copys the current product array and removes the product
     * then sets the current product array to the new one
     * Sets the quantity of the given product
     */
    public void removeitem(Product item, int quantity) {
        double x = item.getPrice();
        items.remove(item.getName());
        this.total -= x;
        item.setQuantity(item.getQuantity() + quantity);
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id);
    }
}