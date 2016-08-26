import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.io.*;
import java.sql.*;

public class DisplayAttendanceStudent {

    String URL = "jdbc:mysql://localhost:3306/assign4?useSSL=false";
    String DRIVER = "com.mysql.jdbc.Driver";
    String MYSQL_USERNAME = "root";
    String MYSQL_PASSWORD = "iiita";

    private JFrame frame;
    private JPanel panel;

    private int studentid;

    void run(int studentid) {
        this.studentid = studentid;
        frame = new JFrame("View attendance of all subjects");
        panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(new BorderLayout());

        JLabel studentsLabel = new JLabel("<html>S<br>u<br>b<br>j<br>e<br>c<br>t<br>s");
        panel.add(studentsLabel, BorderLayout.WEST);

        JLabel subjectsLabel = new JLabel("Attendance", SwingConstants.CENTER);
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
            String query = "SELECT subject.subjectname, attendance.attendance FROM subject, attendance WHERE subject.subjectid = attendance.subjectid AND attendance.studentid = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, studentid);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                JPanel currPanel = new JPanel();
                String name = rs.getString("subject.subjectname");
                int atten = rs.getInt("attendance.attendance");
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
