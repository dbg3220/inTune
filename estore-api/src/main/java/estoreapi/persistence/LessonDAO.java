package estoreapi.persistence;

import java.io.IOException;
import estoreapi.model.Lesson;

/**
 * Defines the operations for lesson object persistence
 * 
 * @author Damon Gonzalez
 */
public interface LessonDAO {
    /**
     * Retrieves all lessons
     * @return An array of lessons, may be empty
     * @throws IOException if an issue with underlying storage
     */
    Lesson[] getLessons() throws IOException;

    /**
     * Retrieves a lesson with the given id
     * @param id The id of the lesson to get
     * @return The lesson with the matching id, null if no lesson found
     * @throws IOException if an issue with underlying storage
     */
    Lesson getLesson(int id) throws IOException;

    /**
     * Creates and saves a lesson
     * @param lesson The lesson to create
     * @return The newly created lesson if successful, null otherwise 
     * @throws IOException if an issue with underlying storage
     */
    Lesson createLesson(Lesson lesson) throws IOException;

    /**
     * Updates and saves a lesson
     * @param lesson The lesson to be updated and saved
     * @return The updated lesson if successful, null if
     * lesson could not be found
     * @throws IOException if underlying storage cannot be accessed
     */
    Lesson updateLesson(Lesson lesson) throws IOException;

    /**
     * Deletes a lesson with the given id
     * @param id The id of the lesson
     * @return true if the lesson was deleted
     * false if lesson with the given id does not exist
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteLesson(int id) throws IOException;
}
