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
    }

    public Login(String err) {
        error = new JLabel(err);
    }

    void run() {
        frame = new JFrame("Login");
        panel = new JPanel();

        BoxLayout bl = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(bl);

        JLabel heading = new JLabel("LOGIN");
        heading.setFont(new Font("Myraid Pro", Font.BOLD, 20));

        JPanel usernamePanel = new JPanel();
        fillUsernamePanel(usernamePanel);

        JPanel passwordPanel = new JPanel();
        fillPasswordPanel(passwordPanel);

        JButton button = new JButton("Sign In");
        button.addActionListener(new SigninListener());

        JPanel invalidPanel = new JPanel();
        invalidPanel.add(error);
        
        panel.add(heading);
        panel.add(usernamePanel);
        panel.add(passwordPanel);
        panel.add(button);
        panel.add(invalidPanel);

        setUpFrame();
    }

    void fillUsernamePanel(JPanel usernamePanel) {
        JLabel userLabel = new JLabel("Username ");
        username = new JTextField(15);
        usernamePanel.add(userLabel);
        usernamePanel.add(username);
        usernamePanel.setMaximumSize(usernamePanel.getPreferredSize());
    }

    void fillPasswordPanel(JPanel passwordPanel) {
        JLabel passLabel = new JLabel("Password ");
        password = new JPasswordField(15);
        passwordPanel.add(passLabel);
        passwordPanel.add(password);
        passwordPanel.setMaximumSize(passwordPanel.getPreferredSize());
    }

    void setUpFrame() {
        frame.getContentPane().add(panel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
    }

    int checkValid(String uname, String pass, String tablename) {
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(URL, MYSQL_USERNAME, MYSQL_PASSWORD);
            String query = "SELECT " + tablename + "id FROM " + tablename + " WHERE " + tablename +"name = ? AND password = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, uname);
            pstmt.setString(2, pass);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(tablename + "id");
                con.close();
                return id;
            }
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    class SigninListener implements ActionListener {
        public void actionPerformed(ActionEvent aev) {
            int instructorid = checkValid(username.getText(), new String(password.getPassword()), "instructor");
            int studentid = checkValid(username.getText(), new String(password.getPassword()), "student");
            if (instructorid != -1) {
                new InstructorHome().run(instructorid);
            } else if (studentid != -1) {
                new StudentHome().run(studentid);
            } else {
                new Login("Invalid username/password").run();
            }
            frame.dispose();
        }
    }
}
