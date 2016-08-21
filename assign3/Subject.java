import java.util.*;

class Subject {

    String name;
    ArrayList<Student> studentsEnrolled;

    public Subject(String subjectName) {
        studentsEnrolled = new ArrayList<Student>();
        name = subjectName;
    }
}
