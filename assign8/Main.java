import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Main {
    
    ArrayList<Activity> table;

    void run() {
        String inputData = Helper.getInputFromFile("input.txt");
        createTable(inputData);
        //System.out.println(table);
        calculate();
        printTable();
    }

    void calculate() {
        for (Activity activity : table) {
            if (activity.predecessor.size() == 0) {
                activity.startTime = 0;
                activity.completionTime = activity.duration;
            } else {
                activity.startTime = Collections.max(activity.predecessor);
                activity.completionTime = activity.startTime + activity.duration;
            }
        }
    }

    void createTable(String data) {
        table = new ArrayList<Activity>();

        String[] rows = data.split("\n");
        for (int i = 1; i < rows.length; i++) {
            //System.out.println(rows[i] + "|");
            table.add(createActivity(rows[i]));
        }
    }

    Activity createActivity(String line) {
        String[] details = line.split("\\s\\s+");
        String activityName = details[0];
        ArrayList<Activity> predecessor = findPredecessor(details[1]);
        int duration = Integer.parseInt(details[2]);
        return new Activity(activityName, predecessor, duration);
    }

    ArrayList<Activity> findPredecessor(String data) {
        ArrayList<Activity> predecessor = new ArrayList<Activity>();
        if (!data.equals("-")) {
            String[] names = data.split(",");
            for (String name : names) {
                predecessor.add(search(name));
            }
        }
        return predecessor;
    }

    Activity search(String name) {
        name = name.trim();
        System.out.println("> " + name);
        for (int i = 0; i < table.size(); i++) {
            if (table.get(i).name.equals(name)) {
                return table.get(i);
            }
        }
        return null;
    }

    void printTable() {
        for (Activity activity : table) {
            System.out.println(activity.name + " " + activity.startTime + " " + activity.completionTime);
        }
    }
            
    public static void main(String[] args) {
        new Main().run();
    }
}


