import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

class AddAttendance {

    JFrame frame;
    JPanel panel;

    HashMap< Student, JTextField > map;

    Subject currentSubject;

    JLabel error;

    public AddAttendance() {
        map = new HashMap< Student, JTextField >();
        error = new JLabel("");
    }

    void run(Subject subject) {
        currentSubject = subject;
        frame = new JFrame("Add record of: " + subject.name);
        panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        BoxLayout bl = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(bl);

        JLabel heading = new JLabel("Add a record");
        heading.setFont(new Font("Myraid Pro", Font.BOLD, 20));
        panel.add(heading);

        fillRecords(subject);

        panel.add(error);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new SaveButtonListener());
        panel.add(saveButton);

        setUpFrame();
    }

    void fillRecords(Subject subject) {
        for (Student student : subject.studentsEnrolled) {
            JPanel rowPanel = new JPanel();
            JLabel studentName = new JLabel(student.name);
            JTextField record = new JTextField(4);
            rowPanel.add(studentName);
            rowPanel.add(record);
            rowPanel.setMaximumSize(rowPanel.getPreferredSize());
            panel.add(rowPanel);

            map.put(student, record);
        }
    }

    void setUpFrame() {
        frame.getContentPane().add(panel);
        frame.setVisible(true);
        frame.setSize(300, 300);
    }

    boolean checkValid() {
        try {
            for (Student student : currentSubject.studentsEnrolled) {
                ArrayList<Integer> atten = student.attendance.get(currentSubject);
                int rec = Integer.parseInt(map.get(student).getText());
            }
            return true;
        } catch (NumberFormatException nex) {
            return false;
        }
    }

    class SaveButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            boolean isValid = checkValid();
            if (isValid == true) {
                for (Student student : currentSubject.studentsEnrolled) {
                    ArrayList<Integer> atten = student.attendance.get(currentSubject);
                    int rec = Integer.parseInt(map.get(student).getText());
                    atten.add(rec);
                    student.attendance.put(currentSubject, atten);
                }
                frame.dispose();
            } else {
                error.setText("Enter valid entries");
                frame.repaint();
            }

        }
    }
}

