package pt.activityInventory;

import pt.ActivityTask;

import java.util.ArrayList;

class Node {
    private final ArrayList<ActivityTask> actTasks;

    private Node next;

    public Node(ArrayList<ActivityTask> actTasks) {
        setNext(null);
        this.actTasks = actTasks;
    }

    public Node(Node toAdd) {
        this.actTasks = toAdd.getUnfinished();
        next = toAdd.getNext();
    }

    public static Node empty() {
        return new Node( new ArrayList<>());
    }

    public void displayTasks() {
        System.out.format("+------+----+----------------------------------+---------------+---------------------------+%n");
        System.out.format("| Done | ID |               Name               |     Marks     |           Notes           |%n");
        System.out.format("+------+----+----------------------------------+---------------+---------------------------+%n");
        displayTasks(actTasks);
        System.out.format("+------+----+----------------------------------+---------------+---------------------------+%n");
        displayTasks(finishedTasks);
        System.out.format("+------+----+----------------------------------+---------------+---------------------------+%n");
    }

    private void displayTasks(ArrayList<ActivityTask> list) {
        String leftAlignFormat = "| %4s | %2s | %-32s | %-13s | %-25s |%n";
        for (ActivityTask t : list) {
            String done = (t.isDone()) ? "DONE" : "";
            String id = t.getId() == -1 ? "" : Integer.toString(t.getId());
            ArrayList<String> name = format(t.getName(), 32);
            ArrayList<String> pom = format(returnMarks(t.getPomodoros()), 13);
            ArrayList<String> notes = format(t.getNotes(), 25);
            displayMultiLine(done, id, name, pom, notes);
        }
    }

    private void displayMultiLine(String done, String id, ArrayList<String> name, ArrayList<String> pom, ArrayList<String> notes) {
        String leftAlignFormat = "| %4s | %2s | %-32s | %-13s | %-25s |%n";
        int maxLines = Math.max(Math.max(name.size(), pom.size()), notes.size());
        String nameS = " ", pomS = " ", notesS = " ";
        boolean setId = false;
        for (int i = 0; i < maxLines; i++) {
            if (name.size() > i) {
                nameS = name.get(i);
            }
            if (pom.size() > i) {
                pomS = pom.get(i);
            }
            if (notes.size() > i) {
                notesS = notes.get(i);
            }
            if (!setId) {
                //name too long
                System.out.format(leftAlignFormat, done, id, nameS, pomS, notesS);
                setId = true;
            }
            else {
                System.out.format(leftAlignFormat, done, " ", nameS, pomS, notesS);
            }
        }
    }
    /*
    size=2 > 0 t
    size=2 > 1 t
     */

    private ArrayList<String> format(String s, int len) {
        String[] words = s.split(" ");
        int charCounter = 0;
        String line = "";
        ArrayList<String> multiLine = new ArrayList<>();
        for (int i = 0; i < words.length; i++) {
            charCounter += words[i].length() + 1;
            if (charCounter > len) {
                multiLine.add(line);
                line = "";
                charCounter = 0;
            }
            line += words[i] + " ";
        }
        multiLine.add(line);
        return multiLine;
    }

    private String returnMarks(int marks) {
        String mark = "";
        for (int i = 0; i < marks; i++) {
            mark += "X ";
        }
        return mark;
    }

    ArrayList<ActivityTask> getFinished() {
        return finishedTasks;
    }

    ArrayList<ActivityTask> getUnfinished() {
        return actTasks;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
