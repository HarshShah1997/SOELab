import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;

public class AddMarks {

    String URL = "jdbc:mysql://localhost:3306/assign4?useSSL=false";
    String DRIVER = "com.mysql.jdbc.Driver";
    String MYSQL_USERNAME = "root";
    String MYSQL_PASSWORD = "iiita";

    private JFrame frame;
    private JPanel panel;

    private Map< JTextField, Integer > studentidMap;

    private int subjectid;

    private JLabel error;

    public AddMarks() {
        studentidMap = new HashMap< JTextField, Integer >();
        error = new JLabel("");
    }

    void run(int subjectid) {
        this.subjectid = subjectid;
        frame = new JFrame("Update marks");
        panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        BoxLayout bl = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(bl);

        JLabel heading = new JLabel("Add a record");
        heading.setFont(new Font("Myraid Pro", Font.BOLD, 20));
        panel.add(heading);

        fillRecords();

        panel.add(error);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new SaveButtonListener());
        panel.add(saveButton);

        setUpFrame();
    }

    void fillRecords() {
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(URL, MYSQL_USERNAME, MYSQL_PASSWORD);
            String query = "SELECT student.studentid, student.studentname FROM student, marks WHERE marks.studentid = student.studentid AND marks.subjectid = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, subjectid);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int currStudentid = rs.getInt("student.studentid");
                String currStudentname = rs.getString("student.studentname");

                JPanel rowPanel = new JPanel();
                JTextField record = new JTextField(3);
                rowPanel.add(new JLabel(currStudentname));
                studentidMap.put(record, currStudentid);
                rowPanel.add(record);
                rowPanel.setMaximumSize(rowPanel.getPreferredSize());
                panel.add(rowPanel);
            }
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void setUpFrame() {
        frame.getContentPane().add(panel);
        frame.setVisible(true);
        frame.setSize(300, 300);
    }

    boolean checkValid() {
        try {
            for (JTextField record : studentidMap.keySet()) {
                int res = Integer.parseInt(record.getText());
            }
            return true;
        } catch (NumberFormatException nex) {
            return false;
        }
    }

    void updateTable() {
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(URL, MYSQL_USERNAME, MYSQL_PASSWORD);
            for (JTextField record : studentidMap.keySet()) {
                int studentid = studentidMap.get(record);
                int newMarks = Integer.parseInt(record.getText());

                String query = "UPDATE marks SET marks = marks + ? WHERE studentid = ? AND subjectid = ?";
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setInt(1, newMarks);
                pstmt.setInt(2, studentid);
                pstmt.setInt(3, subjectid);
                pstmt.executeUpdate();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    class SaveButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            boolean isValid = checkValid();
            if (isValid == true) {
                updateTable();
                error.setText("Updated Successfully");
                frame.repaint();
            } else {
                error.setText("Enter valid entries");
                frame.repaint();
            }

        }
    }
}

