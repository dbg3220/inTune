package estoreapi.persistence;

import java.io.IOException;

import estoreapi.model.Lesson;

public interface LessonDAO {
    
Lesson assignLesson(Lesson lesson) throws IOException;

Lesson clearLesson(Lesson lesson) throws IOException;

Lesson getLessons() throws IOException;

Lesson getLesson(int id) throws IOException;

}
