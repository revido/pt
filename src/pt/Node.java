package pt;

import java.util.ArrayList;

class Node {
    private final ArrayList<Task> finishedTasks;
    private final ArrayList<Task> unfinishedTasks;

    private Node next;

    public Node(ArrayList<Task> finishedTasks, ArrayList<Task> unfinishedTasks) {
        setNext(null);
        this.finishedTasks = finishedTasks;
        this.unfinishedTasks = unfinishedTasks;
    }

    public Node(Node toAdd) {
        this.finishedTasks = toAdd.getFinished();
        this.unfinishedTasks = toAdd.getUnfinished();
        next = toAdd.getNext();
    }

    public void displayTasks() {
        System.out.format("+------+----+----------------------------------+---------------+---------------------------+%n");
        System.out.format("| Done | ID |               Name               |     Marks     |           Notes           |%n");
        System.out.format("+------+----+----------------------------------+---------------+---------------------------+%n");
        displayUnfinishedTasks();
        System.out.format("+------+----+----------------------------------+---------------+---------------------------+%n");
        displayFinishedTasks();
        System.out.format("+------+----+----------------------------------+---------------+---------------------------+%n");
    }

    private void displayFinishedTasks() {
        String leftAlignFormat = "| %4s | %2s | %-32s | %-13s | %-25s |%n";
        for (Task t : finishedTasks) {
            String done = (t.isFinished()) ? "DONE" : "";
            String id = t.getId() == -1 ? "" : Integer.toString(t.getId());
            System.out.format(leftAlignFormat, done, id, t.getName(), returnMarks(t.getMarks()), t.getNotes());
        }
    }

    private void displayUnfinishedTasks() {
        String leftAlignFormat = "| %4s | %2s | %-32s | %-13s | %-25s |%n";
        for (Task t : unfinishedTasks) {
            String done = (t.isFinished()) ? "DONE" : "";
            String id = t.getId() == -1 ? "" : Integer.toString(t.getId());
            System.out.format(leftAlignFormat, done, id, t.getName(), returnMarks(t.getMarks()), t.getNotes());
        }

    }

    private String returnMarks(int marks) {
        String mark = "";
        for (int i = 0; i < marks; i++) {
            mark += "X ";
        }
        return mark;
    }

    ArrayList<Task> getFinished() {
        return finishedTasks;
    }

    ArrayList<Task> getUnfinished() {
        return unfinishedTasks;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public static Node empty() {
        return new Node(new ArrayList<>(), new ArrayList<>());
    }
}
