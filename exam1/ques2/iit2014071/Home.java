
package iit2014071;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;
import java.io.*;

public class Home {
    
    private JFrame frame;
    private JPanel panel;
    
    private JTextField usernamefield;
    private JTextField heightfield;
    private JLabel error;
    
    public Home() {
        error = new JLabel("");
    }
    
    public Home(String msg) {
        error = new JLabel(msg);
    }
    
    void run() {
        frame = new JFrame("Question 2");
        panel = new JPanel();
        
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JPanel usernamepanel = new JPanel();
        JLabel usernameLabel = new JLabel("User name: ");
        usernamefield = new JTextField(10);
        JButton usernameButton = new JButton("Go");
        usernameButton.addActionListener(new UsernameListener());
        usernamepanel.add(usernameLabel);
        usernamepanel.add(usernamefield);
        usernamepanel.add(usernameButton);
        usernamepanel.setMaximumSize(usernamepanel.getPreferredSize());
        
        JPanel heightpanel = new JPanel();
        JLabel heightLabel = new JLabel("Height");
        heightfield = new JTextField(3);
        JButton heightButton = new JButton("Go");
        heightButton.addActionListener(new HeightListener());
        heightpanel.add(heightLabel);
        heightpanel.add(heightfield);
        heightpanel.add(heightButton);
        heightpanel.setMaximumSize(heightpanel.getPreferredSize());
        
        panel.add(usernamepanel);
        panel.add(heightpanel);
        panel.add(error);
        
        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(400, 200);
    }
    
    private class UsernameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ev) {
            new ShowDetail().run(usernamefield.getText());
        }
    }
    
    private class HeightListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ev) {
            try {
                int currheight = Integer.parseInt(heightfield.getText());
                if (currheight <= 0) {
                    new Home("Invalid").run();
                    frame.dispose();
                } else {                    
                    new ShowNames().run(currheight);
                }
            } catch (NumberFormatException nex) {
                new Home("Invalid").run();
                frame.dispose();
            }
        }
    }
}
