import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;

public class Home {

    private JFrame frame;
    private JPanel panel;

    String[] subjects;
    private JComboBox<String> subjectChoice;

    public Home() {
        subjects = new String[]{"ISOE 532C", "IGVC 532C", "ICNW 532C", "IAIN 532C", "MPOE 530C"};
    }

    void run() {
        //Set up Gui
        frame = new JFrame("Welcome");
        panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        BoxLayout bl = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(bl);
        
        JLabel heading = new JLabel("HOME");
        heading.setFont(new Font("Myraid Pro", Font.BOLD, 20));

        JPanel subjectPanel = new JPanel();
        JLabel subjectLabel = new JLabel("Select Subject ");
        subjectChoice = new JComboBox<String>(subjects);
        subjectPanel.add(subjectLabel);
        subjectPanel.add(subjectChoice);
        subjectPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        subjectPanel.setMaximumSize(subjectPanel.getPreferredSize());

        JButton attendanceButton = new JButton("Get Attendance");

        JButton marksButton = new JButton("Get Marks");

        panel.add(heading);
        panel.add(subjectPanel);
        panel.add(attendanceButton);
        panel.add(marksButton);

        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(400, 400);
    }
}

