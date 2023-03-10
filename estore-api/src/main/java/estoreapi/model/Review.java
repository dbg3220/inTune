package estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a review of a product
 */
public class Review {

    /** Format string for a Review */
    static final String STRING_FORMAT = "[username=%s, rating=%d, description=%s]";

    /** The username of the user who left the review */
    @JsonProperty("username")
    private String username;
    /** The rating of the product, an integer from 1-5 */
    @JsonProperty("rating")
    private int rating;
    /** The description of the product given by the user */
    @JsonProperty("description")
    private String description;

    /**
     * Public constructor for the Review class
     * @param username The username of the user who left this review
     * @param rating The rating from 1-5 left by the user
     * @param description The description left by the user
     */
    public Review(@JsonProperty("username") String username,
                  @JsonProperty("rating") int rating,
                  @JsonProperty("description") String description){
        this.username = username;
        this.rating = rating;
        this.description = description;
    }

    /**
     * Retrieves the id of the user who left the review
     * @return The username
     */
    public String getUsername(){
        return this.username;
    }

    /**
     * Retrieves the rating the user left on the product
     * @return The rating
     */
    public int getRating(){
        return rating;
    }

    /**
     * Retrieves the description the user left of the product
     * @return The description
     */
    public String getDescription(){
        return description;
    }
    
    @Override
    public String toString(){
        return String.format(STRING_FORMAT, username, rating, description);
    }
}
