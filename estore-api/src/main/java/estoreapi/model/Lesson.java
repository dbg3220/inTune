package estoreapi.model;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    public Lesson(
        @JsonProperty("id") int id,
        @JsonProperty("price") double price,
        @JsonProperty("weekday") String weekday,
        @JsonProperty("startTime") int startTime,
        @JsonProperty("name") String name
    ){
        this.id = id;
        this.isFull = false;
        this.category = "";
        this.instructor = "";
        this.weekday = weekday;
        this.startTime = startTime;
        this.userID = -1;
        this.price = price;
        this.name = name;

    }

    public void setLesson(String category, String instructor, int userID){
        this.isFull = true;
        this.category = category;
        this.instructor = instructor;
        this.userID = userID;
    }

    public void clearLesson(){
        this.isFull = false;
        this.category = null;
        this.instructor = null;
        this.userID = -1;
    }

    public boolean getIsFull(){
        return this.isFull;
    }

    public String getCategory(){
        return this.category;
    }

    public String getIntructor(){
        return this.instructor;
    }

    public int getUserID(){
        return this.userID;
    }

    public void setWeekDay(String weekday){
        this.weekday = weekday;
    }

    public String getWeekDay(){
        return this.weekday;
    }

    public void setStartTime(int startTime){
        this.startTime = startTime;
    }

    public int getStartTime(){
        return this.startTime;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public double getPrice(){
        return this.price;
    }

    public int getID(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
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
}
