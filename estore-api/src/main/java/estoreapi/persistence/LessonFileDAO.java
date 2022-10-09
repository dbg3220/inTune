package estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import estoreapi.model.Lesson;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Implements the functionality for JSON file-based peristance for lessons
 * 
 * Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 * 
 * @author Donovan Cataldo
 */
@Component
public class LessonFileDAO implements LessonDAO {

    Map<Integer, Lesson> lessons; // Provides a local cache of the product objects
                                  // so that we don't need to read from the file each time
    private ObjectMapper objectMapper; // Provides conversion between product objects and JSON text format written to the file
    private static int nextId; // The next Id to assign to a new lesson
    private String filename; // Filename to read from and write to
    
    /**
     * Creates a product File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public LessonFileDAO(@Value("${lessons.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load(); 
    }

    /**
     * Loads lessons from the JSON file into the map
     * 
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
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

    /**
     * Saves the lessons from the map into the file as an array of JSON objects
     * 
     * @return true if the lessons were written successfully
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
    * Retrieves all lessons in the lesson JSON as an array of lessons
    * 
    * @return An array of lesson objects
    * 
    * @throws IOException if an issue with underlying storage
    */
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

    /**
    * Retrieves a lesson with the given id
    * 
    * @param id The if of the lesson we want to retrieve
    * 
    * @return The lesson object with the watching id
    * null if the lesson is not found
    * 
    * @throws IOException if an issue with underlying storage
    */
    @Override
    public Lesson getLesson(int id) throws IOException {
        synchronized(lessons) {
            if (lessons.containsKey(id))
                return lessons.get(id);
            else
                return null;
        }
    }

    /**
    * Updates and saves a lesson
    * 
    * @param lesson the lesson to be updated and saved
    * 
    * @return updated lesson if successful
    * null if unsuccessful
    * 
    * @throws IOException if an issue with underlying storage
    */
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
