package estoreapi.viewmodel;

import org.springframework.stereotype.Component;

import estoreapi.model.Lesson;
import estoreapi.persistence.DAO;

/**
 * Component class to handle the business logic of the lesson resource
 * 
 * @author Damon Gonzalez
 */
@Component
public class LessonService {
    
    /**
     * Handles the business logic behind updating a lesson
     * @param lessonDAO The DAO for lessons
     * @param lesson The lesson to update
     * @return true if the operation was successful, false otherwise
     */
    protected boolean updateLesson(DAO<Lesson> lessonDAO, Lesson lesson){
        //TODO
        return false;
    }
}
