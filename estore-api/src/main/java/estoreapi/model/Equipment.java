package estoreapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * @author Donovan Cataldo
 * 
 * An equipment class used so to instaciate product
 * All methods call the super class implementntation of the method
 */
public class Equipment extends Product {

    private static final Logger LOG = Logger.getLogger(Equipment.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "equipment [id=%d, name=%s, price=%.2f, category=%s, quantity=%d, isInstrument=%b, isEquipment=%b, isLesson=%b]";

    @JsonProperty("id") private int id; // The individual ID of each equipment
    @JsonProperty("name") private String name; // The name of the equipment (Ex. Sherman's Violin Rosin)
    @JsonProperty("price") private double price; // The price of the equipment
    @JsonProperty("category") private Product.Category category; // The category of each equipment (Ex. woodwind)
    @JsonProperty("quantity") private int quantity; // The quantity in stock of the equipment 
    @JsonProperty("isInstrument") private boolean isInstrument; // The boolean of whether or not a equipment isIntrument (False)
    @JsonProperty("isEquipment") private boolean isEquipment; // The boolean of whether or not a equipment isEquipment (True)
    @JsonProperty("isLesson") private boolean isLesson; // The boolean of whether or not a equipment isLesson (False)

    /**
     * Create equipment with the given id, name, and price.
     * @param invalid The id of the equiptment
     * @param name The name of the equiptment
     * @param price The price of the equiptment
     * @param category The category of the equiptment
     * @param subcategory The subcategory of the equiptment
     * @param quantity The quantity of the equiptment
     *     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Equipment(@JsonProperty("id") int id , @JsonProperty("name") String name, @JsonProperty("price") double price, @JsonProperty("category") Category category, 
    @JsonProperty("quantity") int quantity, @JsonProperty("isInstrument") boolean isInstrument, 
    @JsonProperty("isEquipment") boolean isEquipment, @JsonProperty("isLesson") boolean isLesson) {
        super(id, name, price, category, quantity, isInstrument, isEquipment, isLesson);
    }

    /**
     * Retrieves the id of the equiptment
     * @return The id of the equiptment
     */
    public int getId() {return super.getId();}

    /**
     * Sets the name of the equiptment 
     * @param name The name of the equiptment
     */
    public void setName(String name) {super.setName(name);}

    /**
     * Retrieves the name of the equiptment
     * @return The name of the equiptment
     */
    public String getName() {return super.getName();}

    /**
     * Sets the price of the equiptment
     * @param price The price of the equiptment
     */
    public void setPrice(double price) {super.setPrice(price);}

    /**
     * Retrieves the price of the equiptment
     * @return The price of the equiptment
     */
    public double getPrice() {return super.getPrice();}

    /**
     * Sets the category of the equiptment 
     * @param category The category of the equiptment
     */
    public void setCategory(Product.Category category) {super.setCategory(category);}

    /**
     * Retrieves the category of the equiptment
     * @return The category of the equiptment
     */
    public Product.Category getCategory() {return super.getCategory();}

    /**
     * Sets the quantity of the equiptment
     * @param quantity The quantity of the equiptment
     */
    public void setQuantity(int quantity) {super.setQuantity(quantity);}

    /**
     * Retrieves the quantity of the equiptment
     * @return The quantity of the equiptment
     */
    public int getQuantity() {return super.getQuantity();}

       /**
     * Retrieves the isInstrument of the equiptment
     * @return The isInstrument of the equiptment
     */
    public boolean getIsInstrument() {return super.getIsInstrument();}

    /**
     * Retrieves the isEquipment of the equiptment
     * @return The isEquipment of the equiptment
     */
    public boolean getIsEquipment() {return super.getIsEquipment();}


    /**
     * Retrieves the isLesson of the equiptment
     * @return The isLesson of the equiptment
     */
    public boolean getIsLesson() {return super.getIsLesson();}
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id,name,price,category,quantity,isInstrument,isEquipment,isLesson);
    }
}