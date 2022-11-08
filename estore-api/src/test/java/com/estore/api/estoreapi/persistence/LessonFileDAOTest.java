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
            testLessons[0] = new Lesson(1, 12.99, "Monday", 12, "12pm Monday lesson");
            testLessons[0].setLesson("String", "Clayton", 4);
            testLessons[1] = new Lesson(2, 70.22, "Friday", 2, "2pm Friday lesson");
            testLessons[1].setLesson("String", "Clayton", 4);
            testLessons[2] = new Lesson(3, 122.99, "Thursday", 9,"9am Thursday Lesson");
            testLessons[2].setLesson("String", "Clayton", 4);
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
        lesson.setLesson("String", "Clayton", 4);
        // Analyze
        assertEquals(lesson, testLessons[0]);
    }

    @Test
    public void testSaveException() throws IOException {
        doThrow(new IOException())
                .when(mockObjectMapper)
                .writeValue(any(File.class), any(Lesson[].class));

        Lesson lesson = new Lesson(1, 12.99, "Monday", 12, "12pm Monday lesson");

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
