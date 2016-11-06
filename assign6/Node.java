import java.util.*;

public class Node {
    String label;
    int number;
    ArrayList<Node> next = new ArrayList<Node>();

    public Node(String title, int no) {
        label = title;
        number = no;
    }
}
