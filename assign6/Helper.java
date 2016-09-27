
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

public class Helper {

    static String removeMultilineComments(String inputData) {
        String pattern = "/\\*.*?\\*/";
        Pattern p  = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher m = p.matcher(inputData);
        inputData = m.replaceAll("");
        return inputData;
    }
    
    static String removeSinglelineComments(String inputData) {
        String pattern = "//.*?\\n";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(inputData);
        inputData = m.replaceAll("\n");
        return inputData;
    }
    
    static String removeQuotes(String inputData) {
        String pattern = "\".*?\"";
        Pattern p = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher m = p.matcher(inputData);
        inputData = m.replaceAll("");
        return inputData;
    }

    static String getInputFromFile(String filename) {
        String inputData = "";
        try {
            FileReader in = new FileReader(filename);
            BufferedReader br = new BufferedReader(in);
            String input;
            while ((input = br.readLine()) != null) {
                inputData += input + "\n";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return inputData;
    }
}
