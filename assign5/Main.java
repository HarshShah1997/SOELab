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
            System.out.println(m.end());
        }
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
