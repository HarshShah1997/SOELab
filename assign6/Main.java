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

        Stack<Node> waitStack = new Stack<Node>();

        for (String stmt : statements) {
            stmt = stmt.trim();
            Node current = new Node("");
            Pattern pattern = Pattern.compile("((if|while|for)\\s*\\(.*?\\))\\s*\\{");
            Boolean flag = false;
            
            int counter = 0;
            while (true) {

                Matcher matcher = pattern.matcher(stmt);
                if (matcher.find()) {

                    ifStack.push(new Node(matcher.group(1)));
                    prev.next.add(ifStack.peek());
                    stmt = matcher.replaceAll("");
                    prev = ifStack.peek();
                    flag = false;

                } else if (!elseStack.empty() && (stmt.indexOf("}") != -1 || counter == 2)) {
                    if (counter == 0) {
                        stmt = stmt.substring(stmt.indexOf("}") + 1);
                    } else {
                        counter = 0;
                    }
                    waitStack.push(elseStack.peek());
                    elseStack.pop();
                    flag = false;

                } else if (!ifStack.empty() && stmt.indexOf("}") != -1) {
                        stmt = stmt.substring(stmt.indexOf("}") + 1);

                    Pattern elsePattern = Pattern.compile("\\s*else\\s*\\{");
                    Matcher elseMatcher = elsePattern.matcher(stmt);

                    Pattern elseifPattern = Pattern.compile("\\s*else\\s*");
                    Matcher elseifMatcher = elseifPattern.matcher(stmt);

                    if (elseMatcher.find()) {
                        elseStack.push(prev);
                        stmt = elseMatcher.replaceAll("");
                        flag = true;
                    } else if (ifStack.peek().label.indexOf("while") != -1 || ifStack.peek().label.indexOf("for") != -1) {
                        prev.next.add(ifStack.peek());
                        flag = true;
                    } else if (elseifMatcher.find()) {
                        elseStack.push(prev);
                        stmt = elseifMatcher.replaceAll("");
                        counter = 1;
                        flag = true;
                    } else {
                        flag = false;
                    }
                    waitStack.push(ifStack.peek());
                    ifStack.pop();

                } else {
                    if (counter == 1) {
                        counter++;
                    }
                    current = new Node(stmt);
                    while (!waitStack.empty()) {
                        waitStack.pop().next.add(current);
                    }
                    if (!flag) {
                        prev.next.add(current);
                    }
                    break;
                }
            }
            prev = current;
        }
        prev.next.add(new Node("end"));
        printGraph(start);
    }

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
