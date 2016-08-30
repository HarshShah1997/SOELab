import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import java.io.*;
import java.sql.*;

public class InstructorHome {

    String URL = "jdbc:mysql://localhost:3306/assign4?useSSL=false";
    String DRIVER = "com.mysql.jdbc.Driver";
    String MYSQL_USERNAME = "root";
    String MYSQL_PASSWORD = "iiita";

    private JFrame frame;
    private JPanel panel;
    
    private JComboBox<String> subjectChoice;
    private Map<Integer, Integer > subjectIdMap;
    private int instructorid;

    public InstructorHome() {
        subjectIdMap = new HashMap<Integer, Integer>();
    }

    void run(int instructorid) {
        this.instructorid = instructorid;
        frame = new JFrame("Welcome");
        panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        BoxLayout bl = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(bl);
        
        JLabel heading = new JLabel("Instructor Home");
        heading.setFont(new Font("Myraid Pro", Font.BOLD, 20));

        JPanel subjectPanel = new JPanel();
        fillSubjectPanel(subjectPanel);
                
        JPanel reportPanel = new JPanel();
        fillReportPanel(reportPanel);

        JPanel addRecordPanel = new JPanel();
        fillAddRecordPanel(addRecordPanel);

        JButton logout = new JButton("Logout");
        logout.addActionListener(new LogoutListener());

        panel.add(heading);
        panel.add(subjectPanel);
        panel.add(addRecordPanel);
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
        JButton attendanceButton = new JButton("View Attendance");
        attendanceButton.addActionListener(new AttendanceButtonListener());

        JButton marksButton = new JButton("View Marks");
        marksButton.addActionListener(new MarksButtonListener());

        reportPanel.add(attendanceButton);
        reportPanel.add(marksButton);
        reportPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        reportPanel.setMaximumSize(reportPanel.getPreferredSize());
    }

    void fillAddRecordPanel(JPanel addRecordPanel) {
        JButton addAttendance = new JButton("Update Attendance");
        addAttendance.addActionListener(new AddAttendanceListener());

        JButton addMarks = new JButton("Update Marks");
        addMarks.addActionListener(new AddMarksListener());

        addRecordPanel.add(addAttendance);
        addRecordPanel.add(addMarks);
        addRecordPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        addRecordPanel.setMaximumSize(addRecordPanel.getPreferredSize());
    }

    void setUpFrame() {
        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(400, 400);
    }

    void fillSubjectChoice() {
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(URL, MYSQL_USERNAME, MYSQL_PASSWORD);
            String query = "SELECT subject.subjectid, subject.subjectname FROM teaches, subject WHERE teaches.subjectid = subject.subjectid AND teaches.instructorid = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, instructorid);
            ResultSet rs = pstmt.executeQuery();
            int count = 0;
            while (rs.next()) {
                int currId = rs.getInt("subject.subjectid");
                String currName = rs.getString("subject.subjectname");
                subjectIdMap.put(count, currId);
                subjectChoice.addItem(currName);
                count++;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    class AttendanceButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            int selectedSubjectId = subjectIdMap.get(subjectChoice.getSelectedIndex());
            new DisplayAttendanceInstructor().run(selectedSubjectId);
        }
    }
    
    class MarksButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            int selectedSubjectId = subjectIdMap.get(subjectChoice.getSelectedIndex());
            new DisplayMarksInstructor().run(selectedSubjectId);
        }
    }

    class AddAttendanceListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            int selectedSubjectId = subjectIdMap.get(subjectChoice.getSelectedIndex());
            new AddAttendance().run(selectedSubjectId);
        }
    }

    class AddMarksListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            int selectedSubjectId = subjectIdMap.get(subjectChoice.getSelectedIndex());
            new AddMarks().run(selectedSubjectId);
        }
    }

    class LogoutListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            new Login().run();
            frame.dispose();
        }
    }
}

