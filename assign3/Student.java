import java.util.*;

class Student {

    String name;
    ArrayList<Subject> subjectsTaken;

    HashMap< Subject, ArrayList<Integer> > attendance;

    public Student(String studentName) {
        subjectsTaken = new ArrayList<Subject>();
        attendance = new HashMap<Subject, ArrayList<Integer>>();
        name = studentName;
    }

    void addSubjectsTaken(Subject subject) {
        subjectsTaken.add(subject);
        attendance.put(subject, new ArrayList<Integer>());
    }
}
