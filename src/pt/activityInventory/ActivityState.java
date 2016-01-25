package pt.activityInventory;

import pt.TaskListState;

class ActivityState {

    private Node head;
    private TaskListState state;
    private boolean changed;

    public ActivityState(TaskListState state) {
        head = Node.empty();
        this.state = state;
    }

    public void saveToHistory() {
        Node toAdd = new Node(head.getActTasks());

        if (head == null) {
            head = new Node(toAdd);
        } else {
            toAdd.setNext(head);
            head = toAdd;
        }
    }

    void add(ActivityTask t) {
        saveToHistory();

        head.getActTasks().add(t);
        changed = true;
    }

    void add(ActivityUnplanned unp, String name, int effort) {
        add(new ActivityTask(getNextId(), unp, name, effort));
    }

    private int getNextId() {
        return head.getActTasks().size()+1;
    }

    public void remove(int id) {
        saveToHistory();
        for (int i = 0; i < head.getActTasks().size(); i++) {
            ActivityTask t = head.getActTasks().get(i);
            if (t.getId() == id) {
                head.getActTasks().remove(i);
                if (i < head.getActTasks().size()) {
                    head.getActTasks().get(i).setId(head.getActTasks().get(i).getId() - 1);
                }
            }
        }
        changed = true;
    }

    public void removeAllTasks() {
        saveToHistory();
        this.changed = true;
        head.getActTasks().clear();
    }

    public void switchPos(int i, int i1) {
        saveToHistory();
        int index1 = i - 1;
        int index2 = i1 - 1;

        ActivityTask t1 = head.getActTasks().get(index1);
        ActivityTask t2 = head.getActTasks().get(index2);

        switchTasks(t1, t2);
        this.changed = true;
    }

    private void switchTasks(ActivityTask t1, ActivityTask t2) {
        ActivityTask temp = new ActivityTask(t1);
        t1.to(t2);
        t2.to(temp);
    }

    public void displayCurrentTasks() {
        head.displayTasks();
    }

    public void displayHistory() {
        Node temp = new Node(head);

        while (temp != null) {
            temp.displayTasks();
            temp = temp.getNext();
            System.out.println();
        }
    }

    public void importToPt() {
        for(ActivityTask t : head.getActTasks()) {
            state.add(t.getName(), t.getInfo(), t.getEffort());
        }
    }

    public void undoChanges() {
        head = head.getNext();
    }

    public boolean isChanged() {
        return changed;
    }

    public Node getCurrentState() {
        return head;
    }

    public void change() {
        changed = true;
    }

    public void saved() {
        changed = false;
    }

}
