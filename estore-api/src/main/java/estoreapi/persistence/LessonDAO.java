package estoreapi.persistence;

import java.io.IOException;
import estoreapi.model.Lesson;
/**
 * Defines the interface for lesson object persistance
 * 
 * @author Donovan Cataldo
 */
public interface LessonDAO {
    /**
     * Retrieves all lessons
     * @return An array of lesson objects, may be empty
     * @throws IOException if an issue with underlying storage
     */
    Lesson[] getLessons() throws IOException;
    /**
     * Retreives a lesson with the given id
     * @param id The id of the lesson to get
     * @return A Lesson Object with the matching id, null if not found
     * @throws IOException if an issue with underlying storage
     */
    Lesson getLesson(int id) throws IOException;
    /**
     * Creates and saves a lesson
     * @param lesson The lesson to be created
     * @return A reference to the new lesson, null otherwise
     * @throws IOException if an issue with underlying storage
     */
    Lesson createLesson(Lesson lesson) throws IOException;
    /**
     * Updates a lesson
     * @param lesson The lesson to be updated
     * @return A reference to the newly updated lesson, null if no lesson found
     * @throws IOException if an issue with underlying storage
     */
    Lesson updateLesson(Lesson lesson) throws IOException;
    /**
     * Deletes a lesson
     * @param id The id of the lesson to delete
     * @return true if the lesson was successfully found and deleted, false if
     * no lesson with the matching id could be found
     * @throws IOException if an issue with underlying storage
     */
    boolean deleteLesson(int id) throws IOException;
}
