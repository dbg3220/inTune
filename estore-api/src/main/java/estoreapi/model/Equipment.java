package estoreapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * 
 * 
 * @author Donovan Cataldo
 */
public class Equipment extends Product {

    private static final Logger LOG = Logger.getLogger(Equipment.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "equipment [id=%d, name=%s, price=%.2f, category=%s, quantity=%d, isInstrument=%b, isEquipment=%b, isLesson=%b]";

    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("price") private double price;
    @JsonProperty("category") private Product.Category category;
    @JsonProperty("quantity") private int quantity;
    @JsonProperty("isInstrument") private boolean isInstrument;
    @JsonProperty("isEquipment") private boolean isEquipment;
    @JsonProperty("isLesson") private boolean isLesson;

    /**
     * Create a product with the given id, name, and price.
     * @param id The id of the 
     * @param name The name of the product
     * @param price The price of the product
     * @param category The category of the product
     * @param subcategory The subcategory of the product
     * @param quantity The quantity of the product
     *     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Equipment(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("price") double price, @JsonProperty("category") Category category, 
    @JsonProperty("quantity") int quantity, @JsonProperty("isInstrument") boolean isInstrument, 
    @JsonProperty("isEquipment") boolean isEquipment, @JsonProperty("isLesson") boolean isLesson) {
        super(id, name, price, category, quantity, isInstrument, isEquipment, isLesson);
    }

    /**
     * Retrieves the id of the product
     * @return The id of the product
     */
    public int getId() {return super.getId();}

    /**
     * Sets the name of the product - necessary for JSON object to Java object deserialization
     * @param name The name of the product
     */
    public void setName(String name) {super.setName(name);}

    /**
     * Retrieves the name of the product
     * @return The name of the product
     */
    public String getName() {return super.getName();}

    /**
     * Sets the price of the product - necessary for JSON object to Java object deserialization
     * @param price The price of the product
     */
    public void setPrice(double price) {super.setPrice(price);}

    /**
     * Retrieves the price of the product
     * @return The price of the product
     */
    public double getPrice() {return super.getPrice();}

    /**
     * Sets the category of the product - necessary for JSON object to Java object deserialization
     * @param category The category of the product
     */
    public void setCategory(Product.Category category) {super.setCategory(category);}

    /**
     * Retrieves the category of the product
     * @return The category of the product
     */
    public Product.Category getCategory() {return super.getCategory();}

    /**
     * Sets the quantity of the product - necessary for JSON object to Java object deserialization
     * @param quantity The quantity of the product
     */
    public void setQuantity(int quantity) {super.setQuantity(quantity);}

    /**
     * Retrieves the quantity of the product
     * @return The quantity of the product
     */
    public int getQuantity() {return super.getQuantity();}

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id,name,price,category,quantity,isInstrument,isEquipment,isLesson);
    }
}