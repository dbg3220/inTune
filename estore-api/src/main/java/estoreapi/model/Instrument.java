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

    @JsonProperty("id") private int id; // The individual ID of each intrument
    @JsonProperty("name") private String name; // The name of the intrument (Ex. Ludwig Universal Brass Snare Drum)
    @JsonProperty("price") private double price; // The price of the intrument
    @JsonProperty("category") private Product.Category category; // The category of each intrument (Ex. woodwind)
    @JsonProperty("quantity") private int quantity; // The quantity in stock of the intrument 
    @JsonProperty("isInstrument") private boolean isInstrument; // The boolean of whether or not a intrument isIntrument (True)
    @JsonProperty("isEquipment") private boolean isEquipment; // The boolean of whether or not a intrument isEquipment (False)
    @JsonProperty("isLesson") private boolean isLesson; // The boolean of whether or not a intrument isLesson (False)
    @JsonProperty("size") private String size; // The size of the intrument (Ex. 1/2)

    /**
     * Create an intrument with the given id, name, and price.
     * @param id The id of the intrument
     * @param name The name of the intrument
     * @param price The price of the intrument
     * @param category The category of the intrument
     * @param subcategory The subcategory of the intrument
     * @param quantity The quantity of the intrument
     *    
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Instrument (@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("price") double price, @JsonProperty("category") Category category, 
    @JsonProperty("quantity") int quantity, @JsonProperty("isInstrument") boolean isInstrument, 
    @JsonProperty("isEquipment") boolean isEquipment, @JsonProperty("isLesson") boolean isLesson, @JsonProperty("size") String size) {
        super(id, name, price, category, quantity, isInstrument, isEquipment, isLesson);
        this.size = size;
    }

    /**
     * Retrieves the id of the intrument
     * @return The id of the intrument
     */
    public int getId() {return super.getId();}

    /**
     * Sets the name of the intrument
     * @param name The name of the intrument
     */
    public void setName(String name) {super.setName(name);}

    /**
     * Retrieves the name of the intrument
     * @return The name of the intrument
     */
    public String getName() {return super.getName();}

    /**
     * Sets the price of the intrument
     * @param price The price of the intrument
     */
    public void setPrice(double price) {super.setPrice(price);}

    /**
     * Retrieves the price of the intrument
     * @return The price of the intrument
     */
    public double getPrice() {return super.getPrice();}

    /**
     * Sets the category of the intrument
     * @param category The category of the intrument
     */
    public void setCategory(Product.Category category) {super.setCategory(category);}

    /**
     * Retrieves the category of the intrument
     * @return The category of the intrument
     */
    public Product.Category getCategory() {return super.getCategory();}

    /**
     * Sets the quantity of the intrument
     * @param quantity The quantity of the intrument
     */
    public void setQuantity(int quantity) {super.setQuantity(quantity);}

    /**
     * Retrieves the quantity of the intrument
     * @return The quantity of the intrument
     */
    public int getQuantity() {return super.getQuantity();}

    /**
     * Retrieves the isInstrument of the intrument
     * @return The isInstrument of the intrument
     */
    public boolean getIsInstrument() {return super.getIsInstrument();}

    /**
     * Retrieves the isEquipment of the intrument
     * @return The isEquipment of the intrument
     */
    public boolean getIsEquipment() {return super.getIsEquipment();}


    /**
     * Retrieves the isLesson of the intrument
     * @return The isLesson of the intrument
     */
    public boolean getIsLesson() {return super.getIsLesson();}

    /**
     * Sets the size of the intrument
     * @param size The size of the intrument
     */
    public void setSize(String size) {this.size = size;}

    /**
     * Retrieves the size of the intrument
     * @return The size of the intrument
     */
    public String getSize() {return size;}

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id,name,price,category,quantity,isInstrument,isEquipment,isLesson,size);
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