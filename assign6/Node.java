import java.util.*;

public class Node {
    String label;
    ArrayList<Node> next = new ArrayList<Node>();

    public Node(String title) {
        label = title;
    }
}
