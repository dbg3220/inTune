package model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents an Instrument
 * 
 * @author Hayden Cieniawski
 */
public class Instrument {
    private static final Logger LOG = Logger.getLogger(Instrument.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "Instrument [id=%d, name=%s, price=%f, category=%s]";

    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("price") private double price;
    @JsonProperty("category") private String category;

    /**
     * Create an instrument with the given id, name, and price.
     * @param id The id of the instrument
     * @param name The name of the instrument
     * @param price The price of the instrument
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Instrument(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("price") double price, @JsonProperty("category") String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    /**
     * Retrieves the id of the instrument
     * @return The id of the instrument
     */
    public int getId() {return id;}

    /**
     * Sets the name of the instrument - necessary for JSON object to Java object deserialization
     * @param name The name of the instrument
     */
    public void setName(String name) {this.name = name;}

    /**
     * Retrieves the name of the instrument
     * @return The name of the instrument
     */
    public String getName() {return name;}

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id,name);
    }
}