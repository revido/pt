package pt.taskManagement;

public abstract class Manager {
    Task head;
    Task tail;

    private boolean changed;

    // t != null
    // Adds the task to the end of the list
    public void add(Task t) {
        if (head == null) {
            head = t;
            tail = t;
        } else {
            tail.setNext(t);
            tail = t;
        }
        changed = true;
    }

    // id > 0 && id <= last_id
    // Removes a task based on its ID (which is the priority)
    public void remove(int id) {
        int count = id;

        if (count == 1) {
            head = head.getNext();
        } else {

            Task temp = head;
            Task prev = head;
            while (count != 1) {
                prev = temp;
                temp = temp.getNext();
                count--;
            }

            prev.setNext(temp.getNext());
        }
        changed = true;
    }

    // Switch position of two tasks in the todo list
    public void switchPos(int id1, int id2) {
        if (id1 == id2) return;

        if (id1 > id2) {
            int save = id1;
            id1 = id2;
            id2 = save;
        }
        Task one, two;
        try {
            one = getTaskFromId(id1);
            two = getTaskFromId(id2);
        } catch (NullPointerException e) {
            System.err.println("ID does not exist.");
            return;
        }

        Task onenext = one.getNext();
        Task prevtwo = getTaskFromId(id2 - 1);

        if (one == head) {
            one.setNext(two.getNext());
            two.setNext(onenext);

            prevtwo.setNext(one);
            head = two;
        } else {
            Task prevone = getTaskFromId(id1 - 1);
            Task twonext = two.getNext();

            one.setNext(twonext);
            prevone.setNext(two);

            if (two != onenext) {
                two.setNext(onenext);
                prevtwo.setNext(one);
            } else {
                onenext.setNext(one);
            }

        }
        changed = true;
    }

    // 0 < id <= last_id && name != null && !name.equals("")
    // Changes the name of a task that is referenced by the given ID
    public void changeName(int id, String name) {
        try {
            getTaskFromId(id).setName(name);
        } catch (NullPointerException e) {
            System.err.println("ID does not exist.");
        }
        changed = true;
    }

    // 0 < id <= last_id && name != null && !name.equals("")
    // Changes the note-field of a task that is referenced by the given ID
    public void changeNote(int id, String note) {
        try {
            getTaskFromId(id).setNote(note);
        } catch (NullPointerException e) {
            System.err.println("ID does not exist.");
        }
        changed = true;
    }

    // 0 < id <= last_id
    // Returns the task from the given id
    protected Task getTaskFromId(int id) {
        int count = id;

        Task temp = head;
        while (count != 1) {
            temp = temp.getNext();
            if (temp == null) throw new NullPointerException();

            count--;
        }
        return temp;
    }

    public Task getTask() {
        return head;
    }

    @Override
    public String toString() {
        if (head != null)
            return head.toString();

        return "";
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean b) {
        this.changed = b;
    }

    public void add(String name, String note, int pos) {
        Task t = new TodoTask(name, note, 0);
        if (pos == -1)
            this.add(t);
        else {
            if (head != null) {
                Task prev = null;
                Task temp = head;
                while (temp != null) {
                    if (pos == 1) {
                        if (prev == null) {
                            t.setNext(head);
                            head = t;
                        } else {
                            t.setNext(prev.getNext());
                            prev.setNext(t);
                        }
                        break;
                    }
                    prev = temp;
                    temp = temp.getNext();
                    pos--;
                }
                if (temp == null) {
                    add(t);
                }
            }
        }
    }
}
