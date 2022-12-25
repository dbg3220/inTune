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
 * Spring Controller to handle http requests for Lesson objects
 * 
 * @author Damon Gonzalez
 */
@RestController
@RequestMapping("lessons")
public class LessonController {
    private static final Logger LOG = Logger.getLogger(LessonController.class.getName());
    private LessonDAO lessonDao;

    /**
     * Creates a REST API controller to respond to requests
     * 
     * @param lessonDAO The lesson data access object to perform CRUD operations
     */
    public LessonController(LessonDAO lessonDao){
        this.lessonDao = lessonDao;
    }
}
