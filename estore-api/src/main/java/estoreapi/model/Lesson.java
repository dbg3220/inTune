package estoreapi.model;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents an product
 * METHODS NEED TO BE UPDATED TO SEND INFORMATION TO THE SUPERCLASS
 * 
 * @author Donovan Cataldo
 */
public class Lesson extends Product {

    enum Day {
        Monday,
        Tuesday,
        Wednesday,
        Thursday,
        Friday
    }

    private static final Logger LOG = Logger.getLogger(Product.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "Lesson [id=%d, name=%s, price=%.2f, category=%s, quantity=%d, isInstrument=%b, isEquipment=%b, isLesson=%b, instructor=%s]";

    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("price") private double price;
    @JsonProperty("category") private Product.Category category; // Set in assign lesson or lesson over method
    @JsonProperty("quantity") private int quantity; // Once set should not change (eqqual to 0)
    @JsonProperty("isInstrument") private boolean isInstrument; // Once set should not change
    @JsonProperty("isEquipment") private boolean isEquipment; // Once set should not change
    @JsonProperty("isLesson") private boolean isLesson; // Once set should not change
    @JsonProperty("instructor") private String instructor; // Set in assign lesson or lesson over method
    @JsonProperty("student") private User student; // Set in assign lesson or lesson over method
    @JsonProperty("isFull") private Boolean isFull; // Set in assign lesson or lesson over method
    @JsonProperty("startTime") private String startTime; // Once set should not change
    @JsonProperty("weekDay") private Day weekDay; // Once set should not change

/**
 * Each lesson (Mon-Fri 9-5) will have a 30 minute length and will be hardcoded into the json.
 * No new lessons will be created and only the student, user, category, and isFull is ever changed
 * Price will be adjusted by the category of the lesson. (possibly by lesson length if we want to group lessons sort of like friends)
 * 
 */
    /**
     * Create a product with the given id, name, and price.
     * @param id The id of the product
     * @param name The name of the product
     * @param price The price of the product
     * @param category The category of the product
     * @param subcategory The subcategory of the product
     * @param quantity The quantity of the product
     *    
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Lesson (@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("price") double price, @JsonProperty("category") Category category, 
    @JsonProperty("quantity") int quantity, @JsonProperty("isInstrument") boolean isInstrument, 
    @JsonProperty("isEquipment") boolean isEquipment, @JsonProperty("isLesson") boolean isLesson, @JsonProperty("instructor") String instructor, 
    @JsonProperty("student") User student, @JsonProperty("weekDay") Day weekDay, @JsonProperty("startTime") String startTime, @JsonProperty("isFull") Boolean isFull) {
        super(id, name, price, category, quantity, isInstrument, isEquipment, isLesson);
        this.instructor = instructor;
        this.isFull = isFull;
        this.student = student;
        this.startTime = startTime;
        this.weekDay = weekDay;
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

    public Product.Category getCategory() {return super.getCategory();}

    /**
     * Retrieves the isInstrument of the product
     * @return The isInstrument of the product
     */
    public boolean getIsInstrument() {return super.getIsInstrument();}

    /**
     * Retrieves the isEquipment of the product
     * @return The isEquipment of the product
     */
    public boolean getIsEquipment() {return super.getIsEquipment();}

    /**
     * Retrieves the isLesson of the product
     * @return The isLesson of the product
     */
    public boolean getIsLesson() {return super.getIsLesson();}

    /**
     * Retrieves the instructor of the product
     * @return The instructor of the product
     */
    public String getInstructor() {return instructor;}

    public String getStartTime(){return startTime;}

    /**
     * 
     * @return
     */
    public boolean getIsFull(){return isFull;}

    /**
     * 
     * @return
     */
    public User getStudent(){return student;}
    /**
     * 
     * @return
     */
    public Day getWeekDay(){return weekDay;}

    

    /**
     * When the lesson has concluded and needs to be reset to an empty lesson, run this method
     * It clears the student, isfull, instrucor, and the lessons category 
     */
    public void lessonOver(){
        this.student = null;
        this.isFull = false;
        this.instructor = null;
        this.category = null;
    }

    /**
     * When a student checks out a lesson, the lesson is changed here and in the JSON file
     * @param student the user who bought the lesson
     * @param instructor the person (string) that the user chose to intruct their lesson
     * @param category the category of the lesson (string, woodwind, keyboard, etc.)
     */
    public void assignLesson(User student, String instructor, Product.Category category){
        this.student = student;
        this.isFull = true;
        this.instructor = instructor;
        this.category = category;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id,name,price,category,quantity,isInstrument,isEquipment,isLesson,instructor,student,weekDay,startTime, isFull);
    }
}