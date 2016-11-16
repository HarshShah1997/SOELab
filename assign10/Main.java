import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.*;

public class Main {

    void run() {
        String inputData = Helper.getInputFromFile("input.c");
        inputData = Helper.removeQuotes(inputData);
        inputData = Helper.removeSinglelineComments(inputData);
        inputData = Helper.removeMultilineComments(inputData);
        detectFunctions(inputData);
    }

    void detectFunctions(String inputData) {
        Pattern detectFunction = Pattern.compile("(float|int|void|double|char)\\s+(\\w+)\\s*\\(.*?\\)\\s*\\{");
        Matcher matcher = detectFunction.matcher(inputData);
        while (matcher.find()) {
            int openingIndex = matcher.end();
            int closingIndex = Helper.findMatching(inputData, openingIndex);
            System.out.println(matcher.group(2));
            //decisionTree(inputData.substring(openingIndex, closingIndex-1));
        }
    }



    public static void main(String[] args) {
        new Main().run();
    }
}


