package com.estore.api.estoreapi.model;

import estoreapi.model.Product;
import estoreapi.model.Product.Category;
import estoreapi.model.Instrument;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
/**
 * The JUnit test for the functionality of the Instrument class
 * 
 * @author Clayton Acheson
 */

public class InstrumentTest {
private Instrument testInstrument;
    @Test
    public void testID(){
        // Setup
        int testing_id = 16;

        // Invoke
        testInstrument = new Instrument(testing_id, null, 0.0, Category.STRINGS, 0, true,false,false, null);

        // Analyze
        assertEquals(testing_id,testInstrument.getId());
    }
    @Test
    public void testName(){
        // Setup
        String expected_name = "Cello";

        // Invoke
        testInstrument = new Instrument(12, expected_name, 0.0, Category.STRINGS, 0, true,false,false, null);

        // Analyze
        assertEquals(expected_name,testInstrument.getName());
    }
    @Test
    public void testInstrumentPrice(){
        // Setup
        double expectedInstrumentPrice = 1699.99;

        // Invoke
        testInstrument = new Instrument(12, null, expectedInstrumentPrice, Category.STRINGS, 0, true,false,false, null);

        // Analyze
        assertEquals(expectedInstrumentPrice,testInstrument.getPrice());
    }
    @Test
    public void testCategory(){
        // Setup
        Product.Category expectedCategory = Category.STRINGS;

        // Invoke
        testInstrument = new Instrument(12, null, 0.0, expectedCategory, 0, true,false,false, null);
        assertEquals(expectedCategory,testInstrument.getCategory());
    }
    @Test
    public void testQuantity(){
        // Setup
        int expectedQuantity = 4;

        // Invoke
        testInstrument = new Instrument(12, null, 0.0, Category.STRINGS, expectedQuantity, true,false,false, null);
        // Analyze
        assertEquals(expectedQuantity,testInstrument.getQuantity());
    }
    @Test
    public void testIsInstrument(){
        // Setup
        boolean expectedTruthValue = true;

        // Invoke
        testInstrument = new Instrument(12, null, 0.0, Category.STRINGS, 0, expectedTruthValue,false,false, null);
        // Analyze
        assertEquals(expectedTruthValue,testInstrument.getIsInstrument());
    }
    @Test
    public void testIsEquipment(){
        // Setup
        boolean expectedTruthValue = false;

        // Invoke
        testInstrument = new Instrument(12, null, 0.0, Category.STRINGS, 0, false,expectedTruthValue,false, null);
        // Analyze
        assertEquals(expectedTruthValue,testInstrument.getIsEquipment());
    }
    @Test
    public void testIsLesson(){
        // Setup
        boolean expectedTruthValue = false;

        // Invoke
        testInstrument = new Instrument(12, null, 0.0, Category.STRINGS, 0, true,false,expectedTruthValue, null);
        // Analyze
        assertEquals(expectedTruthValue,testInstrument.getIsLesson());
    }

}
