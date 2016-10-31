import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

public class Main {

    int counter = 0;
    String[] statements;

    void run() {
        String inputData = Helper.getInputFromFile("input.c");
        inputData = Helper.removeQuotes(inputData);
        inputData = Helper.removeSinglelineComments(inputData);
        inputData = Helper.removeMultilineComments(inputData);
        detectFunctions(inputData);
    }

    void detectFunctions(String inputData) {
        Pattern detectFunction = Pattern.compile("\\w+\\s+(\\w+)\\(\\w*\\)\\s*\\{");
        Matcher matcher = detectFunction.matcher(inputData);
        while (matcher.find()) {
            int openingIndex = matcher.end();
            int closingIndex = Helper.findMatching(inputData, openingIndex);
            System.out.println(matcher.group(1));
            decisionTree(inputData.substring(openingIndex, closingIndex));
        }
    }

    void decisionTree(String data) {
        counter = 0;
        statements = data.split(";");
        Node prev = new Node("start");
        Node start = prev;

        while (counter < statements.length) {
            String stmt = statements[counter].trim();
            Pattern detectif = Pattern.compile("if\\s*\\(.*?\\)");
            Matcher ifmatcher = detectif.matcher(stmt);
            if (ifmatcher.find()) {
                solve();
            } else {
                Node current = new Node(stmt);
                prev.next.add(current);
                prev = current;
                counter++;
            }
        }   
        printGraph(start);
    }

    Node solve() {


    void printGraph(Node current) {
        HashMap<Node, Boolean> visited = new HashMap<Node, Boolean>();
        Queue<Node> q = new LinkedList<Node>();

        q.add(current);
        visited.put(current, true);

        while (!q.isEmpty()) {
            Node top = q.peek();
            System.out.print(top.label.trim());
            q.remove();
            System.out.print(" -> ");

            for (Node next : top.next) {
                if (visited.get(next) == null) {
                    q.add(next);
                    visited.put(next, true);
                }
                System.out.print(next.label.trim() + ", ");
            }
            System.out.println("");
            System.out.println("-----------");
        }
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
