package com.estore.api.estoreapi.viewmodel;
import estoreapi.viewmodel.LessonController;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import estoreapi.model.Lesson;
import estoreapi.persistence.LessonDAO;

/**
 * The unit test for the product controller
 * 
 * @author Donovan Cataldo
 */
@Tag("Controller-tier")
public class LessonControllerTest {
    LessonDAO mockDAO;
    LessonController lessonController;

    @BeforeEach
    public void setUpLessonController(){
        mockDAO = mock(LessonDAO.class);
        lessonController = new LessonController(mockDAO);
    }

    //Lesson test = new Lesson(0, true, "STRINGS", "Amadeus", "MONDAY", 12, 2, 100.0, "Violin Masterclass");

    @Test
    public void testGetLesson() throws Exception{
        // Setup
        Lesson lesson = new Lesson(0, true, "STRINGS", "Amadeus", "MONDAY", 12, 2, 100.0, "Violin Masterclass");
        when(mockDAO.getLesson(lesson.getID())).thenReturn(lesson);
        
        // Invoke
        ResponseEntity<Lesson> response = lessonController.getLesson(lesson.getID());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(lesson,response.getBody());
    }

    @Test
    public void testGetLessonNotFound() throws Exception{
        // Setup
        int lessonID = 100000;
        when(mockDAO.getLesson(lessonID)).thenReturn(null);

        // Invoke
        ResponseEntity<Lesson> response = lessonController.getLesson(lessonID);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetLessonHandleException() throws Exception{
        // Setup
        int lessonID = 100000;
        doThrow(new IOException()).when(mockDAO).getLesson(lessonID);

        // Invoke
        ResponseEntity<Lesson> response = lessonController.getLesson(lessonID);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetLessons() throws Exception{
        // Setup
        Lesson[] testLessons = new Lesson[3];
        testLessons[0] = new Lesson(0, true, "STRINGS", "Amadeus", "MONDAY", 12, 2, 100.0, "Violin Masterclass");
        testLessons[1] = new Lesson(0, true, "WOODWINDS", "Bach", "TUESDAY", 12, 2, 100.0, "Violin Masterclass");
        testLessons[2] = new Lesson(0, true, "STRINGS", "Amadeus", "MONDAY", 12, 2, 100.0, "Violin Masterclass");
        when(mockDAO.getLessons()).thenReturn(testLessons);

        // Invoke
        ResponseEntity<Lesson[]> response = lessonController.getLessons();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(testLessons,response.getBody());
    } 

    @Test
    public void testGetLessonsHandleException() throws Exception{
        doThrow(new IOException()).when(mockDAO).getLessons();

        ResponseEntity<Lesson[]> response = lessonController.getLessons();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testCreateLesson() throws Exception{
        // Setup
        Lesson testLesson = new Lesson(-1, false, "STRINGS", "Amadeus", "MONDAY", 12, -1, 100.0, "Violin Masterclass");
        when(mockDAO.createLesson(testLesson)).thenReturn(testLesson);
        // Invoke
        ResponseEntity<Lesson> response = lessonController.createLesson(testLesson);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(testLesson,response.getBody());
    }

    @Test
    public void testCreateLessonFailure() throws IOException {
        Lesson testLesson = new Lesson(0, true, "STRINGS", "Amadeus", "MONDAY", 12, 2, 100.0, "Violin Masterclass");
        when(mockDAO.createLesson(testLesson)).thenReturn(null);
        // Invoke
        ResponseEntity<Lesson> response = lessonController.createLesson(testLesson);

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    public void testCreateLessonHandleException() throws Exception{
        // Setup
        Lesson testLesson = new Lesson(0, true, "STRINGS", "Amadeus", "MONDAY", 12, 2, 100.0, "Violin Masterclass");
        doThrow(new IOException()).when(mockDAO).createLesson(testLesson);

        // Invoke
        ResponseEntity<Lesson> response = lessonController.createLesson(testLesson);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testUpdateLesson() throws IOException{
        // Setup
        Lesson testLesson = new Lesson(0, true, "STRINGS", "Amadeus", "MONDAY", 12, 2, 100.0, "Violin Masterclass");
        when(mockDAO.updateLesson(testLesson)).thenReturn(testLesson);

        // Invoke
       ResponseEntity<Lesson> result = lessonController.updateLesson(testLesson);

        // Analyze
        assertEquals(HttpStatus.OK,result.getStatusCode());
        assertEquals(testLesson,result.getBody());
    }

    @Test
    public void testUpdateLessonFailed() throws IOException{
        // Setup
        Lesson testLesson = new Lesson(0, true, "STRINGS", "Amadeus", "MONDAY", 12, 2, 100.0, "Violin Masterclass");
        when(mockDAO.updateLesson(testLesson)).thenReturn(null);

        // Invoke
        ResponseEntity<Lesson> response = lessonController.updateLesson(testLesson);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testUpdateLessonHandleException() throws IOException{
        // Setup
        Lesson testLesson = new Lesson(0, true, "STRINGS", "Amadeus", "MONDAY", 12, 2, 100.0, "Violin Masterclass");
        doThrow(new IOException()).when(mockDAO).updateLesson(testLesson);

        // Invoke
        ResponseEntity<Lesson> response = lessonController.updateLesson(testLesson);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testDeleteProduct() throws IOException{
        // Setup
        int lessonID = 1;
        when(mockDAO.deleteLesson(lessonID)).thenReturn(true);

        // Invoke
        ResponseEntity<Lesson> response = lessonController.deleteLesson(lessonID);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteProductNotFound() throws IOException{
        // Setup
        int lessonID = 1;
        when(mockDAO.deleteLesson(lessonID)).thenReturn(false);

        // Invoke
        ResponseEntity<Lesson> response = lessonController.deleteLesson(lessonID);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());

    }

    @Test
    public void testDeleteProductHandleException() throws IOException{
        // Setup
        int lessonID = 1;
        doThrow(new IOException()).when(mockDAO).deleteLesson(lessonID);

        // Invoke
        ResponseEntity<Lesson> response = lessonController.deleteLesson(lessonID);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
