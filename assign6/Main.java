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

        Stack<Node> ifStack = new Stack<Node>();
        Stack<Node> elseStack = new Stack<Node>();

        for (String stmt : statements) {
            stmt = stmt.trim();
            Node current = new Node("");
            Pattern pattern = Pattern.compile("(if\\s*\\(.*?\\))\\s*\\{");
            Matcher matcher = pattern.matcher(stmt);

            if (matcher.find()) {

                ifStack.push(new Node(matcher.group(1)));
                prev.next.add(ifStack.peek());
                stmt = matcher.replaceAll("");
                current = new Node(stmt);
                prev = ifStack.peek();
                prev.next.add(current);

            } else if (!elseStack.empty() && stmt.indexOf("}") != -1) {

                stmt = stmt.substring(stmt.indexOf("}") + 1);
                current = new Node(stmt);
                elseStack.peek().next.add(current);
                prev.next.add(current);
                elseStack.pop();

            } else if (!ifStack.empty() && stmt.indexOf("}") != -1) {
                
                stmt = stmt.substring(stmt.indexOf("}") + 1);

                Pattern elsePattern = Pattern.compile("\\s*else\\s*\\{");
                Matcher elseMatcher = elsePattern.matcher(stmt);
                if (elseMatcher.find()) {
                    elseStack.push(prev);
                    stmt = elseMatcher.replaceAll("");
                    current = new Node(stmt);
                } else {
                    current = new Node(stmt);
                    prev.next.add(current);
                }
                ifStack.peek().next.add(current);
                ifStack.pop();

            } else {
                current = new Node(stmt);
                prev.next.add(current);
            }
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
