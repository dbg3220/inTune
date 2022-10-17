import estoreapi.model.Product;
import estoreapi.model.Product.Category;
import estoreapi.model.Equipment;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
/**
 * The JUnit test for the functionality of the Equipment class
 * 
 * @author Clayton Acheson
 */

public class EquipmentTest {
private Equipment testEquipment;
    @Test
    public void testID(){
        // Setup
        int testing_id = 12;

        // Invoke
        testEquipment = new Equipment(testing_id, null, 0.0, Category.STRINGS, 0, false,true,false);

        // Analyze
        assertEquals(testing_id,testEquipment.getId());
    }
    @Test
    public void testName(){
        // Setup
        String expected_name = "Rosin";

        // Invoke
        testEquipment = new Equipment(null, expected_name, 0.0, Category.STRINGS, 0, false,true,false);

        // Analyze
        assertEquals(expected_name,testEquipment.getName());
    }
    @Test
    public void testEquipmentPrice(){
        // Setup
        double expectedEquipmentPrice = 16.99;

        // Invoke
        testEquipment = new Equipment(null, null, expectedEquipmentPrice, Category.STRINGS, 0, false,true,false);

        // Analyze
        assertEquals(expectedEquipmentPrice,testEquipment.getPrice());
    }
    @Test
    public void testCategory(){
        // Setup
        Product.Category expectedCategory = Category.STRINGS;

        // Invoke
        testEquipment = new Equipment(null, null, 0.0, expectedCategory, 0, false,true,false);
        // Analyze
        assertEquals(expectedCategory,testEquipment.getCategory());
    }
    @Test
    public void testQuantity(){
        // Setup
        int expectedQuantity = 20;

        // Invoke
        testEquipment = new Equipment(null, null, 0.0,null, expectedQuantity, false,true,false);
        // Analyze
        assertEquals(expectedQuantity,testEquipment.getQuantity());
    }
    @Test
    public void testIsInstrument(){
        // Setup
        boolean expectedTruthValue = false;

        // Invoke
        testEquipment = new Equipment(null, null, 0.0,null, 0, expectedTruthValue,true,false);
        // Analyze
        assertEquals(expectedTruthValue,testEquipment.isInstrument());
    }
    @Test
    public void testIsEquipment(){
        // Setup
        boolean expectedTruthValue = true;

        // Invoke
        testEquipment = new Equipment(null, null, 0.0,null, 0, false,expectedTruthValue,false);
        // Analyze
        assertEquals(expectedTruthValue,testEquipment.isEquipment());
    }
    @Test
    public void testIsLesson(){
        // Setup
        boolean expectedTruthValue = false;

        // Invoke
        testEquipment = new Equipment(null, null, 0.0,null, 0, false,true,expectedTruthValue);
        // Analyze
        assertEquals(expectedTruthValue,testEquipment.isLesson());
    }

}
