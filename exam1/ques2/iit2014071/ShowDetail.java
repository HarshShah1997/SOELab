
package iit2014071;
 
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;
import java.io.*;

public class ShowDetail {
    
    private JFrame frame;
    private JPanel panel;
    
    String URL = "jdbc:mysql://localhost:3306/iit2014071?useSSL=false";
    String DRIVER = "com.mysql.jdbc.Driver";
    String UNAME = "root";
    String PASS = "iiita";
    
    private String username;
    private JLabel name = new JLabel("");
    private JLabel height = new JLabel("");
    
    
    void run(String username) {
        this.username = username;
        System.out.println(username);
        fillDetails();
        
        frame = new JFrame("Student Details");
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JPanel namepanel = new JPanel();
        JLabel nameLabel = new JLabel("Name: ");
        namepanel.add(nameLabel);
        namepanel.add(name);
        
        JPanel heightpanel = new JPanel();
        JLabel heightLabel = new JLabel("Height: ");
        heightpanel.add(heightLabel);
        heightpanel.add(height);
        
        panel.add(namepanel);
        panel.add(heightpanel);
        
        frame.getContentPane().add(panel);
        frame.setSize(400, 200);
        frame.setVisible(true);
    }
    
    void fillDetails() {
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(URL, UNAME, PASS);
            String query = "SELECT student.name, detail.height FROM student, detail WHERE student.username = detail.username AND student.username = ?";
            
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                //System.out.println
                name = new JLabel(rs.getString("student.name"));
                height = new JLabel("" + rs.getInt("detail.height"));
            }
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }    
    
}
