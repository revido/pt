package pt;

import java.util.Date;

class Task {
    private Date date;
    private int id;
    private int pomodoros;
    private boolean done;
    private String name;
    private final String notes;

    public Task(int id, String name, String notes) {
        this.id = id;
        this.name = name;
        this.notes = notes;
        this.date = new Date();
    }

    public Task(Date date, int id, boolean done, String name, int pomodoros, String notes) {
        this(id, name, notes);
        this.date = date;
        this.done = done;
        this.pomodoros = pomodoros;
    }

    public void addMark() {
        pomodoros++;
    }

    public void finish() {
        this.done = true;
        this.id = -1;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFinished() {
        return this.done;
    }

    public int getMarks() {
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

    public String getNotes() {
        return notes;
    }
}
