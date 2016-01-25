package pt.activityInventory;

import java.util.ArrayList;

class Node {
    private final ArrayList<ActivityTask> actTasks;

    private Node next;

    public Node(ArrayList<ActivityTask> actTasks) {
        setNext(null);
        this.actTasks = actTasks;
    }

    public Node(Node toAdd) {
        this.actTasks = toAdd.getActTasks();
        next = toAdd.getNext();
    }

    public static Node empty() {
        return new Node( new ArrayList<>());
    }

    public void displayTasks() {
        System.out.format("+----+---------------+----------------------------------+--------+%n");
        System.out.format("| ID | Info/Deadline |               Name               | Effort |%n");
        System.out.format("+----+---------------+----------------------------------+--------+%n");
        displayTasks(actTasks);
        System.out.format("+----+---------------+----------------------------------+--------+%n");
    }

    private void displayTasks(ArrayList<ActivityTask> list) {
        String leftAlignFormat = "| %-2s | %-13s | %-32s | %-6s |%n";
        for (ActivityTask t : list) {
            System.out.format(leftAlignFormat, t.getId() , t.getInfo(), t.getName(), t.getEffort());
        }
    }

    ArrayList<ActivityTask> getActTasks() {
        return actTasks;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
