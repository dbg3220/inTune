package model.persistence;

import java.io.IOException;
import model.Instrument;

/**
 * Defines the interface for instrument object persistence
 * 
 * @author Hayden Cieniawski
 * 
 */
public interface InstrumentDAO {
    /**
     * Retrieves all {@linkplain instruments}
     * 
     * @return An array of {@link instrument} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Instrument[] getInstruments() throws IOException;

    /**
     * Finds all {@linkplain instruments} whose name contains the given text
     * 
     * @param containsText The text to match against
     * 
     * @return An array of {@link instruments} whose names contains the given text, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Instrument[] findInstruments(String containsText) throws IOException;

    /**
     * Retrieves a {@linkplain instrument} with the given id
     * 
     * @param id The id of the {@link instrument} to get
     * 
     * @return a {@link instrument} object with the matching id
     * <br>
     * null if no {@link instrument} with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Instrument getInstrument(int id) throws IOException;

    
    /**
     * Retrieves a {@linkplain instrument} with the given id
     * 
     * @param id The id of the {@link instrument} to get
     * 
     * @return a {@link instrument} object with the matching id
     * <br>
     * null if no {@link instrument} with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Instrument findInstrumentCategory(String category) throws IOException;

    /**
     * Creates and saves a {@linkplain instrument}
     * 
     * @param instrument {@linkplain instrument} object to be created and saved
     * <br>
     * The id of the instrument object is ignored and a new uniqe id is assigned
     *
     * @return new {@link instrument} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    Instrument createInstrument(Instrument instrument) throws IOException;

    /**
     * Updates and saves a {@linkplain instrument}
     * 
     * @param {@link instrument} object to be updated and saved
     * 
     * @return updated {@link instrument} if successful, null if
     * {@link instrument} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Instrument updateInstrument(Instrument instrument) throws IOException;

    /**
     * Deletes a {@linkplain instrument} with the given id
     * 
     * @param id The id of the {@link instrument}
     * 
     * @return true if the {@link instrument} was deleted
     * <br>
     * false if instrument with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteInstrument(int id) throws IOException;
}
