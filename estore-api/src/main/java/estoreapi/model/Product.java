package estoreapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents an product
 * 
 * @author Hayden Cieniawski
 * @author Clayton Acheson
 */
public class Product {

    public enum Category {
        STRINGS, 
        WOODWINDS, 
        PERCUSSION,
        BRASS, 
        KEYBOARDS
    }

    private static final Logger LOG = Logger.getLogger(Product.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "product [id=%d, name=%s, price=%.2f, category=%s, quantity=%d, isInstrument=%b, isEquipment=%b, isLesson=%b]";

    @JsonProperty("id") private int id; //The product ID
    @JsonProperty("name") private String name; //The name of the product to be displayed
    @JsonProperty("price") private double price; //The price of the given product
    @JsonProperty("category") private Category category; //The category of the product, for front-end classification
    @JsonProperty("quantity") private int quantity; //The amount of the product in stock
    @JsonProperty("description") private String description; //The description of the product, for the product page
    @JsonProperty("size") private String image; // The size of the intrument (Ex. 1/2)

    /**
     * Create a product with the given id, name, and price.
     * @param id The id of the product
     * @param name The name of the product
     * @param price The price of the product
     * @param category2 The category of the product
     * @param subcategory The subcategory of the product
     * @param quantity The quantity of the product
     *     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Product(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("price") double price, @JsonProperty("category") Category category, 
    @JsonProperty("quantity") int quantity, @JsonProperty("description") String description, @JsonProperty("size") String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
        this.description = description;
        this.image = image;
    }

    /**
     * Retrieves the id of the product
     * @return The id of the product
     */
    public int getId() {return id;}

    /**
     * Sets the name of the product - necessary for JSON object to Java object deserialization
     * @param name The name of the product
     */
    public void setName(String name) {this.name = name;}

    /**
     * Retrieves the name of the product
     * @return The name of the product
     */
    public String getName() {return name;}

    /**
     * Sets the price of the product - necessary for JSON object to Java object deserialization
     * @param price The price of the product
     */
    public void setPrice(double price) {this.price = price;}

    /**
     * Retrieves the price of the product
     * @return The price of the product
     */
    public double getPrice() {return price;}

    /**
     * Sets the category of the product - necessary for JSON object to Java object deserialization
     * @param category The category of the product
     */
    public void setCategory(Category category) {this.category = category;}

   /**
     * Retrieves the specific enum of the product
     * @return The quantity of the product
     */
    public String getStatus() {
        return category.name();
    }

    /**
     * Sets the quantity of the product - necessary for JSON object to Java object deserialization
     * @param quantity The quantity of the product
     */
    public void setQuantity(int quantity) {this.quantity = quantity;}

    /**
     * Retrieves the quantity of the product
     * @return The quantity of the product
     */
    public int getQuantity() {return quantity;}

    /**
     * Sets the Description of the product
     */
    public void setDescription (String desc){this.description = desc;}

    /**
     * Retrieves the Description of the product
     * @return The Description of the product
     */
    public String getDescription(){return description;}

    /**
     * Sets the Image of the product
     */
    public void setImage (String img){this.image = img;}

    /**
     * Retrieves the Image of the product
     * @return The image of the product
     */
    public String getImage(){return image;}

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id,name,price,getStatus(),quantity,description);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if(o instanceof Product){
           Product check = (Product) o;
           if(check.getId() == this.id && 
           check.getName() == this.name && 
           check.getPrice() == this.price &&
           check.getStatus() == this.getStatus() &&
           check.getQuantity() == this.quantity &&
           check.getDescription() == this.description){
            return true;
           }
           return false;
        }
        return false;
    }
}