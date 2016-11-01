import java.util.*;

public class Activity {
    String name;
    int startTime;
    ArrayList<Activity> predecessor;
    int duration;
    int completionTime;
    boolean isCritical;

    public Activity(String activityName, ArrayList<Activity> pre, int dur) {
        name = activityName;
        predecessor = pre;
        duration = dur;
    }

    /*public int compareTo(Activity b) {
        return this.startTime - (b.startTime);
    }*/
}

