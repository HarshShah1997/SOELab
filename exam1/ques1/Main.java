import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

public class Main {
    
    ArrayList<String> functions = new ArrayList<String>();
    
    void run() {
        String inputData = getInput();
        inputData = removeQuotes(inputData);
        detectFunctions(inputData);
        displayFunctions();
        String[] splitInput = inputData.split("\n");
        detectAndDisplayCalled(splitInput);
    }
    
    void detectFunctions(String inputData) {

        String argument = "((int|float|char|double)(\\s)+(\\w)+)";

        String function = "(int|float|char|double|void)\\s+(\\w+)\\s*\\((\\s)*" + "(" + argument + "(\\s)*" + "(,(\\s)*" + argument + "(\\s)*)*)*\\)";

        Pattern pattern = Pattern.compile(function);
        Matcher m = pattern.matcher(inputData);
        while (m.find()) {
            functions.add(m.group(2));
            //System.out.println(m.group(2));
        }
    }
    
    String checkFunction(String inputData) {
        
        String argument = "((int|float|char|double)(\\s)+(\\w)+)";

        String function = "(int|float|char|double|void)\\s+(\\w+)\\s*\\((\\s)*" + "(" + argument + "(\\s)*" + "(,(\\s)*" + argument + "(\\s)*)*)*\\)";

        Pattern pattern = Pattern.compile(function);
        Matcher m = pattern.matcher(inputData);
        if (m.find()) {
            return m.group(2);
        } else {
            return "";
        }
    }
        
    
    void detectAndDisplayCalled(String[] splitInput) {
        String calledFunc = "";
        String format = "%-10s%-10s%-10s%n";
        System.out.printf(format, "Calling", "Called", "Line Number");
        System.out.printf(format, "function", "function", "");
        for (int i = 0; i < splitInput.length; i++) {
            String checkfn = checkFunction(splitInput[i]);
            if (!checkfn.equals("")) {
                calledFunc = checkfn;
                continue;
            }
            String pattern = "(.*?)(\\w+)\\(.*?\\)";
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(splitInput[i]);
            while (m.find()) {
                if (functions.contains(m.group(2))) {
                    System.out.printf(format, calledFunc, m.group(2), "" + (i+1));
                }
            }
        }
    }       
    
    void displayFunctions() {
        System.out.println("Functions:");
        for (String func : functions) {
            System.out.println(func + "()");
        }
        System.out.println("");
    }
    
    String removeQuotes(String inputData) {
        String pattern = "\".*?\"";
        Pattern p = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher m = p.matcher(inputData);
        inputData = m.replaceAll("");
        return inputData;
    }
    
    String getInput() {
        String inputData = "";
        try {
            FileReader in = new FileReader("input.c");
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
        
    public static void main(String[] args) {
        new Main().run();
    }
}

