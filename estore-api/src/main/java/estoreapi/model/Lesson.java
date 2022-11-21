package estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Lesson
 * 
 * @author Damon Gonzalez
 */
public class Lesson {

    /** Format string for toString() method */
    static final String STRING_FORMAT = "lesson [id=%d, isFull=%b, category=%s, instructor=%s, weekday=%s, startTime=%d, userID=%d, price=%s, name=%s]";

    /** The unique identifier for this lesson object */
    @JsonProperty("id") private int id;
    /** Boolean to identify whether this lesson is taken by a user */
    @JsonProperty("isFull") private boolean isFull;
    /** The category of instrument that this lesson is for */
    @JsonProperty("category") private String category;
    /** The name of the instructor for this lesson */
    @JsonProperty("instructor") private String instructor;
    /** The day of the week this lesson is held on(MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY) */
    @JsonProperty("weekday") private String weekday;
    /** The starting time (in hours) of this lesson */
    @JsonProperty("startTime") private int startTime;
    /** The id of the user who has taken this lesson, if not taken by a user is set to -1 */
    @JsonProperty("userID") private int userID;
    /** The weekly price of this lesson */
    @JsonProperty("price") private double price;
    /** The name of the lesson (i.e. Piano Lesson, Cello masterclass) */
    @JsonProperty("name") private String name;

    /**
     * Public constructor to deserialize lesson objects from a json file
     */
    public Lesson(@JsonProperty("id") int id,
                  @JsonProperty("isFull") boolean isFull,
                  @JsonProperty("category") String category,
                  @JsonProperty("instructor") String instructor,
                  @JsonProperty("weekday") String weekday,
                  @JsonProperty("startTime") int startTime,
                  @JsonProperty("userID") int userID,
                  @JsonProperty("price") double price,
                  @JsonProperty("name") String name){
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

    /** Getter for id */
    public int getID(){
        return id;
    }

    /** Getter for isFull */
    public boolean isFull(){
        return isFull;
    }
    
    /** Getter for the category */
    public String getCategory(){
        return category;
    }

    /** Getter for the instructor */
    public String getInstructor(){
        return instructor;
    }

    /** Getter for the weekday */
    public String getWeekDay(){
        return weekday;
    }

    /** Getter for the startTime */
    public int getStartTime(){
        return startTime;
    }

    /** Getter for the userID */
    public int getUserID(){
        return userID;
    }

    /** Getter for the price */
    public double getPrice(){
        return price;
    }

    /** Getter for the name */
    public String getName(){
        return name;
    }

    @Override
    public String toString(){
        return String.format(STRING_FORMAT, id, isFull, category, instructor, weekday, startTime, userID, price, name);
    }

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
