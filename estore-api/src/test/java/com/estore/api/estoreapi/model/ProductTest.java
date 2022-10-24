package com.estore.api.estoreapi.model;

import estoreapi.model.Product;
import estoreapi.model.Product.Category;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
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

        // Invoke
        testProduct = new Product(testing_id, null, 0.0, Category.STRINGS, 0, null, null);

        // Analyze
        assertEquals(testing_id,testProduct.getId());
    }
    @Test
    public void testName(){
        // Setup
        String expected_name = "Cello";

        // Invoke
        testProduct = new Product(12, expected_name, 0.0, Category.STRINGS, 0, null, null);

        // Analyze
        assertEquals(expected_name,testProduct.getName());

    }
    @Test
    public void testProductPrice(){
        // Setup
        double expectedProductPrice = 16.99;

        // Invoke
        testProduct = new Product(12, null, expectedProductPrice, Category.STRINGS, 0,null,null);

        // Analyze
        assertEquals(expectedProductPrice,testProduct.getPrice());
    }
    @Test
    public void testCategory(){
        // Setup
        Product.Category expectedCategory = Category.STRINGS;

        // Invoke
        testProduct = new Product(12, null, 0.0, expectedCategory, 0,null,null);
        // Analyze
        assertEquals(expectedCategory,testProduct.getCategory());

        String testString = "STRINGS";

        assertEquals(testString, testProduct.getCategoryName());
    }
    @Test
    public void testQuantity(){
        // Setup
        int expectedQuantity = 20;

        // Invoke
        testProduct = new Product(12, null, 0.0,null, expectedQuantity, null,null);
        // Analyze
        assertEquals(expectedQuantity,testProduct.getQuantity());
    }
    @Test
    public void testDescription(){
        // Setup
        String testDesc = "The cello is on the lower end of the string family with about a 4ft stature and a very deep resonate sound";

        // Invoke
        testProduct = new Product(12, null, 0.0,null, 0, testDesc,null);
        // Analyze
        assertEquals(testDesc,testProduct.getDescription());
    }
    @Test
    public void testImage(){
        // Setup
        String testImage = "https://m.media-amazon.com/images/I/71nJxZ9AUrL.jpg";

        // Invoke
        testProduct = new Product(12, null, 0.0,null, 0,null,testImage);
        // Analyze
        assertEquals(testImage,testProduct.getImage());
    }

}