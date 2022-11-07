package estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a review of a product
 */
public class Review {
    /** The username of the user who left the review */
    @JsonProperty("reviewUsername")
    private String username;
    /** The rating of the product, 1-5 */
    @JsonProperty("rating")
    private int rating;
    /** The text description of the product, from the user */
    @JsonProperty("description")
    private String description;

    /**
     * Creates a Review
     * 
     * @param reviewUsername
     * @param rating
     * @param description
     */
    public Review(@JsonProperty("reviewUsername") String username, @JsonProperty("rating") int rating,
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
}
