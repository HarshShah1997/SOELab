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
        detectFunctions(inputData);
    }

    void detectFunctions(String inputData) {
        Pattern detectFunction = Pattern.compile("\\w+\\s+(\\w+)\\(.*?\\)\\s*\\{");
        Matcher matcher = detectFunction.matcher(inputData);
        while (matcher.find()) {
            int openingIndex = matcher.end();
            int closingIndex = Helper.findMatching(inputData, openingIndex);
            System.out.println(matcher.group(1));
            decisionTree(inputData.substring(openingIndex, closingIndex-1));
        }
    }

    void decisionTree(String data) {
        String[] statements = data.split(";");
        Node prev = new Node("start", 0);
        Node start = prev;

        Stack<Node> ifStack = new Stack<Node>();
        Stack<Node> elseStack = new Stack<Node>();

        Stack<Node> waitStack = new Stack<Node>();

        int counter = 1;
        for (String stmt : statements) {
            stmt = stmt.trim();
            Node current = new Node("", 0);
            Pattern pattern = Pattern.compile("((if|while|for)\\s*\\(.*?\\))\\s*\\{");
            Boolean flag = false;

            while (true) {
                //System.out.println(stmt + ": " + counter);

                Matcher matcher = pattern.matcher(stmt);
                if (matcher.find()) {
                    Node temp = new Node(matcher.group(1), counter);
                    counter++;
                    ifStack.push(temp);
                    prev.next.add(ifStack.peek());
                    stmt = matcher.replaceAll("");
                    prev = ifStack.peek();
                    flag = false;

                } else if (!elseStack.empty() && (stmt.indexOf("}") != -1)) {
                    stmt = stmt.substring(stmt.indexOf("}") + 1);
                    waitStack.push(elseStack.peek());
                    elseStack.pop();
                    flag = false;
                    //counter++;
                } else if (!ifStack.empty() && stmt.indexOf("}") != -1) {
                    stmt = stmt.substring(stmt.indexOf("}") + 1);

                    Pattern elsePattern = Pattern.compile("\\s*else\\s*\\{");
                    Matcher elseMatcher = elsePattern.matcher(stmt);

                    if (elseMatcher.find()) {
                        elseStack.push(prev);
                        stmt = elseMatcher.replaceAll("");
                        flag = true;
                    } else if (ifStack.peek().label.indexOf("while") != -1 || ifStack.peek().label.indexOf("for") != -1) {
                        prev.next.add(ifStack.peek());
                        flag = true;
                    } else {
                        flag = false;
                    }
                    //counter++;
                    waitStack.push(ifStack.peek());
                    ifStack.pop();

                } else {
                    current = new Node(stmt, counter);
                    counter++;
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
        prev.next.add(new Node("end", counter));
        int maxCounter = counter;
        generateGraph(start, maxCounter);
    }

    void generateGraph(Node current, int maxCounter) {
        HashMap<Integer, ArrayList<Integer>> matrix = new HashMap<Integer, ArrayList<Integer>>();

        HashMap<Node, Boolean> visited = new HashMap<Node, Boolean>();
        Queue<Node> q = new LinkedList<Node>();

        q.add(current);
        visited.put(current, true);

        while (!q.isEmpty()) {
            Node top = q.peek();
            System.out.print(top.label.trim() + " -> " + top.number);
            q.remove();
            //System.out.print(" -> ");
            ArrayList<Integer> neighbours = new ArrayList<Integer>();

            for (Node next : top.next) {
                if (visited.get(next) == null) {
                    q.add(next);
                    visited.put(next, true);
                }
                neighbours.add(next.number);
                //System.out.print(next.label.trim() + " > " + next.number + ", ");
            }
            System.out.println("");
            //System.out.println("-----------");
            matrix.put(top.number, neighbours);
        }
        //System.out.println(matrix);
        printGraph(matrix, maxCounter);
    }

    void printGraph(HashMap<Integer, ArrayList<Integer>> matrix, int maxCounter)
    {
        System.out.print("    ");
        for (int i = 1; i < maxCounter; i++) {
            System.out.printf("%2d ", i);
        }
        System.out.println("");
        for (int i = 1; i < maxCounter; i++) {
            System.out.printf("%2d" + ": ", i);
            for (int j = 1; j < maxCounter; j++) {
                if (matrix.get(i).indexOf(j) == -1) {
                    System.out.printf("%2d ", 0);
                } else {
                    System.out.printf("%2d ", 1);
                }
            }
            System.out.println("");
        }
    }


    public static void main(String[] args) {
        new Main().run();
    }
}
