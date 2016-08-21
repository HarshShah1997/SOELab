import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

class DisplayAttendance {

    private JFrame frame;
    private JPanel panel;

    void run(Subject subject) {
        frame = new JFrame("Attendance of: " + subject.name);
        panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(new BorderLayout());

        JLabel studentsLabel = new JLabel("<html>S<br>t<br>u<br>d<br>e<br>n<br>t<br>s");
        panel.add(studentsLabel, BorderLayout.WEST);

        JLabel subjectsLabel = new JLabel("Months", SwingConstants.CENTER);
        panel.add(subjectsLabel, BorderLayout.NORTH);

        JPanel centralPanel = new JPanel();
        centralPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        fillCentralPanel(subject, centralPanel);
        panel.add(centralPanel, BorderLayout.CENTER);

        setUpFrame();
    }

    void fillCentralPanel(Subject subject, JPanel centralPanel) {
        centralPanel.setLayout(new GridLayout(3, 5));
        for (Student student : subject.studentsEnrolled) {
            centralPanel.add(new JLabel(student.name));
            for (int i = 0; i < student.attendance.get(subject).size(); i++) {
                JLabel label = new JLabel("" + student.attendance.get(subject).get(i));
                centralPanel.add(label);
            }
        }
    }


    void setUpFrame() {
        frame.add(panel);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
}
