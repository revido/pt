package proj;

import java.util.ArrayList;

class TaskListState {
    private final ArrayList<Task> finishedTasks;
    private final ArrayList<Task> unfinishedTasks;

    public TaskListState next;

    public TaskListState(ArrayList<Task> finishedTasks, ArrayList<Task> unfinishedTasks) {
        this.finishedTasks = finishedTasks;
        this.unfinishedTasks = unfinishedTasks;
        this.next = null;
    }

    ArrayList<Task> getFinished() {
        return this.finishedTasks;
    }

    ArrayList<Task> getUnfinished() {
        return this.unfinishedTasks;
    }
}
