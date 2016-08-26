import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.io.*;
import java.sql.*;

public class DisplayMarksInstructor {

    String URL = "jdbc:mysql://localhost:3306/assign4?useSSL=false";
    String DRIVER = "com.mysql.jdbc.Driver";
    String MYSQL_USERNAME = "root";
    String MYSQL_PASSWORD = "iiita";

    private JFrame frame;
    private JPanel panel;

    private int subjectid;

    void run(int subjectid) {
        this.subjectid = subjectid;
        frame = new JFrame("View marks of all students");
        panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(new BorderLayout());

        JLabel studentsLabel = new JLabel("<html>S<br>t<br>u<br>d<br>e<br>n<br>t<br>s");
        panel.add(studentsLabel, BorderLayout.WEST);

        JLabel subjectsLabel = new JLabel("Marks", SwingConstants.CENTER);
        panel.add(subjectsLabel, BorderLayout.NORTH);

        JPanel centralPanel = new JPanel();
        centralPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        fillCentralPanel(centralPanel);
        panel.add(centralPanel, BorderLayout.CENTER);

        setUpFrame();
    }

    void fillCentralPanel(JPanel centralPanel) {
        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.Y_AXIS));
        centralPanel.setBackground(Color.WHITE);
        centralPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(URL, MYSQL_USERNAME, MYSQL_PASSWORD);
            String query = "SELECT student.studentname, marks.marks FROM student, marks WHERE student.studentid = marks.studentid AND marks.subjectid = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, subjectid);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                JPanel currPanel = new JPanel();
                String name = rs.getString("student.studentname");
                int atten = rs.getInt("marks.marks");
                currPanel.add(new JLabel(name));
                currPanel.add(new JLabel("" + atten));
                currPanel.setMaximumSize(currPanel.getPreferredSize());
                centralPanel.add(currPanel);
            }
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void setUpFrame() {
        frame.getContentPane().add(panel);
        frame.setSize(400, 200);
        frame.setVisible(true);
    }
}
