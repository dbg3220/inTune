package viewmodel;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.persistence.InstrumentDAO;
import model.Instrument;

/**
 * Handles the REST API requests for the Instrument resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author Hayden Cieniawski
 */

@RestController
@RequestMapping("instruments")
public class InstrumentController {
    private static final Logger LOG = Logger.getLogger(InstrumentController.class.getName());
    private InstrumentDAO InstrumentDao;

    /**
     * Creates a REST API controller to respond to requests
     * 
     * @param InstrumentDao The {@link InstrumentDAO Instrument Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public InstrumentController(InstrumentDAO InstrumentDao) {
        this.InstrumentDao = InstrumentDao;
    }

   
    // @GetMapping("/{id}")
    // public ResponseEntity<Instrument> getInstrument(@PathVariable int id) {
    //     LOG.info("GET /Instruments/" + id);        
    // }

   
    @GetMapping("")
    public ResponseEntity<Instrument[]> getInstruments() {
        LOG.info("GET /instruments");
        try {
            Instrument[] instruments = InstrumentDao.getInstruments();
            if (instruments != null)
                return new ResponseEntity<Instrument[]>(instruments,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

       
    }


    // @GetMapping("/")
    // public ResponseEntity<Instrument[]> searchInstrumentes(@RequestParam String name) {
    //     LOG.info("GET /Instrumentes/?name="+name);
       
    // }

   
    @PostMapping("")
    public ResponseEntity<Instrument> createInstrument(@RequestBody Instrument Instrument) {
        LOG.info("POST /Instruments " + Instrument);
        try {
            Instrument newInstrument = InstrumentDao.createInstrument(Instrument);
            if (newInstrument != null)
                return new ResponseEntity<Instrument>(newInstrument,HttpStatus.CREATED);
            else
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    // @PutMapping("")
    // public ResponseEntity<Instrument> updateInstrument(@RequestBody Instrument Instrument) {
    //     LOG.info("PUT /Instrumentes " + Instrument);
        git 
    // }

   
    // @DeleteMapping("/{id}")
    // public ResponseEntity<Instrument> deleteInstrument(@PathVariable int id) {
    //     LOG.info("DELETE /Instrumentes/" + id);
    // }
}

     