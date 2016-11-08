import java.util.*;

public class Main {

    void run() {
        String inputData = Helper.getInputFromFile("input.txt");
        ArrayList<Condition> conditions = findConditions(inputData);
        printOutput(conditions);
    }

    ArrayList<Condition> findConditions(String data) {
        ArrayList<Condition> conditions = new ArrayList<Condition>();

        String lines[] = data.split("\n");
        for (int i = 1; i < lines.length; i++) {
            if (lines[i].trim().equals("Actions")) {
                continue;
            }
            //System.out.println(lines[i]);
            conditions.add(createCondition(lines[i]));
        }
        return conditions;
    }

    Condition createCondition(String data) {
        String[] values = data.split("\\s+");
        String conditionName = values[0];
        ArrayList<String> rules = new ArrayList<String>();
        for (int i = 1; i < values.length; i++) {
            rules.add(values[i]);
        }
        return new Condition(conditionName, rules);
    }

    void printOutput(ArrayList<Condition> conditions) {
        for (int i = 0; i < conditions.get(0).rules.size(); i++) {
            for (int j = 0; j < conditions.size(); j++) {
                System.out.print(conditions.get(j).rules.get(i) + " " + conditions.get(j).conditionName);
                if (j != conditions.size() - 1) {
                    System.out.print(" -> ");
                }
            }
            System.out.println("");
        }
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
