package estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import estoreapi.model.Lesson;

import org.springframework.beans.factory.annotation.Value;

public class LessonFileDAO implements LessonDAO {

    Map<Integer, Lesson> lessons;   
    private ObjectMapper objectMapper;  
    private static int nextId;  
    private String filename;
    

    public LessonFileDAO(@Value("${lessons.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the lessons from the file
    }

    private boolean load() throws IOException {
        lessons = new TreeMap<>();
        nextId = 0;
        // Deserializes the JSON objects from the file into an array of lessons
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Lesson[] lessonArray = objectMapper.readValue(new File(filename),Lesson[].class);

        // Add each lesson to the tree map and keep track of the greatest id
        for (Lesson lesson : lessonArray) {
            lessons.put(lesson.getId(), lesson);
            if (lesson.getId() > nextId)
                nextId = lesson.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    private boolean save() throws IOException {
        Lesson[] lessonArray = getLessons();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),lessonArray);
        return true;
    }

    @Override
    public Lesson[] getLessons() throws IOException {
        synchronized(lessons){
        ArrayList<Lesson> lessonArrayList = new ArrayList<>();
        for (Lesson lesson : lessons.values()) {
            lessonArrayList.add(lesson);
        }
        Lesson[] lessonNatArray = new Lesson[lessonArrayList.size()];
        lessonArrayList.toArray(lessonNatArray);
        return lessonNatArray;
    }
    }

    @Override
    public Lesson getLesson(int id) throws IOException {
        synchronized(lessons) {
            if (lessons.containsKey(id))
                return lessons.get(id);
            else
                return null;
        }
    }

    @Override
    public Lesson updateLesson(Lesson lesson) throws IOException {
        synchronized(lessons){
            if(lessons.containsValue(lesson)){
                lessons.put(lesson.getId(), lesson);
                save();
                return lesson;
            }
            else{
                return null;
            }
        }
    }

    
}
