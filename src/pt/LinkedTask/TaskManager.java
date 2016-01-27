package pt.LinkedTask;

public class TaskManager {
    LinkedTask head;
    LinkedTask tail;

    public TaskManager() {

    }

    // name, notes != null && !name.equals("")
    // Adds a Task to the end of the list
    public void add(String name, String notes) {
        LinkedTask task = new LinkedTask(name, notes);
        if(head == null) {
            head = task;
            tail = task;
        }
        else {
            tail.next = task;
            tail = task;
        }
    }
}
