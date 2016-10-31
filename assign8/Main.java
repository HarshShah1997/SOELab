import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Main {
    
    ArrayList<Activity> table;

    void run() {
        String inputData = Helper.getInputFromFile("input.txt");
        createTable(inputData);
        System.out.println(table);
    }

    void createTable(String data) {
        table = new ArrayList<Activity>();
        String[] rows = data.split("\n");
        for (int i = 1; i < rows.length; i++) {
            System.out.println(rows[i]);
            table.add(createActivity(rows[i]));
        }
    }

    Activity createActivity(String line) {
        String[] details = line.split(" ");
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
        for (int i = 0; i < table.size(); i++) {
            if (table.get(i).name.equals(name)) {
                return table.get(i);
            }
        }
        return null;
    }
            
    public static void main(String[] args) {
        new Main().run();
    }
}


