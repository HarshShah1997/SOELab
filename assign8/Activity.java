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
        pre = predecessor;
        duration = dur;
    }
}

