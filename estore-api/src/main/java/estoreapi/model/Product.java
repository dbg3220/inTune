package estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an product
 * 
 * @author Damon Gonzalez
 */
public class Product {

    static final String STRING_FORMAT = "product [id=%d, name=%s, price=%.2f, category=%s, quantity=%d, description=%s, image=%s]";

    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("price")
    private double price;
    @JsonProperty("category")
    private String category;
    @JsonProperty("quantity")
    private int quantity;
    @JsonProperty("description")
    private String description; 
    @JsonProperty("image")
    private String image;
    @JsonProperty("reviews")
    private Review[] reviews;

    /**
     * Create a product with the given id, name, and price.
     * 
     * @param id          The id of the product
     * @param name        The name of the product
     * @param price       The price of the product
     * @param string      The category of the product
     * @param quantity    The quantity of the product
     * @param description The description of the product
     * @param image       The image to be displayed with the product
     * @param reviews     The reviews of the product left by users
     */
    public Product( @JsonProperty("id") int id, 
                    @JsonProperty("name") String name, 
                    @JsonProperty("price") double price,
                    @JsonProperty("category") String category,
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
    public String getCategory() { 
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