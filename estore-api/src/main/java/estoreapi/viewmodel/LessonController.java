package estoreapi.viewmodel;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import estoreapi.model.Lesson;
import estoreapi.persistence.LessonDAO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles the REST API requests for lesson resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST
 * API
 * method handler to the Spring framework
 * 
 * @author Donovan Cataldo
 * 
 */
@RestController
@RequestMapping("lessons")
public class LessonController {
    private static final Logger LOG = Logger.getLogger(LessonController.class.getName());
    private LessonDAO lessonDao;

    /**
     * Creates a REST API controller to respond to requests
     * 
     * @param productDao The {@link LessonDAO Lesson Data Access Object} to
     *                   perform CRUD operations
     *     
     * These dependencies are injected by the spring framework
     */
    public LessonController(LessonDAO lessonDao){
        this.lessonDao = lessonDao;
    }

    /**
    * Handles the HTTP GET request for the lesson resource
    * @param id The id of the lesson to retrieve
    * @return The lesson with the specified id
    */
    @GetMapping("/{id}")
    public ResponseEntity<Lesson> getLesson(@PathVariable int id) {
        LOG.info("GET /Lessons/" + id);   
        try {
            Lesson lesson = lessonDao.getLesson(id);
            if (lesson != null)
                return new ResponseEntity<>(lesson, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
        LOG.info("GET /lessons");
        try {
            Lesson[] lessons = lessonDao.getLessons();
            return new ResponseEntity<>(lessons, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles the HTTP POST request for the lesson resource
     * @param lesson The lesson to create
     * @return The lesson that was created including a unique identifier id that
     *  was assigned by the server
     */
    @PostMapping("")
    public ResponseEntity<Lesson> createLesson(@RequestBody Lesson lesson) {
        LOG.info("POST /lessons " + lesson);
        try {
            Lesson newLesson = lessonDao.createLesson(lesson);
            if (newLesson != null)
                return new ResponseEntity<Lesson>(newLesson,HttpStatus.CREATED);
            else
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
    * Handles the HTTP DELETE request to delete an existing lesson
    * @param id The id of the lesson to delete
    * @return The HTTP response
    */
    @DeleteMapping("/{id}")
    public ResponseEntity<Lesson> deleteLesson(@PathVariable int id) {
        LOG.info("DELETE /lessons/" + id);
        try {
            boolean result = lessonDao.deleteLesson(id);
            if (result){
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
