package estoreapi.persistence;

import java.io.IOException;
import estoreapi.model.Lesson;

public interface LessonDAO {
    
    Lesson[] getLessons() throws IOException;

    Lesson getLesson(int id) throws IOException;

    Lesson createLesson(Lesson lesson) throws IOException;

    Lesson updateLesson(Lesson lesson) throws IOException;

    boolean deleteLesson(int id) throws IOException;

}
