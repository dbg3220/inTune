import estoreapi.model.Lesson;
import estoreapi.model.Product;
import estoreapi.model.User;
import estoreapi.persistence.LessonDAO;
import estoreapi.viewmodel.LessonController;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.beans.Transient;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test the product Controller class
 * 
 * @author Jonathan Zhu
 */

@Tag("Controller-tier")
public class LessonControllerTest {
    private LessonController lessonController;
    private LessonDAO mockLessonDAO;

    /**
     * Before each test, create a new HeroController object and inject
     * a mock Lesson DAO
     */

    @BeforeEach
    public void setupLessonController() {
        mockLessonDAO = mock(LessonDAO.class);
        lessonController = new LessonController(mockLessonDAO);
    }

    @Test
    public void testGetLesson() throws IOException { // getlesson may throw IOException
        // Setup
        User testuser = new User(5000, "Test1", "Test1", null, null, null, null, 0, 0, null, null, false);
        Lesson lesson = new Lesson(99, "Viola", 122.99, Product.Category.WOODWINDS, 1, false, false, true,
                "John Doe", testuser, Lesson.Day.Monday, "12:00", false);

        // When the same id is passed in, our mock Lesson DAO will return the Lesson object
        when(mockLessonDAO.getLesson(lesson.getId())).thenReturn(lesson);

        // Invoke
        ResponseEntity<Lesson> response = lessonController.getLesson(lesson.getId());

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(lesson, response.getBody());

    }

    @Test
    public void testGetLessonNotFound() throws Exception { // createLesson may throw IOException
        // Setup
        int lessonId = 99;

        // When the same id is passed in, our mock Lesson DAO will return null, simulating
        // no lesson found
        when(mockLessonDAO.getLesson(lessonId)).thenReturn(null);

        // Invoke
        ResponseEntity<Lesson> response = lessonController.getLesson(lessonId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetLessonHandleException() throws Exception { // createLesson may throw IOException
        // Setup
        int lessonId = 99;
        // When getLesson is called on the Mock Lesson DAO, throw an IOException
        doThrow(new IOException()).when(mockLessonDAO).getLesson(lessonId); 

        // Invoke
        ResponseEntity<Lesson> response = lessonController.getLesson(lessonId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testCreateHeroHandleException() throws IOException { // createLesson may throw IOException
        // Setup
        Lesson lesson = new Lesson(99, "Viola", 122.99, Product.Category.WOODWINDS, 1, false, false, true,
                "John Doe", "SWEN-261", "Monday", 12, false);

        // When createHLesson is called on the Mock Lesson DAO, throw an IOException
        doThrow(new IOException()).when(mockLessonDAO).createLesson(lesson);

        // Invoke
        ResponseEntity<Lesson> response = lessonController.createLesson(lesson);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode);
    }

    @Test
    public void testUpdateLesson() throws IOException { // updateLesson may throw IOException
        // Setup
        Lesson lesson = new Lesson(99, "Viola", 122.99, Product.Category.WOODWINDS, 1, false, false, true,
                "John Doe", "SWEN-261", "Monday", 12, false);
        // when updateLesson is called, return true simulating successful
        // update and save
        when(mockLessonDAO.updateLesson(lesson)).thenReturn(lesson);
        ResponseEntity<Lesson> response = lessonController.updateLesson(lesson);
        lesson.setName("Doe");

        // Invoke
        response = lessonController.updateLesson(lesson);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(lesson, response.getBody());
    }

    @Test
    public void testUpdatelessonFailed() throws IOException { // updateLesson may throw IOException
        // Setup
        Lesson lesson = new Lesson(99, "Viola", 122.99, Product.Category.WOODWINDS, 1, false, false, true,
                "John Doe", "SWEN-261", "Monday", 12, false);
        // when updateLesson is called, return true simulating successful
        // update and save
        when(mockLessonDAO.updateLesson(lesson)).thenReturn(null);

        // Invoke
        ResponseEntity<Lesson> response = lessonController.updateLesson(lesson);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateLessonHandleException() throws IOException { // updateLesson may throw IOException
        // Setup
        Lesson lesson = new Lesson(99, "Viola", 122.99, Product.Category.WOODWINDS, 1, false, false, true,
                "John Doe", "SWEN-261", "Monday", 12, false);
        // When updateLesson is called on the Mock Lesson DAO, throw an IOException
        doThrow(new IOException()).when(mockLessonDAO).updateLesson(lesson);

        // Invoke
        ResponseEntity<Lesson> response = lessonController.updateLesson(lesson);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetLessons() throws IOException { // getLessons may throw IOException
        // Setup
        Lesson[] lessons = new Lesson[2];
        lessons[0] = new Lesson(99, "Viola", 122.99, Product.Category.WOODWINDS, 1, false, false, true,
                "John Doe", "SWEN-261", "Monday", 12, false);
        lessons[1] = new Lesson(100, "Violin", 155.99, Product.Category.WOODWINDS, 1, false, false, true,
                "Jay up", "SWEN-250", "Tuesday", 2, false);
        // When getLessons is called return the lessons created above
        when(mockLessonDAO.getLessons()).thenReturn(lessons);

        // Invoke
        ResponseEntity<Lesson[]> response = lessonController.getLessons();

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(lessons, response.getBody());
    }


    
    @Test
    public void testGetLessonsHandleException() throws IOException { // getLessons may throw IOException
        // Setup
        // When getLessons is called on the Mock Lesson DAO, throw an IOException
        doThrow(new IOException()).when(mockLessonDAO).getLessons();

        // Invoke
        ResponseEntity<Lesson[]> response = lessonController.getLessons();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    
    @Test
    public void testSearchLessons() throws IOException { // findLessons may throw IOException
        // Setup
        String searchString = "Vi";
        Lesson[] lessons = new Lesson[2];
        lessons[0] = new Lesson(99, "Viola", 122.99, Product.Category.WOODWINDS, 1, false, false, true,
                "John Doe", "SWEN-261", "Monday", 12, false);
        lessons[1] = new Lesson(100, "Violin", 155.99, Product.Category.WOODWINDS, 1, false, false, true,
                "Jay up", "SWEN-250", "Tuesday", 2, false);
        // When findLessons is called with the search string, return the two
        /// lessons above
        when(mockLessonDAO.findLessons(searchString)).thenReturn(lessons);

        // Invoke
        ResponseEntity<Lesson[]> response = lessonController.searchLessons(searchString);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(lessons, response.getBody());
    }

    @Test
    public void testSearchLessonsHandleException() throws IOException { // findLessons may throw IOException
        // Setup
        String searchString = "Vio";
        // When createLesson is called on the Mock Lesson DAO, throw an IOException
        doThrow(new IOException()).when(mocklessonDAO).findLessons(searchString);

        // Invoke
        ResponseEntity<Lesson[]> response = lessonController.searchLessons(searchString);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testDeleteLesson() throws IOException { // deleteLesson may throw IOException
        // Setup
        int lessonId = 99;
        // when deleteLesson is called return true, simulating successful deletion
        when(mockLessonDAO.deleteLesson(lessonId)).thenReturn(true);

        // Invoke
        ResponseEntity<Lesson> response = lessonController.deleteLesson(lessonId);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteLessonNotFound() throws IOException { // deleteLesson may throw IOException
        // Setup
        int lessonId = 99;
        // when deleteLesson is called return false, simulating failed deletion
        when(mockLessonDAO.deleteLesson(lessonId)).thenReturn(false);

        // Invoke
        ResponseEntity<Lesson> response = lessonController.deleteLesson(lessonId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteLessonHandleException() throws IOException { // deleteLesson may throw IOException
        // Setup
        int lessonId = 99;
        // When deleteLesson is called on the Mock Lesson DAO, throw an IOException
        doThrow(new IOException()).when(mockLessonDAO).deleteLesson(lessonId);

        // Invoke
        ResponseEntity<Lesson> response = lessonController.deleteLesson(lessonId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

}


