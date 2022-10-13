import estoreapi.model.Cart;
import estoreapi.model.User;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
/**
 * The unit test for the user model class
 * 
 * @author Donovan Cataldo
 */

public class UserTest {
private User testUser;
    @Test
    public void testID(){
        // Setup
        int expected_id = 99;

        // Invoke
        testUser = new User(expected_id, null, null, null, null, null, null, 0, 0, null, null, false);

        // Analyze
        assertEquals(expected_id,testUser.getId());
    }
    @Test
    public void testName(){
        // Setup
        String expected_name = "Donovan";

        // Invoke
        testUser = new User(null, expected_name, null, null, null, null, null, null, null, null, null, false);

        // Analyze
        assertEquals(expected_name,testUser.getName());
    }
    @Test
    public void testUsername(){
        // Setup
        String expected_username = "Kenn";

        // Invoke
        testUser = new User(null, null, expected_username, null, null, null, null, null, null, null, null, false);

        // Analyze
        assertEquals(expected_username,testUser.getUsername());
    }
    @Test
    public void testEmail(){
        // Setup
        String expected_email = "ILOVESWEN@rit.edu";

        // Invoke
        testUser = new User(null, null, null, expected_email, null, null, null, null, null, null, null, false);

        // Analyze
        assertEquals(expected_email,testUser.getEmail());
    }
    @Test
    public void testPassword(){
        // Setup
        String expected_password = "MyFirstPetsName1234";

        // Invoke
        testUser = new User(null, null, null, null, expected_password, null, null, null, null, null, null, false);

        // Analyze
        assertEquals(expected_password,testUser.getPassword());
    }
    @Test
    public void testAddress(){
        // Setup
        String expected_address = "1 Lomb Memorial Dr, Rochester, NY 14623";

        // Invoke
        testUser = new User(null, null, null, null, null, expected_address, null, null, null, null, null, false);

        // Analyze
        assertEquals(expected_address,testUser.getAddress());
    }
    @Test
    public void testCcnum(){
        // Setup
        String expected_ccnum = "1111-1111-1111";

        // Invoke
        testUser = new User(null, null, null, null, null, null, expected_ccnum, null, null, null, null, false);

        // Analyze
        assertEquals(expected_ccnum,testUser.getccnum());
    }
    @Test
    public void testCcmon(){
        // Setup
        String expected_ccmon = "12";

        // Invoke
        testUser = new User(null, null, null, null, null, null, null, expected_ccmon, null, null, null, false);

        // Analyze
        assertEquals(expected_ccmon,testUser.getccmon());
    }
    @Test
    public void testCcyear(){
        // Setup
        String expected_ccyear = "25";

        // Invoke
        testUser = new User(null, null, null, null, null, null, null, null,expected_ccyear , null, null, false);

        // Analyze
        assertEquals(expected_ccyear,testUser.getccyear());
    }
    @Test
    public void testCart(){
        // Setup
        Cart expected_cart = new Cart(99, null, 0);

        // Invoke
        testUser = new User(0, null, null, null, null, null, null, null, null, expected_cart, null, false);

        // Analyze
        assertEquals(expected_cart,testUser.getCart());
    }
    @Test
    public void testFriends(){
        // Setup
        User[] expected_friends = new User[5];
        expected_friends[0] = new User(5000, "Test1", "Test1", null, null, null, null, 0, 0, null, null, false);
        expected_friends[1] = new User(0, "Test2", "Test2", null, null, null, null, 0, 0, null, null, false);
        expected_friends[2] = new User(null, "test3", "Test3", null, null, null, null, 0, 0, null, null, false);
        expected_friends[3] = new User(27, "Test4", "Test4", null, null, null, null, 0, 0, null, null, false);
        expected_friends[4] = new User(99, "Test5", "Test5", null, null, null, null, 0, 0, null, null, false);

        // Invoke
        testUser = new User(0, null, null, null, null, null, null, 0, 0, null, expected_friends, false);

        // Analyze
        assertEquals(expected_friends, testUser.getFriends());
    }
    @Test
    public void testIsAdmin(){
        // Setup
        Boolean exepected_isadmin = true;

        // Invoke 
        testUser = new User(null, null, null, null, null, null, null, 0, 0, null, null, exepected_isadmin);
    
        // Analyze
        assertEquals(exepected_isadmin,testUser.isAdmin());
    }
}
