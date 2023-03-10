package estoreapi.viewmodel;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import estoreapi.model.Lesson;
import estoreapi.persistence.DAO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Spring Controller to handle http requests for Lesson objects
 * 
 * @author Damon Gonzalez
 */
@RestController
@RequestMapping("/lessons")
public class LessonController {

    /** Logger object user for this controller */
    private static final Logger LOG = Logger.getLogger(LessonController.class.getName());
    /** DAO used to access lesson objects */
    private DAO<Lesson> lessonDAO;
    /** Service used by this controller to handle the business logic of the lesson resource */
    private LessonService eService;

    /**
     * Creates a REST API controller to respond to requests
     * 
     * @param lessonDAO The lesson data access object to perform CRUD operations
     * @param eService The service that handles the business logic of the lesson resource
     */
    public LessonController(DAO<Lesson> lessonDAO, LessonService eService){
        this.lessonDAO = lessonDAO;
        this.eService = eService;
    }

    /**
     * Handles GET request for all lessons
     * @return A response entity with a body of all the lessons in the inventory
     */
    @GetMapping
    public ResponseEntity<Lesson[]> getLessons(){
        LOG.info("GET /lessons");
        try {
            Lesson[] lessons = lessonDAO.getItems();
            return new ResponseEntity<>(lessons, HttpStatus.OK);
        } catch (IOException e){
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles GET request for a single lesson
     * @param id The id of the lesson
     * @return A response entity with the appropriate body and status
     */
    @GetMapping("/{id}")
    public ResponseEntity<Lesson> getLesson(@PathVariable int id){
        LOG.info("GET /lessons/" + id);
        try {
            Lesson lesson = lessonDAO.getItem(id);
            if(lesson == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(lesson, HttpStatus.OK);
        } catch (IOException e){
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles the POST request for a single lesson
     * @param lesson The lesson to be created
     * @return A response entity with the lesson as body and status of OK
     * if successful, status of BAD_REQUEST otherwise
     */
    @PostMapping
    public ResponseEntity<Lesson> createLesson(@RequestBody Lesson lesson){
        LOG.info("POST /lessons " + lesson);
        try {
            Lesson result = lessonDAO.createItem(lesson);
            if(result == null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (IOException e){
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles the PUT request for a single lesson
     * @param lesson The lesson to be updated, containing its unique identifier
     * @return A response entity with a body of the lesson and a status of OK
     * if successful, gives status of NOT_FOUND otherwise
     */
    @PutMapping
    public ResponseEntity<Lesson> updateLesson(@RequestBody Lesson lesson){
        LOG.info("PUT /lessons " + lesson);
        try {
            return new ResponseEntity<>(eService.updateLesson(lessonDAO, lesson));
        } catch (IOException e){
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles DELETE request for a single lesson
     * @param id The id of the lesson
     * @return A response entity with code OK if succesful, NOT_FOUND if 
     * unsuccessful
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Lesson> deleteLesson(@PathVariable int id ){
        LOG.info("DELETE /lessons/" + id);
        try {
            if(!lessonDAO.deleteItem(id)){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e){
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
