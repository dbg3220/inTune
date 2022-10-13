import estoreapi.model.Product;
import estoreapi.model.Product.Category;
import estoreapi.model.Lesson;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
/**
 * The JUnit test for the functionality of the Lesson class
 * 
 * @author Clayton Acheson
 */

public class LessonTest {
private Lesson testLesson;
    @Test
    public void testID(){
        // Setup
        int testing_id = 1;

        // Invoke
        testLesson = new Lesson(testing_id, null, 0.0, Category.STRINGS, 0, false,false,true,null, null,null,null,false);

        // Analyze
        assertEquals(expected_id,testLesson.getId());
    }
    @Test
    public void testName(){
        // Setup
        String expected_name = "Cello Lesson";

        // Invoke
        testLesson = new Lesson(null, expected_name, 0.0, Category.STRINGS, 0, false,false,true,null, null,null,null,false);

        // Analyze
        assertEquals(expected_name,testLesson.getName());
    }
    @Test
    public void testLessonPrice(){
        // Setup
        double expectedLessonPrice = 60.0;

        // Invoke
        testLesson = new Lesson(null, null, expectedLessonPrice, Category.STRINGS, 0, false,false,true,null, null,null,null,false);

        // Analyze
        assertEquals(expectedLessonPrice,testLesson.getPrice());
    }
    @Test
    public void testCategory(){
        // Setup
        Product.Category expectedCategory = Category.STRINGS;

        // Invoke
        testLesson = new Lesson(null, null, 0.0, expectedCategory, 0, false,false,true,null, null,null,null,false);
        // Analyze
        assertEquals(expectedCategory,testLesson.getCategory());
    }
    @Test
    public void testQuantity(){
        // Setup
        int expectedQuantity = 2;

        // Invoke
        testLesson = new Lesson(null, null, 0.0,null, expectedQuantity, false,false,true,null, null,null,null,false);
        // Analyze
        assertEquals(expectedQuantity,testLesson.getQuantity());
    }
    @Test
    public void testIsInstrument(){
        // Setup
        boolean expectedTruthValue = false;

        // Invoke
        testLesson = new Lesson(null, null, 0.0,null, 0, expectedTruthValue,false,true,null, null,null,null,false);
        // Analyze
        assertEquals(expectedTruthValue,testLesson.isLesson());
    }
    @Test
    public void testIsEquipment(){
        // Setup
        boolean expectedTruthValue = true;

        // Invoke
        testLesson = new Lesson(null, null, 0.0,null, 0, false,expectedTruthValue,true,null, null,null,null,false);
        // Analyze
        assertEquals(expectedTruthValue,testLesson.isEquipment());
    }
    @Test
    public void testIsLesson(){
        // Setup
        boolean expectedTruthValue = false;

        // Invoke
        testLesson = new Lesson(null, null, 0.0,null, 0, false,false,expectedTruthValue, null, null,null,null,false);
        // Analyze
        assertEquals(expectedTruthValue,testLesson.isLesson());
    }
    @Test
    public void testInstructor(){
        // Setup
        String testingInstructor = "Dr.Ma";

        // Invoke
        testLesson = new Lesson(null, null, 0.0,null, 0, false,false,false, testingInstructor, null,null,null,false);
        // Analyze
        assertEquals(testingInstructor,testLesson.getInstructor());
    }
    @Test
    public void testStudent(){
        // Setup
        User testingStudent = new User(null, expected_name, null, null, null, null, null, null, null, null, null, false);

        // Invoke
        testLesson = new Lesson(null, null, 0.0,null, 0, false,false,false, null, testingStudent,null,null,false);
        // Analyze
        assertEquals(testingStudent,testLesson.getStudent());
    }
    @Test
    public void testIsFull(){
        // Setup
        boolean testValue = true;

        // Invoke
        testLesson = new Lesson(null, null, 0.0,null, 0, false,false,false, null, null,null,null,testValue);
        // Analyze
        assertEquals(testValue,testLesson.isFull());
    }
    @Test
    public void testStartTime(){
        // Setup
        String testingStartTime = "12:00pm";

        // Invoke
        testLesson = new Lesson(null, null, 0.0,null, 0, false,false,false, null, null,null,testingStartTime,false);
        // Analyze
        assertEquals(testingStartTime,testLesson.getStartTime());
    }
    @Test
    public void testWeekDay(){
        // Setup
        String testingWeekDay = "Tuesday";

        // Invoke
        testLesson = new Lesson(null, null, 0.0,null, 0, false,false,false, null, null,testingWeekDay,null,false);
        // Analyze
        assertEquals(testingWeekDay,testLesson.getWeekDay());
    }

}
