package estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import estoreapi.model.Cart;
import estoreapi.model.User;

/**
 * Implements the functionality for JSON file-based peristance for users
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 * 
 * @author Damon Gonzalez
 */
@Component
public class UserFileDAO implements UserDAO{
    private static final Logger LOG = Logger.getLogger(UserFileDAO.class.getName());
    Map<Integer,User> users;   // Provides a local cache of the user objects
                                           // so that we don't need to read from the file
                                           // each time
    Map<Integer,Cart> carts;   // Provides a local cache of the Cart objects
                                           // so that we don't need to read from the file
                                           // each time
    private ObjectMapper objectMapper;  // Provides conversion between user
                                        // objects and JSON text format written
                                        // to the file
    private static int nextId;  // The next Id to assign to a new user
    private String filename;    // Filename to read from and write to

    /**
     * Creates a user File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public UserFileDAO(@Value("${users.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the users from the file
    }

    /**
     * Generates the next id for a new {@linkplain User user}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of {@linkplain User users} from the tree map
     * 
     * @return  The array of {@link User users}, may be empty
     */
    private User[] getUsersArray() {
        return getUsersArray(null);
    }

    /**
     * Generates an array of {@linkplain User users} from the tree map for any
     * {@linkplain User users} whose username contains the text specified by containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain User users}
     * in the tree map
     * 
     * @return  The array of {@link User users}, may be empty
     */
    private User[] getUsersArray(String containsText) { // if containsText == null, no filter
        ArrayList<User> userArrayList = new ArrayList<>();
        

        for (User user : users.values()) {
            if (containsText == null || user.getUsername().contains(containsText)) {
                userArrayList.add(user);
            }
        }

        User[] userArray = new User[userArrayList.size()];
        userArrayList.toArray(userArray);
        return userArray;
    }

    /**
     * Attempts to find a {@linkplain User user} from the tree map that has the
     * username specified
     * 
     * If username is null, null is returned
     * @param username The username string to match against
     * @return The user with the matching username if found, null otherwise
     */
    private User getUserByUsername(String username) {
        if(username == null){
            return null;
        }
        System.out.println("received name" + username + "\n uservalues: " + users.values());
        for (User user: users.values()){
            System.out.println(user.getUsername());
            if ((user.getUsername()).equals(username)){
                return user;
            }
        }
        return null;
    }

    /**
     * Saves the {@linkplain User users} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link User users} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        User[] userArray = getUsersArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),userArray);
        return true;
    }

    /**
     * Loads {@linkplain User users} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        users = new TreeMap<>();
        nextId = 0;
        // Deserializes the JSON objects from the file into an array of users
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        User[] userArray = objectMapper.readValue(new File(filename),User[].class);

        // Add each user to the tree map and keep track of the greatest id
        for (User user : userArray) {
            users.put(user.getId(), user);
            if (user.getId() > nextId)
                nextId = user.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
        
    }

    /**
     * Private helper method to check if a username is taken so that
     * duplicate users with different ids can't be added to the database
     * @param username The username to check
     * @return true if there is a user with the same username, false otherwise
     */
    private boolean isUsernameTaken(String username){
        User[] users = getUsersArray();
        for(User user : users){
            if(user.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User[] getUsers() throws IOException {
        synchronized(users){
            return getUsersArray();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUser(int id) throws IOException {
        synchronized(users){
            if(users.containsKey(id)){
                return users.get(id);
            } else {
                return null;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findUser(String username) throws IOException {
        synchronized(users){
            return getUserByUsername(username);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User[] findUsers(String containsText) throws IOException {
        synchronized(users){
            return getUsersArray(containsText);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User createUser(User user) throws IOException {
        synchronized(users){
            if(isUsernameTaken(user.getUsername())){
                return null;
            }
            user.setCart(new Cart(user.getId()));
            int array[] = new int[0];
            // ArrayList<Integer> arrayList = new ArrayList<> ();            
            user.setProductsPurchased(array);;
            User newUser = new User(nextId(), user.getUsername(), new Cart(user.getId()), array);
            users.put(newUser.getId(), newUser);
            save();
            return newUser;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteUser(int id) throws IOException {
        synchronized(users) {
            if (users.containsKey(id)) {
                users.remove(id);
                save();
                return true;
            }
            else {
                return false;
            }
        }
    }

    @Override
    public Cart getCart(int id) throws IOException {
        synchronized(users){
            if(users.containsKey(id)){
                return users.get(id).getCart();
            } else {
                return null;
            }
        }
    }


    @Override
    public Cart updateCart(User user, Cart cart) throws IOException {
        synchronized(users){
            if(users.containsKey(user.getId())){
                users.get(cart.getId()).setCart(cart);
                save();
                return cart;
            } else {
                return null;
            }
        }
    }

    public Cart[] getCarts() throws IOException {
        synchronized(users){
            ArrayList<Cart> carts = new ArrayList<>();
            for(User user : users.values()){
                carts.add(user.getCart());
            }
            return carts.toArray(new Cart[0]);
        }
    }

    @Override
    public User updateUser(User user) throws IOException {
        synchronized(users){
            if(users.containsKey(user.getId())){
                users.put(user.getId(), user);
                save();
                return user;
            } else {
                return null;
            }
        }
    }
}