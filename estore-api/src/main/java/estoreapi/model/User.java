package estoreapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents an product
 * 
 * @author Hayden Cieniawski
 */
public  class User {


    private static final Logger LOG = Logger.getLogger(User.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "User [id=%d, name=%s, username=%s, email=%s, password=%s, address=%s, ccnum=%d, ccmon=%d, ccyear=%d, isAdmin=%b]";

    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("username") private String username;
    @JsonProperty("email") private String email;
    @JsonProperty("password") private String password;
    @JsonProperty("address") private String address;
    @JsonProperty("ccnum") private int ccnum;
    @JsonProperty("ccmon") private int ccmon;
    @JsonProperty("ccyear") private int ccyear;
    @JsonProperty("isAdmin") private boolean isAdmin;
    @JsonProperty("cart") private Cart cart;
    @JsonProperty("friends") private User[] friends;

    /**
     * Create a product with the given id, name, and price.
     * @param id The id of the product
     * @param name The name of the product
     * @param price The price of the product
     * @param category The category of the product
     * @param subcategory The subcategory of the product
     * @param quantity The quantity of the product
     *     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public User(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("username") String username, 
    @JsonProperty("email") String email, @JsonProperty("password") String password, @JsonProperty("address") String address, 
    @JsonProperty("ccnum") int ccnum, @JsonProperty("ccmon") int ccmon, @JsonProperty("ccyear") int ccyear, 
    @JsonProperty("isAdmin") boolean isAdmin, @JsonProperty("cart") Cart cart, @JsonProperty("friends") User[] friends) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.address = address;
        this.ccnum = ccnum;
        this.ccmon = ccmon;
        this.ccyear = ccyear;
        this.isAdmin = isAdmin;
        this.cart = cart;
        this.friends = friends;
    }

    /**
     * Retrieves the id of the product
     * @return The id of the product
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the name of the product
     * @return The name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the username of the user
     * @return The username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Retrieves the email of the user
     * @return The email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Retrieves the password of the user
     * @return The password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Retrieves the address of the user
     * @return The address of the user
     */
    public String getAddress() {
        return address;
    }

    /**
     * Retrieves the ccnum of the user
     * @return The ccnum of the user
     */
    public int getccnum() {
        return ccnum;
    }

    /**
     * Retrieves the ccmon of the user
     * @return The ccmon of the user
     */
    public int getccmon() {
        return ccmon;
    }

    /**
     * Retrieves the ccyear of the user
     * @return The ccyear of the user
     */
    public int getccyear() {
        return ccyear;
    }

    /**
     * Retrieves the isAdmin of the user
     * @return The isAdmin of the user
     */
    public boolean getIsAdmin() {
        return isAdmin;
    }

    /**
     * Retrieves the cart of the user
     * @return The cart of the user
     */
    public Cart getCart() {
        return cart;
    }

    /**
     * Retrieves the friends of the user
     * @return The friends of the user
     */
    public User[] getFriends() {
        return friends;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id,name,username,email,password,address,ccnum,ccmon,ccyear,isAdmin);
    }
}