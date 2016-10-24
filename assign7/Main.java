import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Main {

    void run() {
        String inputData = Helper.getInputFromFile("input.java");
        inputData = Helper.removeQuotes(inputData);
        inputData = Helper.removeSinglelineComments(inputData);
        inputData = Helper.removeMultilineComments(inputData);
        findClass(inputData);
    }

    void findClass(String data) {
        Pattern detectClass = Pattern.compile("class\\s+(\\w+)\\s*\\{");
        Matcher matcher = detectClass.matcher(data);
        while (matcher.find()) {
            int openingIndex = matcher.end();
            int closingIndex = Helper.findMatching(data, openingIndex);
            System.out.println(matcher.group(1));
            findCohesion(data.substring(openingIndex, closingIndex));
        }
    }

    void findCohesion(String data) {
        ArrayList<ArrayList<String>> variablesInMethods = new ArrayList<ArrayList<String>>();
        ArrayList<String> methods = new ArrayList<String>();
        Pattern detectMethod = Pattern.compile("\\w+\\s+(\\w+\\(.*?\\))\\s*\\{");
        ArrayList<String> attributes = new ArrayList<String>();
        Matcher matcher = detectMethod.matcher(data);
        boolean found = false;
        System.out.println("Methods:");
        while (matcher.find()) {
            if (!found) {
                attributes = findVariables(data.substring(0, matcher.start()));
                found = true;
            }
            int openingIndex = matcher.end();
            int closingIndex = Helper.findMatching(data, openingIndex);
            System.out.println(matcher.group(1));
            variablesInMethods.add(findVariables(data.substring(openingIndex, closingIndex)));
            methods.add(matcher.group(1));
            //System.out.println(data.charAt(matcher.start()));
        }
        //System.out.println(attributes);
        printCohesion(attributes, variablesInMethods, methods);
    }

    void printCohesion(ArrayList<String> attributes, ArrayList<ArrayList<String>> variablesInMethods, ArrayList<String> methods) {

        int p = 0;
        int q = 0;

        for (int i = 0; i < methods.size() - 1; i++) {
            for (int j = i+1; j < methods.size(); j++) {
                ArrayList<String> com1 = new ArrayList<String>(variablesInMethods.get(i));
                com1.retainAll(variablesInMethods.get(j));
                com1.retainAll(attributes);
                //System.out.println(methods.get(i) + " " + methods.get(j) + " " + com1);
                if (com1.size() > 0) {
                    q++;
                } else {
                    p++;
                }
            }
        }
        System.out.println("P: "+ p + " Q: " + q);
        int lcom = (p > q) ? (p - q) : 0;
        System.out.println("LCOM: " + lcom);
        System.out.println("");

    }

    ArrayList<String> findVariables(String data) {
        Pattern detectVariables = Pattern.compile("\\b[A-Za-z]\\w*\\b(?!\\s*\\()");
        Matcher matcher = detectVariables.matcher(data);
        ArrayList<String> variables = new ArrayList<String>();
        while (matcher.find()) {
            //System.out.println(matcher.group(0));
            if (filter(matcher.group(0))) {
                variables.add(matcher.group(0));
            }
        }
        return variables;
    }

    boolean filter(String name) {
        Pattern keyword = Pattern.compile("(int|float|void|double|public|private|protected|boolean)");
        Matcher m = keyword.matcher(name);
        if (m.find()) {
            return false;
        } else {
            return true;
        }
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
