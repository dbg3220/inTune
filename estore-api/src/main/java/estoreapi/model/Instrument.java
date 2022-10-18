package estoreapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Donovan Cataldo
 * 
 * Creates an intrument that is only different from the product because an intrument has a size
 */
public class Instrument extends Product {

    private static final Logger LOG = Logger.getLogger(Product.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "Instrument [id=%d, name=%s, price=%.2f, category=%s, quantity=%d, isInstrument=%b, isEquipment=%b, isLesson=%b, size=%s]";

    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("price")
    private double price;
    @JsonProperty("category")
    private Product.Category category;
    @JsonProperty("quantity")
    private int quantity;
    @JsonProperty("isInstrument")
    private boolean isInstrument;
    @JsonProperty("isEquipment")
    private boolean isEquipment;
    @JsonProperty("isLesson")
    private boolean isLesson;
    @JsonProperty("size")
    private String size;

    /**
     * Create a product with the given id, name, and price.
     * 
     * @param id          The id of the product
     * @param name        The name of the product
     * @param price       The price of the product
     * @param category    The category of the product
     * @param subcategory The subcategory of the product
     * @param quantity    The quantity of the product
     * 
     *                    {@literal @}JsonProperty is used in serialization and
     *                    deserialization
     *                    of the JSON object to the Java object in mapping the
     *                    fields. If a field
     *                    is not provided in the JSON object, the Java field gets
     *                    the default Java
     *                    value, i.e. 0 for int
     */
    public Instrument(@JsonProperty("id") int id, @JsonProperty("name") String name,
            @JsonProperty("price") double price, @JsonProperty("category") Product.Category category,
            @JsonProperty("quantity") int quantity, @JsonProperty("isInstrument") boolean isInstrument,
            @JsonProperty("isEquipment") boolean isEquipment, @JsonProperty("isLesson") boolean isLesson,
            @JsonProperty("size") String size) {
        super(id, name, price, category, quantity, isInstrument, isEquipment, isLesson);
        this.size = size;
    }

    /**
     * Retrieves the id of the product
     * 
     * @return The id of the product
     */
    public int getId() {
        return super.getId();
    }

    /**
     * Sets the name of the product - necessary for JSON object to Java object
     * deserialization
     * 
     * @param name The name of the product
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the name of the product
     * 
     * @return The name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the price of the product - necessary for JSON object to Java object
     * deserialization
     * 
     * @param price The price of the product
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Retrieves the price of the product
     * 
     * @return The price of the product
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the category of the product - necessary for JSON object to Java object
     * deserialization
     * 
     * @param category The category of the product
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Retrieves the category of the product
     * 
     * @return The category of the product
     */
    public Product.Category getCategory() {
        return this.category;
    }

    /**
     * Sets the quantity of the product - necessary for JSON object to Java object
     * deserialization
     * 
     * @param quantity The quantity of the product
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Retrieves the quantity of the product
     * 
     * @return The quantity of the product
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the isInstrument of the product - necessary for JSON object to Java
     * object deserialization
     * 
     * @param isInstrument The isInstrument of the product
     */
    public void setIsInstrument(boolean isInstrument) {
        this.isInstrument = isInstrument;
    }

    /**
     * Retrieves the isInstrument of the product
     * 
     * @return The isInstrument of the product
     */
    public boolean getIsInstrument() {
        return isInstrument;
    }

    /**
     * Sets the isEquipment of the product - necessary for JSON object to Java
     * object deserialization
     * 
     * @param isEquipment The isEquipment of the product
     */
    public void setIsEquipment(boolean isEquipment) {
        this.isEquipment = isEquipment;
    }

    /**
     * Retrieves the isEquipment of the product
     * 
     * @return The isEquipment of the product
     */
    public boolean getIsEquipment() {
        return isEquipment;
    }

    /**
     * Sets the isLesson of the product - necessary for JSON object to Java object
     * deserialization
     * 
     * @param isLesson The isLesson of the product
     */
    public void setIsLesson(boolean isLesson) {
        this.isLesson = isLesson;
    }

    /**
     * Retrieves the isLesson of the product
     * 
     * @return The isLesson of the product
     */
    public boolean getIsLesson() {
        return isLesson;
    }

    /**
     * Sets the size of the product - necessary for JSON object to Java object
     * deserialization
     * 
     * @param size The size of the product
     */
    public void setSize(String size) {
        this.size = size;
    }

    /**
     * Retrieves the size of the product
     * 
     * @return The size of the product
     */
    public String getSize() {
        return size;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, id, name, price, category, quantity, isInstrument, isEquipment, isLesson,
                size);
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Instrument){
            Instrument object =  (Instrument) o;
            if((object.getName() == this.name) && (object.getPrice() == this.price) && (object.getCategory() == this.category) && (object.getQuantity() == this.quantity) && 
            (object.isEquipment == this.isEquipment) && (object.getIsInstrument() == this.isInstrument) && (object.getIsLesson() == this.isLesson) && (object.getSize() == this.size)){
                return true;
            }
            else{
                return false;
            }
        }
        return false;
    }
}