//
//package com.estore.api.estoreapi.model;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//
//import org.junit.jupiter.api.Tag;
//import org.junit.jupiter.api.Test;
//
//import estoreapi.model.Cart;
//
///**
// * The unit test suite for the Cart class
// *
// * @author Damon Gonzalez
// */
//@Tag("Model-tier")
//public class CartTest {
//
//    @Test
//    public void testCtor(){
//        int expectedID = 0;
//        ArrayList<Integer> productIDS = new ArrayList<>(Arrays.asList(1, 6, 3));
//        ArrayList<Integer> quantities = new ArrayList<>(Arrays.asList(1, 2, 3));
//
//        Cart cart = new Cart(expectedID, productIDS, quantities);
//
//        assertEquals(expectedID, cart.getId());
//
//        for(int i = 0; i < productIDS.size(); i++){
//            assertEquals(cart.getProductIDS().get(i), productIDS.get(i));
//        }
//
//        for(int i = 0; i < quantities.size(); i++){
//            assertEquals(cart.getQuantities().get(i), quantities.get(i));
//        }
//    }
//
//    @Test
//    public void testToString(){
//        String expectedStr = "Cart [id=0]";
//        Cart cart = new Cart(0);
//
//        assertEquals(expectedStr, cart.toString());
//    }
//
//    @Test
//    public void testGetID(){
//        // Setup
//        int testing_ID = 12;
//        // Invoke
//        Cart testCart = new Cart(testing_ID);
//
//        // Analyze
//        assertEquals(testing_ID, testCart.getId());
//    }
//
//    @Test
//    public void testSetID()
//    {
//        // Setup
//        int testing_ID = 12;
//        //Invoke
//        Cart testCart = new Cart(testing_ID);
//        assertEquals(testing_ID, );
//    }
//
//}