package com.estore.api.estoreapi.persistence;

import estoreapi.persistence.LessonFileDAO;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import estoreapi.model.Lesson;
import estoreapi.model.Product;
import estoreapi.model.User;

/**
 * The junit test suit for the LessonFileDAO class
 * 
 * @author Damon Gonzalez
 */
@Tag("Persistence-tier")
public class LessonFileDAOTest {
    LessonFileDAO lessonFileDAO;
    Lesson[] testLessons;
    ObjectMapper mockObjectMapper;
    User user;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * 
     * @throws IOException
     */
    @BeforeEach
    public void setupHeroFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        user = new User(0, "Kenneth", "kenny", "sss@gmail.com", "pwd",
                "40 west st", "0000000000000000", 6, 2000, null, new User[0], false);
        testLessons = new Lesson[2];
        testLessons[0] = new Lesson(0, "Clarinet Lesson", 30, Product.Category.WOODWINDS,
                1, false, false, true, "Paul Mccartney", user, Lesson.Day.Monday, "3:00", false);
        testLessons[1] = new Lesson(1, "Trumpet Lesson", 30, Product.Category.BRASS,
                1, false, false, true, "Paul Mccartney", user, Lesson.Day.Tuesday, "3:00", false);

        when(mockObjectMapper
                .readValue(new File("doesn't_matter.txt"), Lesson[].class))
                .thenReturn(testLessons);
        lessonFileDAO = new LessonFileDAO("doesn't_matter.txt", mockObjectMapper);
    }

    @Test
    public void testGetLessons() {
        try {
            Lesson[] lessons = lessonFileDAO.getLessons();
            assertEquals(lessons.length, testLessons.length);
            for (int i = 0; i < testLessons.length; i++) {
                assertEquals(lessons[i], testLessons[i]);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Test
    public void testGetLesson() {
        try {
            Lesson lesson = lessonFileDAO.getLesson(0);
            assertEquals(lesson, testLessons[0]);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Test
    public void testUpdateLesson() {
        try {
            Lesson lesson = new Lesson(0, "Clarinet Lesson", 30, Product.Category.WOODWINDS,
                1, false, false, true, "Paul Mccartney", user, Lesson.Day.Tuesday, "3:00", false);
            Lesson result = assertDoesNotThrow(() -> lessonFileDAO.updateLesson(lesson), "unexpected exception thrown");

            assertNotNull(result);
            Lesson actual = lessonFileDAO.getLesson(lesson.getId());
            assertEquals(actual,lesson);
        } catch(IOException e) {
            System.out.println(e);
        }
    }
}
