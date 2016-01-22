package pt;

class TaskListState {

    private Node head;
    private boolean changed;

    public TaskListState() {
        head = Node.empty();
    }

    public void saveToHistory() {
        Node toAdd = new Node(head.getFinished(), head.getUnfinished());

        if (head == null) {
            head = new Node(toAdd);
        } else {
            toAdd.setNext(head);
            head = toAdd;
        }
    }

    void add(Task t) {
        saveToHistory();
        if (t.isFinished())
            head.getFinished().add(t);
        else
            head.getUnfinished().add(t);

        changed = true;
    }

    void add(String name, String notes) {
        add(new Task(head.getUnfinished().size() + 1, name, notes));
    }

    public void remove(int id) {
        saveToHistory();
        for (int i = 0; i < head.getUnfinished().size(); i++) {
            Task t = head.getUnfinished().get(i);
            if (t.getId() == id) {
                head.getUnfinished().remove(i);
                if (i < head.getUnfinished().size()) {
                    head.getUnfinished().get(i).setId(head.getUnfinished().get(i).getId() - 1);
                }
            }
        }
        changed = true;
    }

    public void removeAllTasks() {
        saveToHistory();
        this.changed = true;
        head.getUnfinished().clear();
        head.getFinished().clear();
    }

    public void switchPos(int i, int i1) {
        saveToHistory();
        int index1 = i - 1;
        int index2 = i1 - 1;

        Task t1 = head.getUnfinished().get(index1);
        Task t2 = head.getUnfinished().get(index2);

        String tempName = t1.getName();
        t1.setName(t2.getName());
        t2.setName(tempName);
        this.changed = true;
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
}
