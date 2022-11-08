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
     * Public constructor for REST to instantiate this object at initialization
     * @param lessonDao A data access object to be injected into this controller
     */
    public LessonController(LessonDAO lessonDao){
        this.lessonDao = lessonDao;
    }

    /**
     * Handles the REST GET request for a single lesson
     * @param id The id of the lesson to get
     * @return A response entity with the lesson if found and a status of OK. If no lesson
     * with the matching id is found than a response entity with the status NOT_FOUND is sent.
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
     * Handles the REST GET request for all lessons
     * @return A response entity containing an array of lessons which may be empty
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
     * Handles the REST POST request for the lesson resource
     * @param lesson The lesson to be created
     * @return A response entity with the newly created lesson
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
     * Handles the REST PUT request for the lesson resource
     * @param lesson The lesson to be updated
     * @return A response entity with the newly updated lesson. If no lesson
     * with the matching id is found than a status of NOT_FOUND is sent.
     */
    @PutMapping("")
    public ResponseEntity<Lesson> updateLesson(@RequestBody Lesson lesson) {
        LOG.info("PUT /lessons " + lesson);
        try {
            Lesson newLesson = lessonDao.updateLesson(lesson);
            if (newLesson != null){
                return new ResponseEntity<Lesson>(newLesson,HttpStatus.OK);
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

    /**
     * Handles the REST DELETE request for the lesson resource
     * @param id The id of the lesson to be deleted
     * @return A status of OK if the lesson was successfully deleted. If the
     * lesson could not be found than a status of NOT_FOUND is sent.
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
