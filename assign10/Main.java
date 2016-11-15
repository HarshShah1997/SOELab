import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.*;

public class Main {

    int uniqOperators = 0;
    int uniqOperands = 0;

    int totalOperators = 0;
    int totalOperands = 0;

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
            
            System.out.println("Operators");
            String data = findOperators(inputData.substring(openingIndex, closingIndex-1));
            System.out.println("Operands");
            findOperands(data);

            System.out.println("Operators: N1=" + totalOperators + " n1=" + uniqOperators);
            System.out.println("Operands: N2=" + totalOperands + " n2=" + uniqOperands);
        }
    }

    String findOperators(String data) {
        String[] comparisons = {"<", ">", "=="};
        String[] brackets = {"(", ")", "}", "{", "["};
        String[] arithmetic = {"++", "--", "+", "-", "/", "*", "%"};
        String[] conditions = {"for", "while", "if", "switch", "case", "else", "default"};
        String[] datatypes = {"void", "char", "int", "float", "double"};
        String[] jumps = {"return", "break", "continue"};
        String[] misc = {",", ";", "=(?!=)"};

        data = findComparisons(data, comparisons);
        data = findBrackets(data, brackets);
        data = findArithmetic(data, arithmetic);
        data = findKeywords(data, conditions);
        data = findKeywords(data, datatypes);
        data = findKeywords(data, jumps);
        data = findMisc(data, misc);
        return data;

    }

    void findOperands(String data) {
        HashMap<String, Integer> operands = new HashMap<String, Integer>();

        Pattern pattern = Pattern.compile("\\b\\w+\\b");
        Matcher m = pattern.matcher(data);
        while (m.find()) {
            String operand = m.group(0);
            if (operands.get(operand) == null) {
                uniqOperands++;
                operands.put(operand, 1);
            } else {
                operands.put(operand, operands.get(operand) + 1);
            }
        }
        for (String operand : operands.keySet()) {
            totalOperands += operands.get(operand);
            System.out.println(operand + " " + operands.get(operand));
        }
    }

    String findComparisons(String data, String[] comparisons) {
        for (String op : comparisons) {
            Pattern pattern = Pattern.compile(op);
            Matcher m = pattern.matcher(data);
            int count = 0;
            while (m.find()) {
                count++;
            }
            if (count != 0) {
                uniqOperators++;
                totalOperators += count;
                System.out.println(op + " " + count);
            }
            data = m.replaceAll(" ");
        }
        return data;
    }

    String findBrackets(String data, String[] brackets) {
        for (String op : brackets) {
            Pattern pattern = Pattern.compile(Pattern.quote(op));
            Matcher m = pattern.matcher(data);
            int count = 0;
            while (m.find()) {
                count++;
            }
            if (count != 0) {
                uniqOperators++;
                totalOperators += count;
                if (op.equals("[")) { 
                    System.out.println(op + getClosing(op) + " " + count);
                } else {
                    System.out.println(op + " " + count);
                }
            }
            data = m.replaceAll(" ");
        }
        return data;
    }

    String findArithmetic(String data, String[] arithmetic) {
        for (String op : arithmetic) {
            Pattern pattern;
            if (op.equals("+") || op.equals("-")) {
                pattern = Pattern.compile(Pattern.quote(op) + "(?!" + Pattern.quote(op) + ")");
            } else {
                pattern = Pattern.compile(Pattern.quote(op));
            }
            int count = 0;
            Matcher m = pattern.matcher(data);
            while (m.find()) {
                count++;
            }
            if (count != 0) {
                uniqOperators++;
                totalOperators += count;
                System.out.println(op + " " + count);
            }
            data = m.replaceAll(" ");
        }
        return data;
    }

    String findKeywords(String data, String[] keywords) {
        for (String op : keywords) {
            Pattern pattern = Pattern.compile("\\b"+Pattern.quote(op)+"\\b");
            Matcher m = pattern.matcher(data);
            int count = 0;
            while (m.find()) {
                count++;
            }
            if (count != 0) {
                uniqOperators++;
                totalOperators += count;
                System.out.println(op + " " + count);
            }
            data = m.replaceAll(" ");
        }
        return data;
    }

    String findMisc(String data, String[] misc) {
        for (String op : misc) {
            Pattern pattern = Pattern.compile(op);
            Matcher m = pattern.matcher(data);
            int count = 0;
            while (m.find()) {
                count++;
            }
            if (count != 0) {
                uniqOperators++;
                totalOperators += count;
                if (op.equals("=(?!=)")) {
                    System.out.println("= " + count);
                } else {
                    System.out.println(op + " " + count);
                }
            }
            data = m.replaceAll(" ");
        }
        return data;
    }

    String getClosing(String opening) {
        if (opening.equals("(")) {
            return ")";
        } else if (opening.equals("[")) {
            return "]";
        } else if (opening.equals("{")) {
            return "}";
        } else {
            return "ERROR";
        }
    }
         
    public static void main(String[] args) {
        new Main().run();
    }
}


