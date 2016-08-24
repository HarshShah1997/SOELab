import java.util.*;
import java.io.*;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

class StudentHome {

    JFrame frame;
    JPanel panel;

    int studentid;

    void run(int studentid) {
        this.studentid = studentid;
        frame = new JFrame("Welcome");

        setUpFrame();
    }

    void setUpFrame() {
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
}
