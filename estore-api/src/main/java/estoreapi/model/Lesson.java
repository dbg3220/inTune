package estoreapi.model;

import org.apache.catalina.startup.Catalina;

import com.fasterxml.jackson.annotation.JsonProperty;

import estoreapi.model.Product.Category;

/**
 * @author Damon Gonzalez
 * @author Donovan Cataldo
 * 
 * Represents a Lesson
 */
public class Lesson {

    static final String STRING_FORMAT = "lesson [id=%d, isFull=%b, category=%s, instructor=%s, weekday=%s, startTime=%d, userID=%d, price=%.2f, name=%s]";

    @JsonProperty("id")
    private int id; // The Lesson ID
    @JsonProperty("isFull")
    private boolean isFull; // Used to detect if the lesson is booked or not
    @JsonProperty("category")
    private Category category; // The category of the lesson
    @JsonProperty("instructor")
    private String instructor; // The instructor for the lesson
    @JsonProperty("weekday")
    private String weekday; // The weekday of the lesson
    @JsonProperty("startTime")
    private int startTime; // The time the lesson starts
    @JsonProperty("userID")
    private int userID; // The id of the user who books the lesson
    @JsonProperty("price")
    private double price; // The price of the lesson
    @JsonProperty("name")
    private String name; // The name of the lesson

    /**
     * Public constructor for deserialization from json file to runtime object
     * @param id The id of the lesson
     * @param isFull Whether the lesson is scheduled for a user
     * @param category What category of instrument the lesson will teach
     * @param instructor The name of the lesson instructor
     * @param weekday The day of the week the lesson is on
     * @param startTime The starting time of the lesson, in hours
     * @param userID The id of the user who has scheduled this lesson
     * @param price The weekly price of the lesson
     * @param name The name of the lesson, (i.e. 'Guitar Lesson')
     */
    public Lesson(
        @JsonProperty("id") int id,
        @JsonProperty("isFull") boolean isFull,
        @JsonProperty("category") Category category,
        @JsonProperty("instructor") String instructor,
        @JsonProperty("weekday") String weekday,
        @JsonProperty("startTime") int startTime,
        @JsonProperty("userID") int userID,
        @JsonProperty("price") double price,
        @JsonProperty("name") String name
    ){
        this.id = id;
        this.isFull = isFull;
        this.category = category;
        this.instructor = instructor;
        this.weekday = weekday;
        this.startTime = startTime;
        this.userID = userID;
        this.price = price;
        this.name = name;
    }

    /**
     * Public constuctor for creating a lesson from a REST request
     * The parameters isFull and userID are set to placeholder values
     * 
     * @param id The id of the lesson
     * @param category What category of instrument the lesson will teach
     * @param instructor The name of the lesson instructor
     * @param weekday The day of the week the lesson is on
     * @param startTime The starting time of the lesson, in hours
     * @param price The weekly price of the lesson
     * @param name The name of the lesson, (i.e. 'Guitar Lesson')
     */
    public Lesson(
        int id,
        Category category,
        String instructor,
        String weekday,
        int startTime,
        double price,
        String name
    ){
        this(id, false, category, instructor, weekday, startTime, -1, price, name);
    }

    /**
     * Getter for the id
     * @return The id of this lesson
     */
    public int getID(){
        return this.id;
    }

    /**
     * Getter for the isFull parameter
     * @return true if isFull is true, false otherwise
     */
    public boolean isFull(){
        return this.isFull;
    }

    /**
     * Getter for the category
     * @return An all caps string representation of the category of the lesson
     */
    public Category getCategory(){
        return this.category;
    }

    /**
     * Getter for the instructor
     * @return The name of the instructor
     */

    public String getIntructor(){
        return this.instructor;
    }

    /**
     * Getter for the userID
     * @return The id of the user who has scheduled this lesson, -1 for no user
     */
    public int getUserID(){
        return this.userID;
    }

    /**
     * Getter for the weekDay
     * @return The day of the week this lesson is on
     */
    public String getWeekDay(){
        return this.weekday;
    }

    /**
     * Getter for the startTime
     * @return The time during the day this lesson starts, in hours
     */
    public int getStartTime(){
        return this.startTime;
    }

    /**
     * Getter for the price
     * @return The weekly price of this lesson
     */
    public double getPrice(){
        return this.price;
    }

    /**
     * Getter for the name
     * @return The name of this lesson
     */
    public String getName(){
        return this.name;
    }

    @Override
    public String toString(){
        return String.format(STRING_FORMAT, id, isFull, category, instructor, weekday, startTime, userID, price, name);
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public boolean equals(Object other){
        if( other instanceof Lesson){
            Lesson otherLesson = (Lesson) other;
            return this.id == otherLesson.id &&
                   this.isFull == otherLesson.isFull &&
                   this.category.equals(otherLesson.category) &&
                   this.instructor.equals(otherLesson.instructor) &&
                   this.weekday.equals(otherLesson.weekday) &&
                   this.startTime == otherLesson.startTime &&
                   this.userID == otherLesson.userID &&
                   this.price == otherLesson.price &&
                   this.name.equals(otherLesson.name);
        }
        return false;
    }

    @Override
    public int hashCode(){
        return Integer.hashCode(id) + 
               Boolean.hashCode(isFull) + 
               category.hashCode() + 
               instructor.hashCode() + 
               weekday.hashCode() + 
               Integer.hashCode(startTime) +
               Integer.hashCode(userID) + 
               Double.hashCode(price) + 
               name.hashCode();
    }
}
