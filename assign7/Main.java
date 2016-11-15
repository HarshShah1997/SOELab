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
        Pattern detectMethod = Pattern.compile("(void|float|double|int|boolean)\\s+((\\w+)\\s*\\((.*?)\\))\\s*\\{");
        ArrayList<String> attributes = new ArrayList<String>();
        Matcher matcher = detectMethod.matcher(data);
        boolean found = false;
        System.out.println("Methods:");
        while (matcher.find()) {
            if (!found) {
                attributes = findVariables(data.substring(0, matcher.start()), true);
                found = true;
            }
            int openingIndex = matcher.end();
            int closingIndex = Helper.findMatching(data, openingIndex);
            System.out.print(matcher.group(3));
            System.out.print(" Arguments: " + matcher.group(4));
            variablesInMethods.add(findVariables(data.substring(openingIndex, closingIndex)));
            System.out.println(" Methods: " + findMethodsInvoked(data.substring(openingIndex, closingIndex)));
            methods.add(matcher.group(2));
            //System.out.println(data.charAt(matcher.start()));
        }
        //System.out.println(attributes);
        printCohesion(attributes, variablesInMethods, methods);
        printLcom2(attributes, variablesInMethods, methods);
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

    }

    void printLcom2(ArrayList<String> attributes, ArrayList<ArrayList<String>> variablesInMethods, ArrayList<String> methods) {
        int m = methods.size();
        int a = attributes.size();

        int summx = 0;
        for (String attribute : attributes) {
            //System.out.print(attribute + " : ");
            int mx = 0;
            for (int i = 0; i < methods.size(); i++) {
                if (variablesInMethods.get(i).indexOf(attribute) != -1) {
                    //System.out.print(methods.get(i) + " ");
                    mx++;
                }
            }
            //System.out.println("");
            summx += mx;
        }
        //System.out.println("Sum mx " + summx + " m " + m + " a " + a);
        float lcom2 = 1 - ((float)summx / (m * a));
        float lcom3 = (m - ((float)summx / a)) / (m -1);

        System.out.println("LCOM2: " + lcom2);
        System.out.println("LCOM3: " + lcom3);
        System.out.println("");
    }

    ArrayList<String> findVariables(String data) {
        return findVariables(data, false);
    }

    ArrayList<String> findVariables(String data, boolean inMethod) {
        if (!inMethod) {
            data = removeDeclarations(data);
        }

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

    ArrayList<String> findMethodsInvoked(String data) {
        Pattern detectMethodsInvoked = Pattern.compile("\\b([A-Za-z]\\w*)\\b\\s*\\(");
        Matcher matcher = detectMethodsInvoked.matcher(data);
        ArrayList<String> methodsInvoked = new ArrayList<String>();
        while (matcher.find()) {
            //System.out.println("> " + matcher.group(1));
            methodsInvoked.add(matcher.group(1));
        }
        return methodsInvoked;
    }

    String removeDeclarations(String data) {
        Pattern removeLocal = Pattern.compile("(int|char|float|double|boolean)\\s+\\w+");
        Matcher removeLocalMatcher = removeLocal.matcher(data);
        while(removeLocalMatcher.find()) {
            //System.out.println("> " + removeLocalMatcher.group(0));
        }
        data = removeLocalMatcher.replaceAll("");
        return data;
    }

    boolean filter(String name) {
        Pattern keyword = Pattern.compile("(int|float|void|double|public|private|protected|boolean|if|while|for|switch|static|final)");
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
