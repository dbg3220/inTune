package estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.*;

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

    @Override
    public User[] getUsers() throws IOException {
        synchronized(users){
            return getUsersArray();
        }
    }

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

    @Override
    public User createUser(User user) throws IOException {
        synchronized(users){
            //TODO redo this horrifying piece
            nextId();
            return null;
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
}