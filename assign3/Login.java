import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;


public class Login {

    private ArrayList<User> users;

    private JFrame frame;
    private JPanel panel;
    private JTextField username;
    private JPasswordField password;

    public Login() {
        users = new ArrayList<User>();
        users.add(new User("admin", "admin"));
    }

    void run() {
        frame = new JFrame("Login");
        panel = new JPanel();

        frame.getContentPane().add(panel);
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
        
        panel.add(heading);
        panel.add(usernamePanel);
        panel.add(passwordPanel);
        panel.add(button);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        //frame.pack();
    }

    boolean checkValid(String uname, String pass) {
        for (User user : users) {
            if (user.getUsername().equals(uname) && user.getPassword().equals(pass)) {
                return true;
            }
        }
        return false;
    }

    class SigninListener implements ActionListener {
        public void actionPerformed(ActionEvent aev) {
            boolean valid = checkValid(username.getText(), new String(password.getPassword()));
            if (valid == true) {
                frame.dispose();
                new Home().run();
                //System.out.println("working");
            }
        }
    }
}
