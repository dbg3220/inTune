package estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import estoreapi.model.Lesson;

/**
 * Implements methods described in DAO<T> using the type Lesson.
 * 
 * @author Damon Gonzalez
 */
@Component
public class LessonFileDAO implements DAO<Lesson>{

    /** The map storing lessons that are mapped from their id */
    private Map<Integer, Lesson> lessons;
    /** Class used to write java objects to json files */
    private ObjectMapper objectMapper;
    /** The next id of a lesson, to be only accessed through nextId() */
    private static int nextId;
    /** The file to read/write to */
    private String filename;

    /**
     * Public constructor for LessonFileDAO
     * @param filename The path of the file to read/write
     * @param objectMapper Provides JSON Object serialization and deserialization
     * @throws IOException If there is an issue with underlying storage
     */
    public LessonFileDAO(@Value("${lessons.file}") String filename,
                         ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    /**
     * Provides the next id for a lesson to be created with in order
     * to preserve uniqueness.
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Saves lesson objects in the map of lessons to file storage
     * @throws IOException when file cannot be accessed or written to
     */
    private void save() throws IOException {
        Lesson[] lessonArray = getLessonsArray();
        objectMapper.writeValue(new File(filename), lessonArray);
    }

    /**
     * Loads lesson objects from file storage
     * @throws IOException when file cannot be accessed or read from
     */
    private void load() throws IOException {
        lessons = new TreeMap<>();
        nextId = -1;
        Lesson[] lessonArray = objectMapper.readValue(new File(filename),Lesson[].class);
        for (Lesson lesson : lessonArray) {
            lessons.put(lesson.getID(), lesson);
            if (lesson.getID() > nextId)
                nextId = lesson.getID();
        }
        ++nextId;//make the next id greater than the last
    }

    /**
     * Generates an array of lessons from the map of lessons
     * 
     * @return The array of lessons, may be empty
     */
    private Lesson[] getLessonsArray() {
        ArrayList<Lesson> lessonArrayList = new ArrayList<>();
        for (Lesson lesson : lessons.values()) {
            lessonArrayList.add(lesson);
        }
        Lesson[] lessonArray = new Lesson[lessonArrayList.size()];
        lessonArrayList.toArray(lessonArray);
        return lessonArray;
    }

    @Override
    public Lesson[] getItems() throws IOException {
        synchronized(lessons){
            return getLessonsArray();
        }
    }

    @Override
    public Lesson getItem(int id) throws IOException {
        synchronized(lessons){
            if( !lessons.containsKey(id) )
                return null;

            return lessons.get(id);
        }
    }

    @Override
    public Lesson createItem(Lesson lesson) throws IOException {
        synchronized(lessons){//TODO implement this so that if attributes are invalid a lesson isn't created
            Lesson newLesson = new Lesson(nextId(),//the next unique id
                                          lesson.getCategory(),
                                          lesson.getInstructor(), 
                                          lesson.getWeekDay(),
                                          lesson.getStartTime(),
                                          -1,//default value
                                          lesson.getPrice(),
                                          lesson.getName());
            lessons.put(newLesson.getID(), newLesson);
            save();
            return newLesson;
        }
    }

    @Override
    public Lesson updateItem(Lesson lesson) throws IOException {
        synchronized(lessons){
            if( !lessons.containsKey(lesson.getID()) )
                return null;

            lessons.put(lesson.getID(), lesson);
            save();
            return lesson;
        }
    }

    @Override
    public boolean deleteItem(int id) throws IOException {
        synchronized(lessons){
            if( !lessons.containsKey(id) )
                return false;

            lessons.remove(id);
            save();
            return true;
        }
    }
}
