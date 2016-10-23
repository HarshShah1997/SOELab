import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

public class Main {

    void run() {
        String inputData = Helper.getInputFromFile("input.c");
        inputData = Helper.removeQuotes(inputData);
        inputData = Helper.removeSinglelineComments(inputData);
        inputData = Helper.removeMultilineComments(inputData);
        decisionTree(inputData);
    }

    void decisionTree(String data) {
        String[] statements = data.split(";");
        Node prev = new Node("start");
        Node start = prev;
        boolean ifstarted = false;
        Node ifcondition = new Node("");

        boolean elsestarted = false;
        Node elsecondition = new Node("");

        for (String stmt : statements) {
            stmt = stmt.trim();
            Node current = new Node("");
            Pattern pattern = Pattern.compile("(if\\s*\\(.*?\\))\\s*\\{");
            Matcher matcher = pattern.matcher(stmt);

            if (matcher.find()) {

                ifcondition = new Node(matcher.group(1));
                prev.next.add(ifcondition);
                ifstarted = true;
                stmt = matcher.replaceAll("");
                current = new Node(stmt);
                prev = ifcondition;
                prev.next.add(current);

            } else if (elsestarted == true && stmt.indexOf("}") != -1) {

                elsestarted = false;
                stmt = stmt.substring(stmt.indexOf("}") + 1);
                current = new Node(stmt);
                elsecondition.next.add(current);
                prev.next.add(current);

            } else if (ifstarted == true && stmt.indexOf("}") != -1) {
                
                ifstarted = false;
                stmt = stmt.substring(stmt.indexOf("}") + 1);

                Pattern elsePattern = Pattern.compile("\\s*else\\s*\\{");
                Matcher elseMatcher = elsePattern.matcher(stmt);
                if (elseMatcher.find()) {
                    elsestarted = true;
                    elsecondition = prev;
                    stmt = elseMatcher.replaceAll("");
                    current = new Node(stmt);
                } else {
                    current = new Node(stmt);
                    prev.next.add(current);
                }
                ifcondition.next.add(current);

            } else {
                current = new Node(stmt);
                prev.next.add(current);
            }
            //System.out.println(stmt);
            prev = current;
        }
        prev.next.add(new Node("end"));
        printGraph(start);
    }

    void printGraph(Node current) {
        if (current.label.equals("end")) {
            return;
        }
        System.out.print(current.label.trim() + " -> ");
        for (Node next : current.next) {
            System.out.print(next.label.trim() + ", ");
        }
        System.out.println("");
        for (Node next : current.next) {
            printGraph(next);
        }
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
