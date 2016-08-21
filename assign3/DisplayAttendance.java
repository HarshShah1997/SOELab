import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class DisplayAttendance {

    private JFrame frame;
    private JPanel panel;

    void run(Subject subject) {
        frame = new JFrame("Attendance of: " + subject.name);

        setUpFrame();
    }

    void setUpFrame() {
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
}
