package pt.taskManagement;

import pt.Table;

public class TodoManager extends Manager {

    private LinkedTaskDone done;

    // name, notes != null && !name.equals("")
    // Adds a Task to the end of the list
    public void add(String name, String notes) {
        TodoTask temp = new TodoTask(name, notes, 0);
        super.add(temp);
    }

    // Adds a finished task
    public void addDone(LinkedTaskDone done) {
        if (this.done == null)
            this.done = done;
        else {
            LinkedTaskDone temp = this.done;
            while (temp.getNext() != null) {
                temp = (LinkedTaskDone) temp.getNext();
            }
            temp.setNext(done);
        }
        super.setChanged(false);
    }

    // Adds another pomodoro when timer runs out
    public void mark() {
        head.setPomodoros(head.getPomodoros() + 1);
       super.setChanged(false);
    }

    // Finish the task
    public void finish() {
        if (head == null) {
            return;
        }
        if (done == null) {
            done = new LinkedTaskDone((TodoTask) head);
        } else
            addDone((LinkedTaskDone) head);

        super.remove(1);
    }

    // Temporary
    public void displayTasks() {
        Table t = new Table();
        t.displayTasks((TodoTask) head, done);
    }

    public boolean isLongBreak() {
        int count = 0;

        Task temp = head;
        while (temp.getNext() != null) {
            count += temp.getPomodoros();
        }
        temp = done;
        while (temp.getNext() != null) {
            count += temp.getPomodoros();
        }

        return count % 4 == 0 && count > 0;
    }

    public LinkedTaskDone getDone() {
        return done;
    }
}
