import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.sql.*;
import java.io.*;

public class Login {

    String URL = "jdbc:mysql://localhost:3306/assign4?useSSL=false";
    String DRIVER = "com.mysql.jdbc.Driver";
    String MYSQL_USERNAME = "root";
    String MYSQL_PASSWORD = "iiita";

    private JFrame frame;
    private JPanel panel;
    private JTextField username;
    private JPasswordField password;
    private JLabel error;

    public Login() {
        error = new JLabel("");
        ;
    }

    void run() {
        frame = new JFrame("Login");
        panel = new JPanel();

        BoxLayout bl = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(bl);

        JLabel heading = new JLabel("LOGIN");
        heading.setFont(new Font("Myraid Pro", Font.BOLD, 20));

        JPanel usernamePanel = new JPanel();
        JLabel userLabel = new JLabel("Username ");
        username = new JTextField(15);
        usernamePanel.add(userLabel);
        usernamePanel.add(username);
        usernamePanel.setMaximumSize( usernamePanel.getPreferredSize() );

        JPanel passwordPanel = new JPanel();
        JLabel passLabel = new JLabel("Password ");
        password = new JPasswordField(15);
        passwordPanel.add(passLabel);
        passwordPanel.add(password);
        passwordPanel.setMaximumSize( passwordPanel.getPreferredSize() );

        JButton button = new JButton("Sign In");
        button.addActionListener(new SigninListener());

        JPanel invalidPanel = new JPanel();
        invalidPanel.add(error);
        
        panel.add(heading);
        panel.add(usernamePanel);
        panel.add(passwordPanel);
        panel.add(button);
        panel.add(invalidPanel);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
    }

    int checkValid(String uname, String pass) {
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(URL, MYSQL_USERNAME, MYSQL_PASSWORD);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT instructorid, instructorname, password FROM instructor");
            while (rs.next()) {
                String currName = rs.getString("instructorname");
                String currPassword = rs.getString("password");
                int instructorid = rs.getInt("instructorid");
                if (currName.equals(uname) && currPassword.equals(pass)) {
                    con.close();
                    return instructorid;
                }
            }
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    class SigninListener implements ActionListener {
        public void actionPerformed(ActionEvent aev) {
            int instructorid = checkValid(username.getText(), new String(password.getPassword()));
            if (instructorid != -1) {
                System.out.println("Success");
                frame.dispose();
            } else {
                error.setText("Invalid username/password");
                frame.revalidate();
                frame.repaint();
            }

        }
    }
}
