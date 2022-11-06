package com.estore.api.estoreapi.persistence;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
            testLessons[0] = new Lesson(1, false, "", "", "MONDAY", 0, 0, 0.0,"");
            testLessons[1] = new Lesson(2, false, "", "", "TUESDAY", 0, 0, 0.0, "");
            testLessons[2] = new Lesson(3, false, "", "", "THURSDAY", 0, 0, 0.0, "");
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
        Lesson lesson = lessonFileDAO.getLesson(1); 

        // Analyze
        assertEquals(lesson, testLessons[0]);
    }

    @Test
    public void testUpdateLesson() throws IOException {
        Lesson lesson = new Lesson(1, false, "", "", "", 0, 0, 0.0, "");

        Lesson result = lessonFileDAO.updateLesson(lesson);

        assertNotNull(result);
        Lesson actual = lessonFileDAO.getLesson(lesson.getID());
        assertEquals(actual, lesson);
    }

    @Test
    public void testSaveException() throws IOException {
        doThrow(new IOException())
                .when(mockObjectMapper)
                .writeValue(any(File.class), any(Lesson[].class));

        Lesson lesson = new Lesson(0, false, "", "", "", 0, 0, 0.0, "");

        assertThrows(IOException.class,
                () -> lessonFileDAO.createLesson(lesson),
                "IOException not thrown");
    }

    @Test
    public void testGetProductNotFound() throws IOException {
        // Invoke
        Lesson lesson = lessonFileDAO.getLesson(99);

        // Analyze
        assertEquals(lesson, null);
    }

    @Test
    public void testUpdateProductNotFound() {
        // Setup
        Lesson lesson = new Lesson(0, false, "", "", "", 0, 0, 0.0, "");

        // Invoke
        Lesson result = assertDoesNotThrow(() -> lessonFileDAO.updateLesson(lesson),
                "Unexpected exception thrown");

        // Analyze
        assertNull(result);
    }
    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);

        doThrow(new IOException())
                .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"), Lesson[].class);

        assertThrows(IOException.class, () -> new LessonFileDAO("doesnt_matter.txt", mockObjectMapper),
                "IOException not thrown");
    }

}
