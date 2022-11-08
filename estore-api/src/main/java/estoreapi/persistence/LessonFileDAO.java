package estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import estoreapi.model.Lesson;
/**
 * Implements the functionality for JSON file-based peristance for lessons
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 * 
 * @author Donovan Cataldo
 * 
 */
@Component
public class LessonFileDAO implements LessonDAO{

    Map<Integer,Lesson> lessons;

    private ObjectMapper objectMapper;  // Provides conversion between lessons
                                        // objects and JSON text format written
                                        // to the file
    private static int nextId;  // The next Id to assign to a new lesson
    private String filename;    // Filename to read from and write to

    /**
     * Creates a lesson File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public LessonFileDAO(@Value("${lessons.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the lessons from the file
    }

    /**
     * Generates the next id for a new {@linkplain Lesson lesson}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Saves the {@linkplain Lesson lessons} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Lesson lessons} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Lesson[] lessonArray = getLessons();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),lessonArray);
        return true;
    }

    /**
     * Loads {@linkplain Lesson lessons} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        lessons = new TreeMap<>();
        nextId = 0;
        // Deserializes the JSON objects from the file into an array of lesson
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Lesson[] lessonArray = objectMapper.readValue(new File(filename),Lesson[].class);
        // Add each lesson to the tree map and keep track of the greatest id
        for (Lesson lesson : lessonArray) {
            lessons.put(lesson.getID(), lesson);
            if (lesson.getID() > nextId)
                nextId = lesson.getID();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Lesson[] getLessons() throws IOException {
        ArrayList<Lesson> lessonArrayList = new ArrayList<>();
        for(Lesson lesson : lessons.values()){
            lessonArrayList.add(lesson);
        }
        Lesson[] lessonList = new Lesson[lessonArrayList.size()];
        lessonArrayList.toArray(lessonList);
        return lessonList;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Lesson getLesson(int id) throws IOException {
        synchronized(lessons){
            if(lessons.containsKey(id)){
                return lessons.get(id);
            }
            else{
                return null;
            }
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Lesson createLesson(Lesson lesson) throws IOException {
        synchronized(lessons){
            Lesson newLesson = new Lesson(nextId(),lesson.getCategory(),lesson.getIntructor(),lesson.getWeekDay(),
                lesson.getStartTime(),lesson.getPrice(),lesson.getName());
            lessons.put(lesson.getID(), newLesson);
            save();
            return newLesson;
        }
    }

    @Override
    public Lesson updateLesson(Lesson lesson) throws IOException {
        synchronized(lessons){
            if(lessons.containsKey(lesson.getID())){
                lessons.put(lesson.getID(), lesson);
                save();
                return lesson;
            } else {
                return null;
            }
        }
    }
    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteLesson(int id) throws IOException {
        synchronized(lessons){
            if(lessons.containsKey(id)){
                lessons.remove(id);
                return save();
            }
            else{
                return false;
            }
        }
    }
    
}
