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
        String pattern = "(void|int|float|char|double)\\s+(.*)\\(.*?\\)\\s*\\{";
        Pattern functions = Pattern.compile(pattern);
        Matcher m = functions.matcher(inputData);
        while (m.find()) {
            int openingIndex = m.end();
            int closingIndex = findMatching(inputData, openingIndex);
            controlFlowGraph(inputData.substring(openingIndex, closingIndex));
        }
    }

    void controlFlowGraph(String data) {
        findDecisionPoints(data, 0);
    }

    void findDecisionPoints(String data, int count) {
        String pattern = "(if|while|for)\\s*\\(.*?\\)\\s*\\{";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(data);
        if (m.find()) {
            int openingIndex = m.end();
            int closingIndex = findMatching(data, openingIndex);
            for (int i = 0; i < count; i++) {
                System.out.print("\t");
            }
            System.out.println(m.group(0));
            //System.out.println(">> " + data.substring(openingIndex, closingIndex));
            findDecisionPoints(data.substring(openingIndex, closingIndex), count + 1);

            String elsepatternstring = "else\\s*{";
            Pattern elsepattern = Pattern.compile(elsepatternstring);
            Matcher elsematcher = elsepattern.matcher(data.substring(closingIndex, data.length()));
            if (elsematcher.find()) {
                int elseopen = elsematcher.end();
                int elseclose = findMatching(data, elseopen);
                System.out.println(



            //System.out.println("<");
            //for (int i = 0; i < count; i++) {
            //    System.out.print("\t");
            //}
            //System.out.println("}");
            findDecisionPoints(data.substring(closingIndex, data.length()), count);
        } else {  
        //for (int i = 0; i < count; i++) {
        //    System.out.print("\t");
        //}
            System.out.println(data);
        }
        
    }

    int findMatching(String inputData, int openingIndex) {
        int i = openingIndex;
        int count = 1;
        int matchingIndex = -1;
        while (true) {
            if (inputData.charAt(i) == '{') {
                count++;
            } else if (inputData.charAt(i) == '}') {
                count--;
            }
            if (count == 0) {
                matchingIndex = i;
                break;
            }
            i++;
        }
        return matchingIndex;
    }

    public static void main(String[] args) {
        new Main().run();
    }
}

