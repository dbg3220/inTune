package com.estore.api.estoreapi.model;

import org.junit.jupiter.api.Tag;
import estoreapi.model.Lesson;
import estoreapi.model.Product.Category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
/**
 * The JUnit test for the functionality of the Lesson class
 * 
 * @author Donovan Cataldo
 * @author Damon Gonzalez
 */

@Tag("Model-tier")
public class LessonTest {
    private Lesson testLesson;

    @Test
    public void testID(){
        // Setup
        int testing_id = 12;

        // Invoke
        testLesson = new Lesson(testing_id, false, Category.STRINGS, "", "", 0, 0, 0.0, "");

        // Analyze
        assertEquals(testing_id, testLesson.getID());
    }

    @Test
    public void testName(){
        // Setup
        String expected_name = "Cello";

        // Invoke
        testLesson = new Lesson(0, false, Category.STRINGS, "", "", 0, 0, 0.0, expected_name);

        // Analyze
        assertEquals(expected_name,testLesson.getName());

    }

    @Test
    public void testPrice(){
        // Setup
        double expectedProductPrice = 16.99;

        // Invoke
        testLesson = new Lesson(0, false, Category.STRINGS, "", "", 0, 0, expectedProductPrice, "");

        // Analyze
        assertEquals(expectedProductPrice,testLesson.getPrice());
    }

    @Test

    public void testWeekday(){
        // Setup
        String expectedWeekday = "Monday";

        // Invoke
        testLesson = new Lesson(0, false, Category.STRINGS, "", expectedWeekday, 0, 0, 0.0, "");

        // Analyze
        assertEquals(expectedWeekday, testLesson.getWeekDay());
    }
}
