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

@RestController
@RequestMapping("lessons")
public class LessonController {
    private static final Logger LOG = Logger.getLogger(LessonController.class.getName());
    private LessonDAO lessonDao;

    public LessonController(LessonDAO lessonDao){
        this.lessonDao = lessonDao;
    }

    /**
     * Handles the GET request for a single lesson
     * @param id The id of the lesson to search for
     * @return A response entity with the body of the lesson and a status code of OK if found,
     * sends an error code otherwise
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
     * Handles the GET request for the entire lesson resource
     * @return A response entity with all of the lessons in the inventory
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
     * Handles the POST request for the lesson resource
     * @param lesson The lesson to create, ignoring the fields id, isFull, userID
     * @return A response entity with a status of CREATED, sends an error code otherwise
     */
    @PostMapping("")
    public ResponseEntity<Lesson> createLesson(@RequestBody Lesson lesson) {
        LOG.info("POST /lessons " + lesson);
        try {
            Lesson result = lessonDao.createLesson(lesson);
            if (result != null)
                return new ResponseEntity<Lesson>(result,HttpStatus.CREATED);
            else
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles the PUT request for the lesson resource
     * @param lesson The lesson to be updated
     * @return A response entity with the updated lesson and a status of OK if successful,
     * otherwise sends an error code;
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
     * Handles the DELETE request for the lesson resource
     * @param id The id of the lesson to delete
     * @return A response entity with status code OK if successfully deleted,
     * sends an error code otherwise
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
