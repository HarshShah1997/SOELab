import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.sql.*;
import java.io.*;

public class AddInstructor {

    String URL = "jdbc:mysql://localhost:3306/assign4?useSSL=false";
    String DRIVER = "com.mysql.jdbc.Driver";
    String MYSQL_USERNAME = "root";
    String MYSQL_PASSWORD = "iiita";

    private JFrame frame;
    private JPanel panel;

    private JTextField instructorname;
    private JTextField password;
    private JList<String> subjectsList;
    private Map< String, Integer> subjectidMap;

    private JLabel error;

    public AddInstructor() {
        subjectsList = new JList<String>();
        subjectsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        subjectidMap = new HashMap< String, Integer>();
        error = new JLabel("");
    }

    public AddInstructor(String errmsg) {
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
        JLabel instructorNameLabel = new JLabel("Instructor's Name *");
        instructorname = new JTextField(10);

        namePanel.add(instructorNameLabel);
        namePanel.add(instructorname);
        namePanel.setMaximumSize(namePanel.getPreferredSize());
    }

    void fillPasswordPanel(JPanel passwordPanel) {
        JLabel instructorPasswordLabel = new JLabel("Password *");
        password = new JTextField(10);
        
        passwordPanel.add(instructorPasswordLabel);
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

    void addInstructor() {
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(URL, MYSQL_USERNAME, MYSQL_PASSWORD);
            String query = "INSERT INTO instructor (instructorname, password) VALUES (?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, instructorname.getText());
            pstmt.setString(2, password.getText());
            pstmt.executeUpdate();

            query = "SELECT instructorid FROM instructor WHERE instructorname = ? AND password = ?";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, instructorname.getText());
            pstmt.setString(2, password.getText());
            ResultSet rs = pstmt.executeQuery();
            int instructorid = -1;
            if (rs.next()) {
                instructorid = rs.getInt("instructorid");
            }

            addSubjectsToDatabase(instructorid);
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void addSubjectsToDatabase(int instructorid) {
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(URL, MYSQL_USERNAME, MYSQL_PASSWORD);

            java.util.List<String> selectedSubjects = subjectsList.getSelectedValuesList();
            for (String subjectname : selectedSubjects) {
                String query = "INSERT INTO teaches (instructorid, subjectid) VALUES (?, ?)";
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setInt(1, instructorid);
                pstmt.setInt(2, subjectidMap.get(subjectname));
                pstmt.executeUpdate();
            }
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    boolean validate() {
        if (instructorname.getText().equals("")) {
            return false;
        } else if (password.getText().equals("")) {
            return false;
        }
        return true;
    }

    class SaveButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            boolean isValid = validate();
            if (isValid) {
                addInstructor();
                new AddInstructor("Added successfully").run();
            } else {
                new AddInstructor("*Required").run();
            }
            frame.dispose();
        }
    }
}
