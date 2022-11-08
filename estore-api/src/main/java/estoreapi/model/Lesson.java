package estoreapi.model;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents a lesson
 * 
 * @author Donovan Cataldo
 */
public class Lesson {

    static final String STRING_FORMAT = "lesson [id=%d, isFull=%b, category=%s, instructor=%s, weekday=%s, startTime=%d, userID=%d, price=%s, name=%s]";

    @JsonProperty("id")
    private int id; // The Lesson ID
    @JsonProperty("isFull")
    private boolean isFull; // Used to detect if the lesson is booked or not
    @JsonProperty("category")
    private String category; // The category of the lesson
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
     * Create a lesson
     * 
     * @param id The id of the lesson
     * @param price The price of the lesson
     * @param weekday The day the lesson will take place
     * @param startTime The time the lesson will start
     * @param name The name of the lesson
     * 
     */
    public Lesson(
        @JsonProperty("id") int id,
        @JsonProperty("price") double price,
        @JsonProperty("weekday") String weekday,
        @JsonProperty("startTime") int startTime,
        @JsonProperty("name") String name
    ){
        this.id = id;
        this.isFull = false;
        this.category = null;
        this.instructor = null;
        this.weekday = weekday;
        this.startTime = startTime;
        this.userID = -1;
        this.price = price;
        this.name = name;

    }

    /**
     *  Used when a lesson is "booked". Sets the category, intructor, and ID of the user who booked the lesson
     * @param category The intrument category of the lesson
     * @param instructor The instructor teaching the lesson
     * @param userID The ID of the user taking the lesson
     */
    public void setLesson(String category, String instructor, int userID){
        this.isFull = true;
        this.category = category;
        this.instructor = instructor;
        this.userID = userID;
    }

    /**
     * Gets if the lesson is full or not
     * 
     * @return the boolean of whether the lesson is full or not
     */
    public boolean getIsFull(){
        return this.isFull;
    }

    /**
     * Retrieves the category of the lesson
     * 
     * @return The category of the lesson
     */
    public String getCategory(){
        return this.category;
    }

    /**
     * Retrieves the instrucotr teaching the lesson
     * 
     * @return The instructor teaching the lesson
     */
    public String getIntructor(){
        return this.instructor;
    }

    /**
     * Retrieves the ID of the user who booked the lesson
     * 
     * @return the ID of the user who booked the lesson
     */
    public int getUserID(){
        return this.userID;
    }

    /**
     * Retrieves the week day the lesson takes place
     * 
     * @return the week day the lesson takes place
     */
    public String getWeekDay(){
        return this.weekday;
    }

    /**
     * Retrieves the start time of the less
     * 
     * @return
     */
    public int getStartTime(){
        return this.startTime;
    }

    /**
     * Retrieves the price of the lesson
     * 
     * @return the price of the lesson
     */
    public double getPrice(){
        return this.price;
    }

    /**
     * Retrieves the unique id of the lesson
     * 
     * @return the unique id of the lesson
     */
    public int getID(){
        return this.id;
    }

    /**
     * Retrieves the name of the lesson
     * 
     * @return the name of the lesson
     */
    public String getName(){
        return this.name;
    }

    /**
     * {@inheritDoc}}
     */
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
}
