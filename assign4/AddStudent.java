import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.sql.*;
import java.io.*;

public class AddStudent {

    String URL = "jdbc:mysql://localhost:3306/assign4?useSSL=false";
    String DRIVER = "com.mysql.jdbc.Driver";
    String MYSQL_USERNAME = "root";
    String MYSQL_PASSWORD = "iiita";

    private JFrame frame;
    private JPanel panel;

    private JTextField studentname;
    private JTextField password;
    private JList<String> subjectsList;
    private Map< String, Integer> subjectidMap;

    private JLabel error;

    public AddStudent() {
        subjectsList = new JList<String>();
        subjectsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        subjectidMap = new HashMap< String, Integer>();
        error = new JLabel("");
    }

    public AddStudent(String errmsg) {
        this();
        error = new JLabel(errmsg);
    }

    void run() {
        frame = new JFrame("Add Student");
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel namePanel = new JPanel();
        fillNamePanel(namePanel);

        JPanel passwordPanel = new JPanel();
        fillPasswordPanel(passwordPanel);

        JPanel subjectPanel = new JPanel();
        fillSubjectPanel(subjectPanel);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new SaveButtonListener());

        panel.add(namePanel);
        panel.add(passwordPanel);
        panel.add(subjectPanel);
        panel.add(saveButton);
        panel.add(error);

        setUpFrame();
    }

    void fillNamePanel(JPanel namePanel) {
        JLabel studentNameLabel = new JLabel("Student's Name ");
        studentname = new JTextField(10);

        namePanel.add(studentNameLabel);
        namePanel.add(studentname);
        namePanel.setMaximumSize(namePanel.getPreferredSize());
    }

    void fillPasswordPanel(JPanel passwordPanel) {
        JLabel studentPasswordLabel = new JLabel("Password ");
        password = new JTextField(10);
        
        passwordPanel.add(studentPasswordLabel);
        passwordPanel.add(password);
        passwordPanel.setMaximumSize(passwordPanel.getPreferredSize());
    }

    void fillSubjectPanel(JPanel subjectPanel) {
        JLabel subjectLabel = new JLabel("Select Subjects ");
        fillSubjects();

        subjectPanel.add(subjectLabel);
        subjectPanel.add(subjectsList);
        subjectPanel.setMaximumSize(subjectPanel.getPreferredSize());
    }

    void fillSubjects() {
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(URL, MYSQL_USERNAME, MYSQL_PASSWORD);

            String query = "SELECT subjectid, subjectname FROM subject";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ArrayList<String> subjectNames = new ArrayList<String>();
            while (rs.next()) {
                int subjectid = rs.getInt("subjectid");
                String subjectname = rs.getString("subjectname");
                subjectidMap.put(subjectname, subjectid);
                subjectNames.add(subjectname);
            }
            String[] myarr = subjectNames.toArray(new String[0]);
            subjectsList.setListData(myarr);
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void setUpFrame() {
        frame.getContentPane().add(panel);
        frame.setSize(300, 300);
        frame.setVisible(true);
    }

    void addStudent() {
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(URL, MYSQL_USERNAME, MYSQL_PASSWORD);
            String query = "INSERT INTO student (studentname, password) VALUES (?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, studentname.getText());
            pstmt.setString(2, password.getText());
            pstmt.executeUpdate();

            query = "SELECT studentid FROM student WHERE studentname = ? AND password = ?";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, studentname.getText());
            pstmt.setString(2, password.getText());
            ResultSet rs = pstmt.executeQuery();
            int studentid = -1;
            if (rs.next()) {
                studentid = rs.getInt("studentid");
            }

            addSubjectsToDatabase(studentid);
            addAttendanceToDatabase(studentid);
            addMarksToDatabase(studentid);
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void addSubjectsToDatabase(int studentid) {
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(URL, MYSQL_USERNAME, MYSQL_PASSWORD);

            java.util.List<String> selectedSubjects = subjectsList.getSelectedValuesList();
            for (String subjectname : selectedSubjects) {
                String query = "INSERT INTO subjectstaken (studentid, subjectid) VALUES (?, ?)";
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setInt(1, studentid);
                pstmt.setInt(2, subjectidMap.get(subjectname));
                pstmt.executeUpdate();
            }
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    void addAttendanceToDatabase(int studentid) {
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(URL, MYSQL_USERNAME, MYSQL_PASSWORD);

            java.util.List<String> selectedSubjects = subjectsList.getSelectedValuesList();
            for (String subjectname : selectedSubjects) {
                String query = "INSERT INTO attendance (studentid, subjectid, attendance) VALUES (?, ?, ?)";
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setInt(1, studentid);
                pstmt.setInt(2, subjectidMap.get(subjectname));
                pstmt.setInt(3, 0);
                pstmt.executeUpdate();
            }
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    void addMarksToDatabase(int studentid) {
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(URL, MYSQL_USERNAME, MYSQL_PASSWORD);

            java.util.List<String> selectedSubjects = subjectsList.getSelectedValuesList();
            for (String subjectname : selectedSubjects) {
                String query = "INSERT INTO marks (studentid, subjectid, marks) VALUES (?, ?, ?)";
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setInt(1, studentid);
                pstmt.setInt(2, subjectidMap.get(subjectname));
                pstmt.setInt(3, 0);
                pstmt.executeUpdate();
            }
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    boolean validate() {
        return true;
    }

    class SaveButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            boolean isValid = validate();
            if (isValid) {
                addStudent();
                new AddStudent("Added successfully").run();
                frame.dispose();
            }
        }
    }
}
