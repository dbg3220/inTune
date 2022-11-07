package com.estore.api.estoreapi.model;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import estoreapi.model.Review;
/**
 * The unit test for the review model class
 * 
 * @author Donovan Cataldo
 */
@Tag("Model-tier")
public class ReviewTest {
    
    @Test
    public void testUsername(){
        String expectedUsername = "Donovan";
        Review review = new Review(expectedUsername, 0, null);
        assertEquals(expectedUsername, review.getUsername());
    }

    @Test
    public void testDescription(){
        String expectedDescription = "Such a great product";
        Review review = new Review("", 0, expectedDescription);
        assertEquals(expectedDescription, review.getDescription());
    }

    @Test
    public void testRating(){
        int expectedRating = 5;
        Review review = new Review(null, expectedRating, null);
        assertEquals(expectedRating, review.getRating());
    }


}
