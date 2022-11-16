package com.estore.api.estoreapi.persistence;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import estoreapi.model.Lesson;
import estoreapi.persistence.LessonFileDAO;

/**
 * The junit test suit for the LessonFileDAO class
 * 
 * @author Donovan Cataldo
 */
@Tag("Persistence-tier")
public class LessonFileDAOTest {
    LessonFileDAO lessonFileDAO;
    Lesson[] testLessons;
    ObjectMapper mockObjectMapper;

    @BeforeEach
    public void setupLessonFileDAO() throws IOException{
        mockObjectMapper = mock(ObjectMapper.class);
        testLessons = new Lesson[3];
        testLessons[0] = new Lesson(0, true, "STRINGS", "Amadeus", "MONDAY", 12, 2, 150.0, "Violin Masterclass");
        testLessons[1] = new Lesson(1, true, "STRINGS", "Amadeus", "TuESDAY", 12, 2, 100.0, "Clarinet Lesson");
        testLessons[2] = new Lesson(2, true, "STRINGS", "Jerry Garcia", "MONDAY", 12, 2, 100.0, "Violin Masterclass");
        when(mockObjectMapper.readValue(new File(""), Lesson[].class)).thenReturn(testLessons);
        lessonFileDAO = new LessonFileDAO("", mockObjectMapper);
    }

    @Test
    public void testGetLessons() throws IOException{
        // Invoke
        Lesson[] lessons = lessonFileDAO.getLessons();
        // Analyze
        assertEquals(lessons.length, testLessons.length);
        for(int i = 0; i < testLessons.length; i++){
            assertEquals(lessons[i], testLessons[i]);
        }
    }

    @Test
    public void testGetLesson() throws IOException {
        // Invoke
        Lesson lesson = lessonFileDAO.getLesson(0); 
        // Analyze
        assertEquals(lesson, testLessons[0]);

        lesson = lessonFileDAO.getLesson(50);

        assertNull(lesson);
    }

    @Test
    public void testCreateLesson() throws IOException {
        Lesson lesson =  new Lesson(3, false, "BRASS", "Wynton Marsalis", "FRIDAY", 12, -1, 100.0, "Trumpet Masterclass");

        Lesson result = lessonFileDAO.createLesson(lesson);

        assertEquals(lesson, result);
    }

    @Test
    public void testUpdateLesson() throws IOException {
        Lesson lesson =  new Lesson(0, true, "STRINGS", "Paul Mccartney", "MONDAY", 12, 2, 100.0, "Violin Masterclass");
        Lesson result = assertDoesNotThrow(() -> lessonFileDAO.updateLesson(lesson),
                "Unexpected exception thrown");

        assertNotNull(result);
        Lesson actual = lessonFileDAO.getLesson(lesson.getID());
        assertEquals(actual, lesson);
    }

    @Test
    public void testUpdateException() throws IOException {
        doThrow(new IOException()).when(mockObjectMapper)
                .writeValue(any(File.class), any(Lesson[].class));

        Lesson lesson = new Lesson(0, true, "STRINGS", "Amadeus", "MONDAY", 12, 2, 100.0, "Violin Masterclass");

        assertThrows(IOException.class,
                () -> lessonFileDAO.createLesson(lesson));
    }

    @Test
    public void testUpdateLessonNotFound() throws IOException {
        // Setup
        Lesson lesson = new Lesson(5, true, "STRINGS", "Amadeus", "MONDAY", 12, 2, 100.0, "Violin Masterclass");

        // Invoke
        Lesson result = lessonFileDAO.updateLesson(lesson);

        // Analyze
        assertNull(result);
    }

    @Test
    public void testDeleteLesson() throws IOException {
        //Test Success
        boolean result = lessonFileDAO.deleteLesson(0);

        assertTrue(result);

        //Test Failure
        result = lessonFileDAO.deleteLesson(0);

        assertFalse(result);
    }
}
