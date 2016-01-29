package pt.taskManagement;

public class TodoManager extends Manager {

    LinkedTaskDone done;

    // name, notes != null && !name.equals("")
    // Adds a Task to the end of the list
    public void add(String name, String notes) {
        TodoTask temp = new TodoTask(name, notes, 0);
        super.add(temp);
    }

    // Adds another pomodoro when timer runs out
    public void mark() {
        head.setPomodoros(head.getPomodoros() + 1);
    }

    // Finish the task
    public void finish() {
        if(head == null) {
            return;
        }
        if (done == null) {
            done = new LinkedTaskDone((TodoTask) head);
        } else
            done.setNext(head);

        super.remove(1);
    }
}
