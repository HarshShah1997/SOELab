import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Main {

    void run() {
        String inputData = Helper.getInputFromFile("input.c");
        inputData = Helper.removeQuotes(inputData);
        inputData = Helper.removeSinglelineComments(inputData);
        inputData = Helper.removeMultilineComments(inputData);

        cyclo(inputData);
    }

    void cyclo(String inputData) {
        String pattern = "(void|int|float|char|double)\\s+(.*)\\(.*?\\)\\s*\\{";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(inputData);
        while (m.find()) {
            System.out.println(inputData.charAt(m.end() - 1));
            int openingIndex = m.end();
            int closingIndex = findMatching(inputData, openingIndex);
            System.out.println(inputData.charAt(closingIndex));
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
