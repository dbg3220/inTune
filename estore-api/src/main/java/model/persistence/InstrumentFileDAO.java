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
    Map<Integer,Instrument> Instruments;   // Provides a local cache of the Instrument objects
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
    public InstrumentFileDAO(@Value("${Instruments.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the Instrumentes from the file
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
     * Generates an array of {@linkplain Instrument Instrumentes} from the tree map
     * 
     * @return  The array of {@link Instrument Instrumentes}, may be empty
     */
    private Instrument[] getInstrumentesArray() {
        return getInstrumentesArray(null);
    }

    /**
     * Generates an array of {@linkplain Instrument Instrumentes} from the tree map for any
     * {@linkplain Instrument Instrumentes} that contains the text specified by containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain Instrument Instrumentes}
     * in the tree map
     * 
     * @return  The array of {@link Instrument Instrumentes}, may be empty
     */
    private Instrument[] getInstrumentesArray(String containsText) { // if containsText == null, no filter
        ArrayList<Instrument> InstrumentArrayList = new ArrayList<>();

        for (Instrument Instrument : Instruments.values()) {
            if (containsText == null || Instrument.getName().contains(containsText)) {
                InstrumentArrayList.add(Instrument);
            }
        }

        Instrument[] InstrumentArray = new Instrument[InstrumentArrayList.size()];
        InstrumentArrayList.toArray(InstrumentArray);
        return InstrumentArray;
    }

    /**
     * Saves the {@linkplain Instrument Instrumentes} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Instrument Instrumentes} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Instrument[] InstrumentArray = getInstrumentesArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),InstrumentArray);
        return true;
    }

    /**
     * Loads {@linkplain Instrument Instrumentes} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        Instruments = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of Instrumentes
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Instrument[] InstrumentArray = objectMapper.readValue(new File(filename),Instrument[].class);

        // Add each Instrument to the tree map and keep track of the greatest id
        for (Instrument Instrument : InstrumentArray) {
            Instruments.put(Instrument.getId(),Instrument);
            if (Instrument.getId() > nextId)
                nextId = Instrument.getId();
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
        synchronized(Instruments) {
            return getInstrumentesArray();
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Instrument[] findInstruments(String containsText) {
        synchronized(Instruments) {
            return getInstrumentesArray(containsText);
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Instrument getInstrument(int id) {
        synchronized(Instruments) {
            if (Instruments.containsKey(id))
                return Instruments.get(id);
            else
                return null;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Instrument createInstrument(Instrument Instrument) throws IOException {
        synchronized(Instruments) {
            // We create a new Instrument object because the id field is immutable
            // and we need to assign the next unique id
            Instrument newInstrument = new Instrument(nextId(),Instrument.getName(), Instrument.getPrice(), Instrument.getCategory(), Instrument.getQuantity());
            Instruments.put(newInstrument.getId(),newInstrument);
            save(); // may throw an IOException
            return newInstrument;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Instrument updateInstrument(Instrument Instrument) throws IOException {
        synchronized(Instruments) {
            if (Instruments.containsKey(Instrument.getId()) == false)
                return null;  // Instrument does not exist

            Instruments.put(Instrument.getId(),Instrument);
            save(); // may throw an IOException
            return Instrument;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteInstrument(int id) throws IOException {
        synchronized(Instruments) {
            if (Instruments.containsKey(id)) {
                Instruments.remove(id);
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