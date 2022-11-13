package com.estore.api.estoreapi.model;

import org.junit.jupiter.api.Tag;
import estoreapi.model.Lesson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
/**
 * The JUnit test for the functionality of the Lesson class
 * 
 * @author Donovan Cataldo
 * @author Damon Gonzalez
 */

@Tag("Model-tier")
public class LessonTest {

    @Test
    public void testGetters(){
        Lesson test = new Lesson(0, true, "STRINGS", "Amadeus", "MONDAY", 12, 2, 100.0, "Violin Masterclass");

        int id = test.getID();
        boolean isFull = test.isFull();
        String category = test.getCategory();
        String instructor = test.getInstructor();
        String weekday = test.getWeekDay();
        int startTime = test.getStartTime();
        int userID = test.getUserID();
        double price = test.getPrice();
        String name = test.getName();

        assertEquals(id, 0);
        assertTrue(isFull);
        assertEquals(category, "STRINGS");
        assertEquals(instructor, "Amadeus");
        assertEquals(weekday, "MONDAY");
        assertEquals(startTime, 12);
        assertEquals(userID, 2);
        assertEquals(price, 100.0);
        assertEquals(name, "Violin Masterclass");
    }

    @Test
    public void testToString(){
        Lesson test = new Lesson(0, true, "STRINGS", "Amadeus", "MONDAY", 12, 2, 100.0, "Violin Masterclass");
        String expected = "lesson [id=0, isFull=true, category=STRINGS, instructor=Amadeus, weekday=MONDAY, startTime=12, userID=2, price=100.0, name=Violin Masterclass]";
    
        String output = test.toString();
        
        assertEquals(expected, output);
    }

    @Test
    public void testEquals(){
        //Test Success
        Lesson test1 = new Lesson(0, true, "STRINGS", "Amadeus", "MONDAY", 12, 2, 100.0, "Violin Masterclass");
        Lesson test2 = new Lesson(0, true, "STRINGS", "Amadeus", "MONDAY", 12, 2, 100.0, "Violin Masterclass");

        assertEquals(test1, test2);

        //Test Failure
        Lesson test3 = new Lesson(0, true, "STRINGS", "Amdeus", "MONDAY", 12, 2, 100.0, "Violin Masterclass");
        
        assertNotEquals(test1, test3);

        assertNotEquals(test1, new Object());
    }

    @Test
    public void testHashCode(){
        Lesson test1 = new Lesson(0, true, "STRINGS", "Amadeus", "MONDAY", 12, 2, 100.0, "Violin Masterclass");
        Lesson test2 = new Lesson(0, true, "STRINGS", "Amadeus", "MONDAY", 12, 2, 100.0, "Violin Masterclass");

        int code1 = test1.hashCode();
        int code2 = test2.hashCode();

        assertEquals(code1, code2);
    }
}
