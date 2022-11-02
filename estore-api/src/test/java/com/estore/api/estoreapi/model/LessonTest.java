package com.estore.api.estoreapi.model;

import org.junit.jupiter.api.Tag;
import estoreapi.model.Lesson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
/**
 * The JUnit test for the functionality of the Lesson class
 * 
 * @author Donovan Cataldo
 */

@Tag("Model-tier")
public class LessonTest {
    private Lesson testLesson;

    @Test
    public void testID(){
        // Setup
        int testing_id = 12;

        // Invoke
        testLesson = new Lesson(testing_id, 0, null,0, null);

        // Analyze
        assertEquals(testing_id, testLesson.getID());
    }

    @Test
    public void testName(){
        // Setup
        String expected_name = "Cello";

        // Invoke
        testLesson = new Lesson(0, 0, null, 0, expected_name);

        // Analyze
        assertEquals(expected_name,testLesson.getName());

    }

    @Test
    public void testPrice(){
        // Setup
        double expectedProductPrice = 16.99;

        // Invoke
        testLesson = new Lesson(0, expectedProductPrice, null, 0, null);

        // Analyze
        assertEquals(expectedProductPrice,testLesson.getPrice());
    }

    @Test
    public void testSetLessons(){
        // Setup
        boolean expectedIsFull = true;
        String expectedCategory = "STRINGS";
        String expectedInstructor = "Bay toe van";
        int expectedUserID = 89;

        // Invoke
        testLesson = new Lesson(0, 0, null, 0, null);
        testLesson.setLesson(expectedCategory, expectedInstructor, expectedUserID);

        // Analyze
        assertEquals(expectedCategory, testLesson.getCategory());
        assertEquals(expectedIsFull, testLesson.getIsFull());
        assertEquals(expectedInstructor, testLesson.getIntructor());
        assertEquals(expectedUserID, testLesson.getUserID());
    
    }

    @Test
    public void testClearLesson(){
        // Setup
        boolean expectedIsFull = false;
        String expectedCategory = null;
        String expectedInstructor = null;
        int expectedUserID = -1;

        // Invoke
        testLesson = new Lesson(0, 0, null, 0, null);
        testLesson.clearLesson();

        // Analyze
        assertEquals(expectedCategory, testLesson.getCategory());
        assertEquals(expectedIsFull, testLesson.getIsFull());
        assertEquals(expectedInstructor, testLesson.getIntructor());
        assertEquals(expectedUserID, testLesson.getUserID());
    }

    @Test
    public void testWeekday(){
        // Setup
        String expectedWeekday = "Monday";

        // Invoke
        testLesson = new Lesson(0, 0, expectedWeekday, 0, null);

        // Analyze
        assertEquals(expectedWeekday, testLesson.getWeekDay());
    }
}
