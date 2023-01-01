package estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a lesson
 * 
 * @author Damon Gonzalez
 */
public class Lesson {

    /** Format string for a Lesson */
    static final String STRING_FORMAT = "Lesson [id=%d, category=%s, instructor=%s, weekday=%s, startTime=%d, userID=%d, price=%s, name=%s]";

    /** The id of the lesson, a unique identifier in storage */
    @JsonProperty("id") private int id;
    /** The category of instrument that this lesson is for */
    @JsonProperty("category") private Category category;
    /** The name of the instructor for this lesson */
    @JsonProperty("instructor") private String instructor;
    /** The day of the week this lesson is held on */
    @JsonProperty("weekday") private Weekday weekday;
    /** The starting time (in hours) of this lesson */
    @JsonProperty("startTime") private int startTime;
    /** The id of the user who has scheduled this lesson, -1 if scheduled by no user */
    @JsonProperty("userID") private int userID;
    /** The weekly price of this lesson */
    @JsonProperty("price") private double price;
    /** The name of the lesson (i.e. Piano Lesson, Cello masterclass) */
    @JsonProperty("name") private String name;

    /**
     * Public constructor to deserialize lesson objects from a json file
     * @param id The id of the lesson
     * @param category The category of instrument this lesson will teach
     * @param instructor The name of the instructor for this lesson
     * @param weekday The day of the week this lesson is taught on
     * @param startTime The time during the day this lesson starts on
     * @param userID The id of the user who has scheduled this lesson, -1 is default
     * @param price The weekly price of this lesson
     * @param name The name of this lesson
     * 
     * throws an IllegalLessonException if business logic is violated
     */
    public Lesson(@JsonProperty("id") int id,
                  @JsonProperty("category") Category category,
                  @JsonProperty("instructor") String instructor,
                  @JsonProperty("weekday") Weekday weekday,
                  @JsonProperty("startTime") int startTime,
                  @JsonProperty("userID") int userID,
                  @JsonProperty("price") double price,
                  @JsonProperty("name") String name){
        if(instructor == null || instructor.equals(""))
            throw new IllegalLessonException("instructor cannot be null or an empty string");
        if(startTime < 9 && startTime > 5)
            throw new IllegalLessonException("startTime must be between the hours of 9 to 5(inclusive)");
        if(userID < -1)
            throw new IllegalLessonException("userID must be -1 or a nonnegative integer");
        if(price < 0) 
            throw new IllegalLessonException("price cannot be negative");
        this.id = id;
        this.category = category;
        this.instructor = instructor;
        this.weekday = weekday;
        this.startTime = startTime;
        this.userID = userID;
        this.price = price;
        this.name = name;
    }

    /**
     * Private exception class to halt program when a Lesson is
     * instantiated with values that are illegal under the business logic of this
     * API.
     */
    private class IllegalLessonException extends RuntimeException {
        public IllegalLessonException(String message){
            super("Lesson: " + message);
        }
    }

    /** Getter for id */
    public int getID(){
        return id;
    }
    
    /** Getter for the category */
    public Category getCategory(){
        return category;
    }

    /** Getter for the instructor */
    public String getInstructor(){
        return instructor;
    }

    /** Getter for the weekday */
    public Weekday getWeekDay(){
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
        return String.format(STRING_FORMAT, id, category, instructor, weekday, startTime, userID, price, name);
    }

    /**
     * This equals() disregards the id attribute of the Lesson class for
     * comparison.
     */
    @Override
    public boolean equals(Object other){
        if(other instanceof Lesson){
            Lesson otherLesson = (Lesson) other;
            return this.category.equals(otherLesson.category) &&
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
