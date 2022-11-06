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
     * Retrieves all {@linkplain lesson}
     * 
     * @return An array of {@link lesson} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Lesson[] getLessons() throws IOException;

    /**
     * Retrieves a {@linkplain lesson} with the given id
     * 
     * @param id The id of the {@link lesson} to get
     * 
     * @return a {@link lesson} object with the matching id
     * 
     * null if no {@link lesson} with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Lesson getLesson(int id) throws IOException;

    /**
     * Creates and saves a {@linkplain lesson lesson}
     * 
     * @param Lesson {@linkplain lesson lesson} object to be created and saved
     * <br>
     * The id of the lesson object is ignored and a new uniqe id is assigned
     *
     * @return new {@link Lesson lesson} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    Lesson createLesson(Lesson lesson) throws IOException;

     /**
     * Deletes a {@linkplain lesson} with the given id
     * 
     * @param id The id of the {@link lesson}
     * 
     * @return true if the {@link lesson} was deleted
     *
     * false if lesson with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteLesson(int id) throws IOException;

}
