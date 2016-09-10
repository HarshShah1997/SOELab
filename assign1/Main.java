
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

class Main {

    String[] datatypes = {"int", "char", "float", "double"};
    String[] loops = {"for", "while"};

    PrintWriter out;

    void start() {
        String inputData = getInput();
        try {
            out = new PrintWriter("iit2014071.txt");
            System.out.println(inputData);
            inputData = removeQuotes(inputData);
            inputData = removeSinglelineComments(inputData);
            inputData = removeMultilineComments(inputData);                        
            System.out.println(inputData);

            detectVariables(inputData);
            detectFunctions(inputData);
            detectLoops(inputData);
            detectConditions(inputData);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        out.close();

    }

    void detectVariables(String inputData) {

        String variable = "\\s*\\*?\\s*\\w+(\\s*=\\s*\\w+)?";

        for (String datatype : datatypes) {

            String pattern = datatype + "\\*?\\s+" + variable + "(\\s*,\\s*" + variable + ")*;";

            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(inputData);
            out.println("Variables of type: " + datatype);
            int initialized = 0;
            int total = 0;
            while (m.find()) {
                String matched = inputData.substring(m.start(), m.end());
                Pair returned = parse(matched);
                initialized += returned.first;
                total += returned.second;
                out.println(inputData.substring(m.start(), m.end()));
            }
            out.println("Total = " + total + ", Initialized = " + initialized);
            out.println("");
        }
    }

    void detectFunctions(String inputData) {
        out.println("Functions: ");

        String argument = "((int|float|char|double)(\\s)+(\\w)+)";

        String function = "(int|float|char|double|void)(\\s)+(\\w)+(\\s)*\\((\\s)*" + "(" + argument + "(\\s)*" + "(,(\\s)*" + argument + "(\\s)*)*)*\\)";

        Pattern pattern = Pattern.compile(function);
        Matcher m = pattern.matcher(inputData);
        while (m.find()) {
            out.println(inputData.substring(m.start(), m.end()));
        }
    }

    void detectLoops(String inputData) {
        out.println("");
        String pattern = "(while|for)(\\s)*\\(.*?\\)";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(inputData);

        int count = 0;
        while (m.find()) {
            count++;
        }
        out.println("Loops: " + count);
    }

    void detectConditions(String inputData) {
        out.println("");

        String pattern = "if(\\s)*\\(.*?\\)";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(inputData);

        int count = 0;
        while (m.find()) {
            count++;
        }
        out.println("If-Conditions: " + count);
    }
    
    String removeMultilineComments(String inputData) {
        String pattern = "/\\*.*?\\*/";
        Pattern p  = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher m = p.matcher(inputData);
        inputData = m.replaceAll("");
        return inputData;
    }
    
    String removeSinglelineComments(String inputData) {
        String pattern = "//.*?\\n";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(inputData);
        inputData = m.replaceAll("\n");
        return inputData;
    }
    
    String removeQuotes(String inputData) {
        String pattern = "\".*?\"";
        Pattern p = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher m = p.matcher(inputData);
        inputData = m.replaceAll("");
        return inputData;
    }

    Pair parse(String data) {
        int initialized = 0;
        int total = 1;

        for (int i = 0; i < data.length(); i++) {
            if (data.charAt(i) == '=') {
                initialized++;
            } else if (data.charAt(i) == ',') {
                total++;
            }
        }
        return new Pair(initialized, total);
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

    public static void main(String args[]) {
        new Main().start();
    }
}

class Pair {

    int first;
    int second;

    Pair(int first, int second) {
        this.first = first;
        this.second = second;
    }
}
