package estoreapi.persistence;

import java.io.IOException;
import estoreapi.model.Lesson;

public interface LessonDAO {
    
    /**
     * Retrieves all lessons from storage
     * @return An array of lesson objects
     * @throws IOException
     */
    Lesson[] getLessons() throws IOException;

    /**
     * Retrieves a specific lesson from storage
     * @param id The identifier for the method to search against
     * @return A single lesson object with the matching id, null if not found
     * @throws IOException
     */
    Lesson getLesson(int id) throws IOException;

    /**
     * Creates a lesson in storage
     * @param lesson The lesson to be put into storage
     * @return The created lesson
     * @throws IOException
     */
    Lesson createLesson(Lesson lesson) throws IOException;

    /**
     * Updates a lesson already in storage
     * @param lesson The lesson to be updated
     * @return The updated lesson, null if lesson not found
     * @throws IOException
     */
    Lesson updateLesson(Lesson lesson) throws IOException;

    /**
     * Deletes a lesson in storage
     * @param id The id of the lesson to be deleted
     * @return true if the lesson was successfully deleted from storage, false otherwise
     * @throws IOException
     */
    boolean deleteLesson(int id) throws IOException;

}
