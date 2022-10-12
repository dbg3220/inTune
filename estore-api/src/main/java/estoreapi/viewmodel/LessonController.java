package estoreapi.viewmodel;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import estoreapi.persistence.LessonDAO;
import estoreapi.model.Lesson;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles the REST API requests for the product resource
 * RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author Donovan Cataldo
 */
@RestController

@RequestMapping("lesson")
public class LessonController {
    private LessonDAO lessonDAO; // DAO instance that gets used
    private static final Logger LOG = Logger.getLogger(LessonController.class.getName());
/**
     * Creates a REST API controller to respond to requests
     * @param LessonDao The {@link LessonDAO Lesson Data Access Object} toperform CRUD operations
     * This dependency is injected by the Spring Framework
     */
    public LessonController(LessonDAO lessonDAO){
        this.lessonDAO = lessonDAO; 
    }

    /**
    * Handles the HTTP GET request for the lesson resource
    * @param id The id of the lesson to retrieve
    * @return The lesson with the specified id
    */
    @GetMapping("/{id}")
    public ResponseEntity<Lesson> getLesson(@PathVariable int id) {
        LOG.info("GET /Products/" + id);   
        try {
            Lesson lesson = lessonDAO.getLesson(id);
            if (lesson != null)
                return new ResponseEntity<>(lesson, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles the HTTP GET request for the lesson resource
     * @return All lessons
     */
    @GetMapping("")
    public ResponseEntity<Lesson[]> getLessons() {
    LOG.info("GET /products");
    try {
        return new ResponseEntity<>(lessonDAO.getLessons(), HttpStatus.OK);
    } catch (IOException e) {
        LOG.log(Level.SEVERE, "Error getting products", e);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }

    /**
     * Handles the HTTP PUT request to update an existing lesson
     * @param lesson The lesson to update
     * @return The HTTP response
     */
    @PutMapping("")
    public ResponseEntity<Lesson> updateLesson(@RequestBody Lesson lesson) {
        LOG.info("PUT /products " + lesson);
        try {
            if(lesson.getQuantity() < 0){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            Lesson lessonTwo = lessonDAO.updateLesson(lesson);
            if (lessonTwo != null)
                return new ResponseEntity<Lesson>(lessonTwo,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

