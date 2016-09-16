
package iit2014071;
 
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;
import java.io.*;

public class ShowNames {
    
    private JFrame frame;
    private JPanel panel;
    
    String URL = "jdbc:mysql://localhost:3306/iit2014071?useSSL=false";
    String DRIVER = "com.mysql.jdbc.Driver";
    String UNAME = "root";
    String PASS = "iiita";
    
    private ArrayList<JLabel> names = new ArrayList<JLabel>();
    private int height;
    
    
    void run(int height) {
        this.height = height;
        
        fillDetails();
        
        frame = new JFrame("Students");
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        for (JLabel label : names) {
        
        JPanel namepanel = new JPanel();
        JLabel nameLabel = new JLabel("Name: ");
        namepanel.add(nameLabel);
        namepanel.add(label);
        panel.add(namepanel);
        }
        
        frame.getContentPane().add(panel);
        frame.setSize(400, 200);
        frame.setVisible(true);
    }
    
    void fillDetails() {
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(URL, UNAME, PASS);
            String query = "SELECT student.name FROM student, detail WHERE student.username = detail.username AND detail.height >= ?";
            
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, height);
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                //System.out.println
                names.add(new JLabel(rs.getString("student.name")));
                
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            
        }
    }    
    
}
