package estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a review of a product
 */
public class Review {
    /** The id of the user who left the review */
    @JsonProperty("userID")
    private int userID;
    /** The rating of the product, 1-5 */
    @JsonProperty("rating")
    private int rating;
    /** The text description of the product, from the user */
    @JsonProperty("description")
    private String description;

    /**
     * Creates a Review
     * 
     * @param userID
     * @param rating
     * @param description
     */
    public Review(@JsonProperty("userID") int userID, @JsonProperty("rating") int rating,
                        @JsonProperty("description") String description){
        this.userID = userID;
        this.rating = rating;
        this.description = description;
    }

    public int getUserID(){
        return userID;
    }

    public int getRating(){
        return rating;
    }

    public String getDescription(){
        return description;
    }
}
