import java.util.*;
import java.io.*;

class Student implements Serializable {

    String name;
    ArrayList<Subject> subjectsTaken;

    HashMap< Subject, ArrayList<Integer> > attendance;
    HashMap< Subject, ArrayList<Integer> > marks;

    public Student(String studentName) {
        subjectsTaken = new ArrayList<Subject>();
        attendance = new HashMap<Subject, ArrayList<Integer>>();
        marks = new HashMap<Subject, ArrayList<Integer>>();
        name = studentName;
    }

    void addSubjectsTaken(Subject subject) {
        subjectsTaken.add(subject);
        attendance.put(subject, new ArrayList<Integer>());
        marks.put(subject, new ArrayList<Integer>());
    }

    void clearAttendance(Subject subject) {
        attendance.put(subject, new ArrayList<Integer>());
    }

    void clearMarks(Subject subject) {
        marks.put(subject, new ArrayList<Integer>());
    }

}
