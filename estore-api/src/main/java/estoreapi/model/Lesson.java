package estoreapi.model;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * @author Donovan Cataldo
 * 
 * A lesson that is a product but also has a student, intructor, startTime, weekDay, and isFull
 * The lesson should only be created once and never deleted
 * There will be lessons on a 30 minute interval from 9-5 monday-friday
 * Once a lesson is created, only the isFull, student, intructor, and category of the lesson should ever be changed
 */
public class Lesson extends Product {

    /**
     * An enum that will represent the day that the lesson is set to occur
     */
    public enum Day {
        Monday,
        Tuesday,
        Wednesday,
        Thursday,
        Friday
    }

    private static final Logger LOG = Logger.getLogger(Product.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "Lesson [id=%d, name=%s, price=%.2f, category=%s, quantity=%d, isInstrument=%b, isEquipment=%b, isLesson=%b, instructor=%s, student=%s, isFull=%b, startTime=%s, weekDay=%s]";

    @JsonProperty("id") private int id; // The individual ID of each lesson
    @JsonProperty("name") private String name; // The name of the lesson (Ex. 10am Wednesdy Lesson)
    @JsonProperty("price") private double price; // 
    @JsonProperty("category") private Product.Category category; // The category of each lesson (Ex. woodwind)
    @JsonProperty("quantity") private int quantity; // The quantity of each lesson (should always be 1 for lesson)
    @JsonProperty("isInstrument") private boolean isInstrument; // The boolean of whether or not a lesson isIntrument (False)
    @JsonProperty("isEquipment") private boolean isEquipment; // The boolean of whether or not a lesson isEquipment (False)
    @JsonProperty("isLesson") private boolean isLesson; // The boolean of whether or not a lesson isLesson (True)
    @JsonProperty("instructor") private String instructor; // The intructor teaching the lesson (Ex. Beethoven)
    @JsonProperty("student") private User student; // The user object of the student who purchased the lesson 
    @JsonProperty("isFull") private Boolean isFull; // The boolean of whether or not a lesson isFull or not
    @JsonProperty("startTime") private String startTime; // The startTime of each lesson (Ex. 10am)
    @JsonProperty("weekDay") private Day weekDay; // The day of the week that the lesson occurs

    /**
     * Create a lesson with the given id, name, and price.
     * @param invalid The id of the lesson
     * @param name The name of the lesson
     * @param price The price of the lesson
     * @param category The category of the lesson
     * @param subcategory The subcategory of the lesson
     * @param quantity The quantity of the lesson
     *    
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Lesson (@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("price") double price, @JsonProperty("category") Category category, 
    @JsonProperty("quantity") int quantity, @JsonProperty("isInstrument") boolean isInstrument, 
    @JsonProperty("isEquipment") boolean isEquipment, @JsonProperty("isLesson") boolean isLesson, @JsonProperty("instructor") String instructor, 
    @JsonProperty("student") User student, @JsonProperty("weekDay") Day WeekDay, @JsonProperty("startTime") String startTime, @JsonProperty("isFull") Boolean isFull) {
        super(id, name, price, category, quantity, isInstrument, isEquipment, isLesson);
        this.instructor = instructor;
        this.isFull = isFull;
        this.student = student;
        this.startTime = startTime;
        this.weekDay = WeekDay;
    }
 
    /**
     * Retrieves the id of the lesson
     * @return The id of the lesson
     */
    public int getId() {return super.getId();}

    /**
     * Sets the name of the lesson
     * @param name The name of the lesson
     */
    public void setName(String name) {super.setName(name);}

    /**
     * Retrieves the name of the lesson
     * @return The name of the lesson
     */
    public String getName() {return super.getName();}

    /**
     * Sets the price of the lesson
     * @param price The price of the lesson
     */
    public void setPrice(double price) {super.setPrice(price);}

    /**
     * Retrieves the price of the lesson
     * @return The price of the lesson
     */
    public double getPrice() {return super.getPrice();}

    /**
     * Retrieves the category of the lesson
     * @return The category of the lesson
     */
    public Product.Category getCategory() {return super.getCategory();}

    /**
     * Retrieves the isInstrument of the lesson
     * @return The isInstrument of the lesson
     */
    public boolean getIsInstrument() {return super.getIsInstrument();}

    /**
     * Retrieves the isEquipment of the lesson
     * @return The isEquipment of the lesson
     */
    public boolean getIsEquipment() {return super.getIsEquipment();}

    /**
     * Retrieves the isLesson of the =lesson
     * @return The isLesson of the lesson
     */
    public boolean getIsLesson() {return super.getIsLesson();}

    /**
     * Retrieves the instructor of the lesson
     * @return The instructor of the lesson
     */
    public String getInstructor() {return instructor;}

    /**
     * Retrieves the startTime of the lesson
     * @return The startTime of the lesson
     */
    public String getStartTime(){return startTime;}

    /**
     * Retrieves the isFull of the lesson
     * @return The isFull of the lesson
     */
    public boolean getIsFull(){return isFull;}

    /**
     * Retrieves the student of the lesson
     * @return The student of the lesson
     */
    public User getStudent(){return student;}

    /**
     * Retrieves the weekDay of the lesson
     * @return The weekDay of the lesson
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

    @Override
    public boolean equals(Object o){
        if(o instanceof Lesson){
            Lesson x = (Lesson) o;
            if(this.id == x.getId() &&
            this.name == x.getName() &&
            this.price == x.getPrice() &&
            this.category == x.getCategory() &&
            this.quantity == x.getQuantity() &&
            this.isInstrument == x.getIsInstrument() &&
            this.isEquipment == x.getIsEquipment() &&
            this.isLesson == x.getIsLesson() &&
            this.instructor == x.getInstructor() &&
            this.student == x.getStudent() &&
            this.weekDay == x.getWeekDay() &&
            this.startTime == x.getStartTime() &&
            this.isFull == x.getIsFull()){
                
                return true;
            }

        }
        return false;
    }
}