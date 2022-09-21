package model.persistence;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import model.Instrument;

/**
 * Implements the functionality for JSON file-based peristance for Instruments
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 * 
 * @author Hayden Cieniawski
 */
@Component
public class InstrumentFileDAO implements InstrumentDAO {
    private static final Logger LOG = Logger.getLogger(InstrumentFileDAO.class.getName());
    Map<Integer,Instrument> instruments;   // Provides a local cache of the Instrument objects
                                           // so that we don't need to read from the file
                                           // each time
    private ObjectMapper objectMapper;  // Provides conversion between Instrument
                                        // objects and JSON text format written
                                        // to the file
    private static int nextId;  // The next Id to assign to a new Instrument
    private String filename;    // Filename to read from and write to

    /**
     * Creates a Instrument File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public InstrumentFileDAO(@Value("${instruments.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the Instruments from the file
    }

    /**
     * Generates the next id for a new {@linkplain Instrument Instrument}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of {@linkplain Instrument Instruments} from the tree map
     * 
     * @return  The array of {@link Instrument Instruments}, may be empty
     */
    private Instrument[] getInstrumentsArray() {
        return getInstrumentsArray(null);
    }

    /**
     * Generates an array of {@linkplain Instrument Instruments} from the tree map for any
     * {@linkplain Instrument Instruments} that contains the text specified by containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain Instrument Instruments}
     * in the tree map
     * 
     * @return  The array of {@link Instrument Instruments}, may be empty
     */
    private Instrument[] getInstrumentsArray(String containsText) { // if containsText == null, no filter
        ArrayList<Instrument> instrumentArrayList = new ArrayList<>();

        for (Instrument Instrument : instruments.values()) {
            if (containsText == null || Instrument.getName().contains(containsText)) {
                instrumentArrayList.add(Instrument);
            }
        }

        Instrument[] instrumentArray = new Instrument[instrumentArrayList.size()];
        instrumentArrayList.toArray(instrumentArray);
        return instrumentArray;
    }

    /**
     * Saves the {@linkplain Instrument Instruments} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Instrument Instruments} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Instrument[] instrumentArray = getInstrumentsArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),instrumentArray);
        return true;
    }

    /**
     * Loads {@linkplain Instrument Instruments} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        instruments = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of Instruments
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Instrument[] instrumentArray = objectMapper.readValue(new File(filename),Instrument[].class);

        // Add each Instrument to the tree map and keep track of the greatest id
        for (Instrument instrument : instrumentArray) {
            instruments.put(instrument.getId(),instrument);
            if (instrument.getId() > nextId)
                nextId = instrument.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Instrument[] getInstruments() {
        synchronized(instruments) {
            return getInstrumentsArray();
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Instrument[] findInstruments(String containsText) {
        synchronized(instruments) {
            return getInstrumentsArray(containsText);
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Instrument getInstrument(int id) {
        synchronized(instruments) {
            if (instruments.containsKey(id))
                return instruments.get(id);
            else
                return null;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Instrument createInstrument(Instrument Instrument) throws IOException {
        synchronized(instruments) {
            // We create a new Instrument object because the id field is immutable
            // and we need to assign the next unique id
            Instrument newInstrument = new Instrument(nextId(),Instrument.getName(), Instrument.getPrice(), Instrument.getCategory(), Instrument.getQuantity());
            instruments.put(newInstrument.getId(),newInstrument);
            save(); // may throw an IOException
            return newInstrument;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Instrument updateInstrument(Instrument Instrument) throws IOException {
        synchronized(instruments) {
            if (instruments.containsKey(Instrument.getId()) == false)
                return null;  // Instrument does not exist

            instruments.put(Instrument.getId(),Instrument);
            save(); // may throw an IOException
            return Instrument;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteInstrument(int id) throws IOException {
        synchronized(instruments) {
            if (instruments.containsKey(id)) {
                instruments.remove(id);
                return save();
            }
            else
                return false;
        }
    }

    @Override
    public Instrument findInstrumentCategory(String category) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }
}