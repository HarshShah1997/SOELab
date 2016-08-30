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

    public AddStudent() {
        subjectsList = new JList<String>();
        subjectsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        subjectidMap = new HashMap< String, Integer>();
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

        panel.add(namePanel);
        panel.add(passwordPanel);
        panel.add(subjectPanel);
        panel.add(saveButton);

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
}


