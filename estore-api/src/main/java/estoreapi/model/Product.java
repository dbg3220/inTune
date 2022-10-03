package estoreapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents an product
 * 
 * @author Hayden Cieniawski
 */
public abstract class Product {

    enum Category {
        STRINGS, 
        WOODWINDS, 
        PERCUSSION,
        BRASS, 
        KEYBOARDS
    }

    private static final Logger LOG = Logger.getLogger(Product.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "product [id=%d, name=%s, price=%.2f, category=%s, quantity=%d, isInstrument=%b, isEquipment=%b, isLesson=%b]";

    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("price") private double price;
    @JsonProperty("category") private Category category;
    @JsonProperty("quantity") private int quantity;
    @JsonProperty("isInstrument") private boolean isInstrument;
    @JsonProperty("isEquipment") private boolean isEquipment;
    @JsonProperty("isLesson") private boolean isLesson;

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
    public Product(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("price") double price, @JsonProperty("category") estoreapi.model.Equipment.Category category2, 
    @JsonProperty("quantity") int quantity, @JsonProperty("isInstrument") boolean isInstrument, 
    @JsonProperty("isEquipment") boolean isEquipment, @JsonProperty("isLesson") boolean isLesson) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category2;
        this.quantity = quantity;
        this.isInstrument = isInstrument;
        this.isEquipment = isEquipment;
        this.isLesson = isLesson;

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
     * Retrieves the category of the product
     * @return The category of the product
     */
    public Category getCategory() {return category;}

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
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id,name,price,category,quantity,isInstrument,isEquipment,isLesson);
    }
}