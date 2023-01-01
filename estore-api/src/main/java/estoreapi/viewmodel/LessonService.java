package estoreapi.viewmodel;

import org.springframework.stereotype.Component;

import estoreapi.model.Lesson;
import estoreapi.persistence.DAO;

import java.io.IOException;

/**
 * Component class to handle the business logic of the lesson resource
 * 
 * @author Damon Gonzalez
 */
@Component
public class LessonService {
    
    /**
     * Handles the business logic behind updating a lesson
     * 
     * -When a lesson's userID is changed it can't be updated from 1 valid userID
     * to another, it must be -1 between them.
     * @param lessonDAO The DAO for lessons
     * @param lesson The lesson to update
     * @return true if the operation was successful, false otherwise
     */
    protected boolean updateLesson(DAO<Lesson> lessonDAO, Lesson lesson) throws IOException{
        Lesson existing = lessonDAO.getItem(lesson.getID());
        if(existing.getUserID() != -1 && lesson.getUserID() != -1){
            return false;
        }
        lessonDAO.updateItem(lesson);
        return true;
    }
}
