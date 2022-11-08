package com.estore.api.estoreapi.model;

import estoreapi.model.Product;
import estoreapi.model.Review;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
/**
 * The JUnit test for the functionality of the Product class
 * 
 * @author Clayton Acheson
 */

@Tag("Model-tier")
public class ProductTest {
    private Product testProduct;

    @Test
    public void testID(){
        // Setup
        int testing_id = 12;
        Review[] reviews = new Review[3];
        // Invoke
        testProduct = new Product(testing_id, null, 0.0, "STRINGS", 0, null, null, reviews,0);

        // Analyze
        assertEquals(testing_id,testProduct.getId());
    }

    @Test
    public void testName(){
        // Setup
        String expected_name = "Cello";
        Review[] reviews = new Review[3];
        // Invoke
        testProduct = new Product(12, expected_name, 0.0, "STRINGS", 0, null, null, reviews,0);

        // Analyze
        assertEquals(expected_name,testProduct.getName());

    }

    @Test
    public void testProductPrice(){
        // Setup
        double expectedProductPrice = 16.99;
        Review[] reviews = new Review[3];
        // Invoke
        testProduct = new Product(12, null, expectedProductPrice, "STRINGS", 0,null,null, reviews,0);

        // Analyze
        assertEquals(expectedProductPrice,testProduct.getPrice());
    }

    @Test
    public void testCategory(){
        // Setup
        String expectedCategory = "STRINGS";
        Review[] reviews = new Review[3];
        // Invoke
        testProduct = new Product(12, null, 0.0, expectedCategory, 0,null,null, reviews,0);
        // Analyze
        assertEquals(expectedCategory,testProduct.getCategory());
    }

    @Test
    public void testQuantity(){
        // Setup
        int expectedQuantity = 20;
        Review[] reviews = new Review[3];
        // Invoke
        testProduct = new Product(12, null, 0.0,null, expectedQuantity, null,null, reviews,0);
        // Analyze
        assertEquals(expectedQuantity,testProduct.getQuantity());
    }

    @Test
    public void testDescription(){
        // Setup
        String testDesc = "The cello is on the lower end of the string family with about a 4ft stature and a very deep resonate sound";
        Review[] reviews = new Review[3];
        // Invoke
        testProduct = new Product(12, null, 0.0,null, 0, testDesc,null, reviews,0);
        // Analyze
        assertEquals(testDesc,testProduct.getDescription());
    }

    @Test
    public void testImage(){
        // Setup
        String testImage = "https://m.media-amazon.com/images/I/71nJxZ9AUrL.jpg";
        Review[] reviews = new Review[3];
        // Invoke
        testProduct = new Product(12, null, 0.0,null, 0,null,testImage, reviews,0);
        // Analyze
        assertEquals(testImage,testProduct.getImage());
    }
}