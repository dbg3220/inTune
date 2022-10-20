package estoreapi.persistence;

import java.io.IOException;

import estoreapi.model.Lesson;

/**
 * Defines the interface for lesson object persistence
 * 
 * @author Donovan Cataldo
 * 
 */
public interface LessonDAO {

/**
 * Updates and saves a lesson
 * 
 * @param lesson the lesson to be updated and saved
 * 
 * @return updated lesson if successful
 * null if unsuccessful
 * 
 * @throws IOException if an issue with underlying storage
 */
Lesson updateLesson(Lesson lesson) throws IOException;

/**
* Retrieves all lessons in the lesson JSON as an array of lessons
* 
* @return An array of lesson objects
* 
* @throws IOException if an issue with underlying storage
*/
Lesson[] getLessons() throws IOException;

/**
 * Retrieves a lesson with the given id
 * 
 * @param id The if of the lesson we want to retrieve
 * 
 * @return The lesson object with the watching id
 * null if the lesson is not found
 * 
 * @throws IOException if an issue with underlying storage
 */
Lesson getLesson(int id) throws IOException;

}
