package pt;

import pt.activityInventory.ActivityUnplanned;

import java.util.Date;

class Task {
    private final int expectedPomodorso;
    private final ActivityUnplanned unp;
    private Date date;
    private int id, pomodoros;
    private boolean done;
    private String name, notes;

    public Task(int id, String name, String notes) {
        this.id = id;
        this.name = name;
        this.notes = notes;
        this.date = new Date();
        expectedPomodorso = 0;
        unp = null;
    }

    public Task(Date date, int id, boolean done, String name, int pomodoros, String notes) {
        this(id, name, notes);
        this.date = date;
        this.done = done;
        this.pomodoros = pomodoros;
    }

    public Task(Task t) {
        this(t.getDate(), t.getId(), t.isDone(), t.getName(), t.getPomodoros(), t.getNotes());
    }

    //Used for importing activity tasks
    public Task(int i, boolean b, String name, int effort, ActivityUnplanned unp) {
        this.id = i;
        this.date = new Date();
        this.done = b;
        this.name = name;
        this.expectedPomodorso = effort;
        this.unp = unp;
//        if (unp == null) {
//            notes = "";
//        } else
//            this.notes = unp.toString();
        this.notes = "";
    }

    public void addMark() {
        pomodoros++;
    }

    public void finish() {
        this.done = true;
        this.id = -1;
    }

    public void to(Task t2) {
        this.date = t2.getDate();
        this.pomodoros = t2.getPomodoros();
        this.done = t2.isDone();
        this.name = t2.getName();
        this.notes = t2.getNotes();
    }

    public String getName() {
        return this.name;
    }

    public String getNotes() {
        return notes;
    }

    public boolean isDone() {
        return this.done;
    }

    public int getPomodoros() {
        return this.pomodoros;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }
}
