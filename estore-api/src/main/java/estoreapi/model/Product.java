package estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a product
 * 
 * @author Damon Gonzalez
 */
public class Product {

    /** Format string for a Product */
    static final String STRING_FORMAT = "Product [id=%d, name=%s, price=%.2f, category=%s, quantity=%d, description=%s, image=%s, reviews=%s]";

    /** The id of the product, a unique identifier in storage */
    @JsonProperty("id") private int id;
    /** The name of the product */
    @JsonProperty("name") private String name;
    /** The price of the product */
    @JsonProperty("price") private double price;
    /** The category of the product */
    @JsonProperty("category") private Category category;
    /** The quantity of the product in the store's inventory */
    @JsonProperty("quantity") private int quantity;
    /** A description of the product given by the store owner */
    @JsonProperty("description") private String description;
    /** The URL of an image representing this product */
    @JsonProperty("image") private String image;
    /** An array of reviews of this product left by Users, may be empty */
    @JsonProperty("reviews") private Review[] reviews;

    /**
     * Public constructor to deserialize products objects from a json file
     * @param id The id of the product
     * @param name The name of the product
     * @param price The price of the product
     * @param category The category the product falls under
     * @param quantity The amount of this product in the store's inventory
     * @param description The description of this product created by the admin
     * @param image The url to an image representing this product
     * @param reviews The reviews of this product left by users who purchased it
     * 
     * throws an IllegalProductException if business logic is violated
     */
    public Product(@JsonProperty("id") int id, 
                   @JsonProperty("name") String name, 
                   @JsonProperty("price") double price,
                   @JsonProperty("category") Category category,
                   @JsonProperty("quantity") int quantity, 
                   @JsonProperty("description") String description,
                   @JsonProperty("image") String image,
                   @JsonProperty("reviews") Review[] reviews) {
        if(name == null || name.equals(""))
            throw new IllegalProductException("name cannot be null or an empty string");
        if(price < 0)
            throw new IllegalProductException("price cannot be negative");
        if(quantity < 0)
            throw new IllegalProductException("quantity cannot be negative");
        if(reviews == null)
            throw new IllegalProductException("reviews cannot be null");
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
     * Private exception class to halt program when a Product is
     * instantiated with values that are illegal under the business logic of this
     * API.
     */
    private class IllegalProductException extends RuntimeException {
        public IllegalProductException(String message){
            super("Product: " + message);
        }
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
     * Retrieves the name of the product
     * 
     * @return The name of the product
     */
    public String getName() {
        return name;
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
     * Retrieves the category of the product
     * @return The category of the product
     */
    public Category getCategory() { 
        return category; 
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
     * Retrieves the Description of the product
     * 
     * @return The Description of the product
     */
    public String getDescription() {
        return description;
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

    @Override
    public String toString() {
        return String.format(STRING_FORMAT, id, name, price, category, quantity, description, image, reviews.toString());
    }

    /**
     * This equals() disregards the id and reviews attributes of the Product
     * class for comparison.
     */
    @Override
    public boolean equals(Object other) {
        if(other instanceof Product){
            Product otherProduct = (Product) other;
            return this.name.equals(otherProduct.name) &&
                   this.price == otherProduct.price &&
                   this.category.equals(otherProduct.category) &&
                   this.quantity == otherProduct.quantity &&
                   this.description.equals(otherProduct.description) &&
                   this.image.equals(otherProduct.image);
        }
        return false;
    }
}
