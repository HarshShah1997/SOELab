import java.util.*;
import java.io.*;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

public class StudentHome {

    String URL = "jdbc:mysql://localhost:3306/assign4?useSSL=false";
    String DRIVER = "com.mysql.jdbc.Driver";
    String MYSQL_USERNAME = "root";
    String MYSQL_PASSWORD = "iiita";

    private JFrame frame;
    private JPanel panel;

    private JComboBox<String> subjectChoice;
    private Map<String, Integer> subjectIdMap;

    private int studentid;

    public StudentHome() {
        subjectIdMap = new HashMap<String, Integer>();
    }

    void run(int studentid) {
        this.studentid = studentid;
        frame = new JFrame("Welcome");
        panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        BoxLayout bl = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(bl);

        JLabel heading = new JLabel("Student Home", SwingConstants.LEFT);
        heading.setFont(new Font("Myraid Pro", Font.BOLD, 20));

        JPanel subjectPanel = new JPanel();
        fillSubjectPanel(subjectPanel);

        JPanel reportPanel = new JPanel();
        fillReportPanel(reportPanel);

        JButton logout = new JButton("Logout");
        logout.addActionListener(new LogoutListener());

        panel.add(heading);
        panel.add(subjectPanel);
        panel.add(reportPanel);
        panel.add(logout);

        setUpFrame();
    }

    void fillSubjectPanel(JPanel subjectPanel) {
        JLabel subjectLabel = new JLabel("Select Subject ");
        subjectChoice = new JComboBox<String>();
        fillSubjectChoice();
        subjectPanel.add(subjectLabel);
        subjectPanel.add(subjectChoice);
        subjectPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        subjectPanel.setMaximumSize(subjectPanel.getPreferredSize());
    }

    void fillReportPanel(JPanel reportPanel) {
        JButton attendanceButton = new JButton("Show my Attendance");
        attendanceButton.addActionListener(new AttendanceButtonListener());

        JButton marksButton = new JButton("Show my Marks");
        marksButton.addActionListener(new MarksButtonListener());

        reportPanel.add(attendanceButton);
        reportPanel.add(marksButton);
        reportPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        reportPanel.setMaximumSize(reportPanel.getPreferredSize());
    }

    void fillSubjectChoice() {
	try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(URL, MYSQL_USERNAME, MYSQL_PASSWORD);
            String query = "SELECT subject.subjectid, subject.subjectname FROM subjectstaken, subject WHERE subjectstaken.subjectid = subject.subjectid AND subjectstaken.studentid = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, studentid);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int currId = rs.getInt("subject.subjectid");
                String currName = rs.getString("subject.subjectname");
                subjectIdMap.put(currName, currId);
                subjectChoice.addItem(currName);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void setUpFrame() {
        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }

    class AttendanceButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            new DisplayAttendanceStudent().run(studentid);
        }
    }

    class MarksButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            new DisplayMarksStudent().run(studentid);
        }
    }

    class LogoutListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            new Login().run();
            frame.dispose();
        }
    }

}
