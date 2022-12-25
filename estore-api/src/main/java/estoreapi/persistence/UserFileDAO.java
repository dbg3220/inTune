package estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import estoreapi.model.User;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
* Implements methods described in UserDAO
* 
* @author Damon Gonzalez
*/
@Component
public class UserFileDAO implements UserDAO {

    /** The map storing users that are mapped from their id */
    Map<Integer, User> users;
    /** Class used to write java objects to json files */
    private ObjectMapper objectMapper;
    /** The next id of a user, to be only accessed through nextId() */
    private static int nextId;
    /** The file to read/write to */
    private String filename;

    /**
     * Public constructor for UserFileDAO
     * @param filename The path of the file to read/write
     * @param objectMapper Provides JSON Object serialization and deserialization
     * @throws IOException when file cannot be accessed or read from
     */
    public UserFileDAO(@Value("${users.file}") String filename,
                          ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    /**
     * Provides the next id for a user to be created with in order
     * to preserve uniqueness.
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Saves user objects in the map of users to file storage
     * @throws IOException when file cannot be accessed or written to
     */
    private void save() throws IOException {
        User[] userArray = getUsersArray();
        objectMapper.writeValue(new File(filename),userArray);
    }

    /**
     * Loads user objects from file storage
     * @throws IOException when file cannot be accessed or read from
     */
    private void load() throws IOException {
        users = new TreeMap<>();
        nextId = 0;
        User[] userArray = objectMapper.readValue(new File(filename),User[].class);
        for (User user : userArray) {
            users.put(user.getId(), user);
            if (user.getId() > nextId)
                nextId = user.getId();
        }
        ++nextId;//make the next id greater than the last
    }
    
    /**
     * Generates an array of users from the map of users
     * 
     * @return The array of users, may be empty
     */
    private User[] getUsersArray() {
        ArrayList<User> userArrayList = new ArrayList<>();
        for (User user : users.values()) {
            userArrayList.add(user);
        }
        User[] userArray = new User[userArrayList.size()];
        userArrayList.toArray(userArray);
        return userArray;
    }

    @Override
    public User[] getUsers() throws IOException{
        synchronized(users) {
            return getUsersArray();
        }
    }

    @Override
    public User getUser(int id) throws IOException{
        synchronized(users) {
            if( !users.containsKey(id) )
                return null;
                
            return users.get(id);
        }
    }

    @Override
    public User createUser(User user) throws IOException {
        synchronized(users) {
            User newUser = new User(nextId(),
                                    user.getUsername(),
                                    user.getCart());
            users.put(newUser.getId(),newUser);
            save();
            return newUser;
        }
    }

    @Override
    public User updateUser(User user) throws IOException {
        synchronized(users) {
            if( !users.containsKey(user.getId()) )
                return null;

            users.put(user.getId(),user);
            save();
            return user;
        }
    }

    @Override
    public boolean deleteUser(int id) throws IOException {
        synchronized(users) {
            if( !users.containsKey(id) )
                return false;

            users.remove(id);
            save();
            return true;
        }
    }
}
