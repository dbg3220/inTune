package estoreapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an product
 * 
 * @author Hayden Cieniawski
 * @author Clayton Acheson
 * @author Damon Gonzalez
 */
public class Product {

    public enum Category {
        STRINGS,
        WOODWINDS,
        PERCUSSION,
        BRASS,
        KEYBOARDS
    }

    private static final Logger LOG = Logger.getLogger(Product.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "product [id=%d, name=%s, price=%.2f, category=%s, quantity=%d, description=%s, image=%s]";

    @JsonProperty("id")
    private int id; // The product ID
    @JsonProperty("name")
    private String name; // The name of the product to be displayed
    @JsonProperty("price")
    private double price; // The price of the given product
    @JsonProperty("category")
    private Category category; // The category of the product, for front-end classification
    @JsonProperty("quantity")
    private int quantity; // The amount of the product in stock
    @JsonProperty("description")
    private String description; // The description of the product, for the product page
    @JsonProperty("image")
    private String image; // The size of the intrument (Ex. 1/2)
    @JsonProperty("reviews")
    private Review[] reviews;

    /**
     * Create a product with the given id, name, and price.
     * 
     * @param id          The id of the product
     * @param name        The name of the product
     * @param price       The price of the product
     * @param category    The category of the product
     * @param quantity    The quantity of the product
     * @param description The description of the product
     * @param image       The image to be displayed with the product
     * @param reviews     The reviews of the product left by users
     *                    *
     *{@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields. If a field
     * is not provided in the JSON object, the Java field gets
     *                    the default Java
     *                    value, i.e. 0 for int
     */
    public Product( @JsonProperty("id") int id, 
                    @JsonProperty("name") String name, 
                    @JsonProperty("price") double price,
                    @JsonProperty("category") Category category,
                    @JsonProperty("quantity") int quantity, 
                    @JsonProperty("description") String description,
                    @JsonProperty("image") String image,
                    @JsonProperty("reviews") Review[] reviews) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
        this.description = description;
        this.image = image;
        this.reviews = reviews;
    }

    /**
     * Retrieves the id of the product
     * 
     * @return The id of the product
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the name of the product - necessary for JSON object to Java object
     * deserialization
     * 
     * @param name The name of the product
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the name of the product
     * 
     * @return The name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the price of the product - necessary for JSON object to Java object
     * deserialization
     * 
     * @param price The price of the product
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Retrieves the price of the product
     * 
     * @return The price of the product
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the category of the product - necessary for JSON object to Java object
     * deserialization
     * 
     * @param category The category of the product
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Retrieves the category of the product
     * @return The category of the product
     */
    public Category getCategory() {return category;}

    /**
     * Sets the quantity of the product - necessary for JSON object to Java object
     * deserialization
     * 
     * @param quantity The quantity of the product
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Retrieves the quantity of the product
     * 
     * @return The quantity of the product
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the Description of the product
     */
    public void setDescription(String desc) {
        this.description = desc;
    }

    /**
     * Retrieves the Description of the product
     * 
     * @return The Description of the product
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the Image of the product
     */
    public void setImage(String img) {
        this.image = img;
    }

    /**
     * Retrieves the Image of the product
     * 
     * @return The image of the product
     */
    public String getImage() {
        return image;
    }

    /**
     * Retrieves the array of all reviews of this product
     * @return An array of reviews
     */
    public Review[] getReviews(){
        return reviews;
}

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, id, name, price, category, quantity, description, image);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other) {
        if(other instanceof Product){
            Product otherProduct = (Product) other;
            return this.id == otherProduct.id &&
                   this.name.equals(otherProduct.name) &&
                   this.price == otherProduct.price &&
                   this.category.equals(otherProduct.category) &&
                   this.quantity == otherProduct.quantity &&
                   this.description.equals(otherProduct.description) &&
                   this.image.equals(otherProduct.image);
        }
        return false;
    }
}