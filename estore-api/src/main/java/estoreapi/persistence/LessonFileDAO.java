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

@Component
public class LessonFileDAO implements LessonDAO{

    Map<Integer,Lesson> lessons;

    private ObjectMapper objectMapper;  // Provides conversion between lessons
                                        // objects and JSON text format written
                                        // to the file
    private static int nextId;  // The next Id to assign to a new lesson
    private String filename;    // Filename to read from and write to

    public LessonFileDAO(@Value("${lessons.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the lessons from the file
    }

    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    private boolean save() throws IOException {
        Lesson[] lessonArray = getLessonsArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),lessonArray);
        return true;
    }

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
     * Generates an array of {@linkplain Lesson lessons} from the tree map
     * 
     * @return  The array of {@link Lesson lessons}, may be empty
     */
    private Lesson[] getLessonsArray() {
        return getLessonsArray(null);
    }

    /**
     * Generates an array of {@linkplain Lesson lessons} from the tree map for any
     * {@linkplain Lesson lessons} that contains the text specified by containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain Lessons lessons}
     * in the tree map
     * 
     * @return  The array of {@link Lesson lessons}, may be empty
     */
    private Lesson[] getLessonsArray(String containsText) { // if containsText == null, no filter
        ArrayList<Lesson> lessonArrayList = new ArrayList<>();

        for (Lesson lesson : lessons.values()) {
            if (containsText == null || lesson.getName().contains(containsText)) {
                lessonArrayList.add(lesson);
            }
        }

        Lesson[] lessonArray = new Lesson[lessonArrayList.size()];
        lessonArrayList.toArray(lessonArray);
        return lessonArray;
    }

    @Override
    public Lesson[] getLessons() throws IOException {
        synchronized(lessons){
            return getLessonsArray();
        }
    }

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

    @Override
    public Lesson createLesson(Lesson lesson) throws IOException {
        synchronized(lessons){
            Lesson newLesson = new Lesson(nextId(), false, lesson.getCategory(), lesson.getInstructor(), 
                                            lesson.getWeekDay(), lesson.getStartTime(), -1, 
                                            lesson.getPrice(), lesson.getName());
            lessons.put(newLesson.getID(), newLesson);
            save();
            return newLesson;
        }
    }

    @Override
    public Lesson updateLesson(Lesson lesson) throws IOException {
        synchronized(lessons){
            if(lessons.containsKey(lesson.getID()) == false){
                return null;
            }
            else{
                lessons.put(lesson.getID(), lesson);
                save();
                return lesson;
            }
        }
    }

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
