import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import java.io.*;
import java.sql.*;

public class AdminHome {

    String URL = "jdbc:mysql://localhost:3306/assign4?useSSL=false";
    String DRIVER = "com.mysql.jdbc.Driver";
    String MYSQL_USERNAME = "root";
    String MYSQL_PASSWORD = "iiita";

    private JFrame frame;
    private JPanel panel;
    
    private JComboBox<String> subjectChoice;
    private Map<Integer, Integer > subjectIdMap;
    private JLabel error;
    private int instructorid;

    public AdminHome() {
        subjectIdMap = new HashMap<Integer, Integer>();
        error = new JLabel("");
    }
    
    public AdminHome(String msg) {
        error = new JLabel(msg);
    
    }

    void run(int instructorid) {
        this.instructorid = instructorid;
        frame = new JFrame("Welcome Admin");
        panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        BoxLayout bl = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(bl);
        
        JLabel heading = new JLabel("ADMIN");
        heading.setFont(new Font("Myraid Pro", Font.BOLD, 20));

        JPanel subjectPanel = new JPanel();
        fillSubjectPanel(subjectPanel);
                
        JPanel reportPanel = new JPanel();
        fillReportPanel(reportPanel);

        JPanel addRecordPanel = new JPanel();
        fillAddRecordPanel(addRecordPanel);

        JPanel addMembersPanel = new JPanel();
        fillAddMembersPanel(addMembersPanel);

        JButton logout = new JButton("Logout");
        logout.addActionListener(new LogoutListener());

        panel.add(heading);
        panel.add(subjectPanel);
        panel.add(addRecordPanel);
        panel.add(reportPanel);
        panel.add(addMembersPanel);
        panel.add(logout);
        panel.add(error);

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

    void fillAddMembersPanel(JPanel addMembersPanel) {
        JButton addInstructor = new JButton("Add Instructor");
        addInstructor.addActionListener(new AddInstructorListener());

        JButton addStudent = new JButton("Add Student");
        addStudent.addActionListener(new AddStudentListener());

        addMembersPanel.add(addInstructor);
        addMembersPanel.add(addStudent);
        addMembersPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        addMembersPanel.setMaximumSize(addMembersPanel.getPreferredSize());
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
    
    boolean check(int selectedSubjectId) {
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(URL, MYSQL_USERNAME, MYSQL_PASSWORD);
            String query = "SELECT teaches.subjectid FROM teaches, instructor WHERE "
                    + "instructor.instructorid = teaches.instructorid AND "
                    + "instructor.instructorname = 'gcnandi'";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int currSubId = rs.getInt("teaches.subjectid");
                if (currSubId == selectedSubjectId) {
                    return true;
                }
            }
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
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
            boolean isPossible = check(selectedSubjectId);
            if (!isPossible) {
                //System.out.println("Not possible");
                JOptionPane.showMessageDialog(null, "Invalid choice!", "Error", JOptionPane.ERROR_MESSAGE);
                //new AdminHome("Invalid choice!").run(this.instructorid);
            } else {
                new AddAttendance().run(selectedSubjectId);
            }
            
        }
    }

    class AddMarksListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            int selectedSubjectId = subjectIdMap.get(subjectChoice.getSelectedIndex());
            boolean isPossible = check(selectedSubjectId);
            if (!isPossible) {
                //System.out.println("Not possible");
                JOptionPane.showMessageDialog(null, "Invalid choice!", "Error", JOptionPane.ERROR_MESSAGE);
                //new AdminHome("Invalid choice!").run(instructorid);
            } else {
                new AddAttendance().run(selectedSubjectId);
            }
        }
    }

    class LogoutListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            new Login().run();
            frame.dispose();
        }
    }

    class AddStudentListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            new AddStudent().run();
        }
    }

    class AddInstructorListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            new AddInstructor().run();
        }
    }
}

