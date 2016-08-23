import java.util.*;
import java.io.*;

class Subject implements Serializable {

    String name;
    ArrayList<Student> studentsEnrolled;

    public Subject(String subjectName) {
        studentsEnrolled = new ArrayList<Student>();
        name = subjectName;
    }
}
