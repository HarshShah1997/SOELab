import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

public class Alternate {

    int counter = 0;
    String[] statements;

    Pattern detectIf = Pattern.compile("if\\s*\\(.*?\\)");
    Pattern detectIfBracket = Pattern.compile("if\\s*\\(.*?\\)\\s*\\{");
    Pattern detectElse = Pattern.compile("\\}\\s*else");
    Pattern detectElseBracket = Pattern.compile("\\}\\s*else\\s*\\{");
    Pattern detectBracket = Pattern.compile("\\}");

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
            Matcher ifmatcher = detectIf.matcher(stmt);
            if (ifmatcher.find()) {
                Node current = new Node(ifmatcher.group(0));
                prev.next.add(current);
                solve(current);
            } else {
                Node current = new Node(stmt);
                prev.next.add(current);
                prev = current;
                counter++;
            }
        }   
        printGraph(start);
    }

    Node solve(Node start) {
        Node prev = start;
        String stmt = statements[counter].trim();
        String lastStmt = "";
        while (true) {
            Matcher detectIfBracketMatcher = detectIfBracket.matcher(stmt);
            Matcher detectElseBracketMatcher = detectElseBracket.matcher(stmt);

            if (detectElseBracketMatcher.find()) {

            
            if (detectIfBracketMatcher.find()) {
                stmt = detectIfBracketMatcher.replaceAll("");
                //statements[counter] = stmt;
                //Detect if again, recursion
                /*Matcher ifmatcher = detectIf.matcher(stmt);
                  if (ifmatcher.find()) {
                  Node current = new Node(ifmatcher.group(0));
                  start.next.add(current);
                  solve(current);
                  } else */
                Node current = new Node(stmt);
                prev.next.add(current);
            }
        }
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
        new Alternate().run();
    }
}
