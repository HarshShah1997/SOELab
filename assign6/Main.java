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
        String patternStr = "(if|while)\\s*\\((.*?)\\)";

        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(data);

        if (matcher.find()) {
            System.out.println(matcher.group(2));
            int openingIndex = matcher.end();
            int closingIndex = Helper.findMatching(data, openingIndex);
            String inside = data.substring(
            System.out.println(data.substring(openingIndex, closingIndex));
        } else {
            System.out.println(data);
        }
    }

    public static void main(String[] args) {
        new Main().run();
    }
}

