package pt.activityInventory;

import java.util.Date;

class ActivityTask {
    private final ActivityUnplanned unp;
    private Date deadLine;
    private int pomodoros;
    private String name, info;
    private int id;

    public ActivityTask(int id, ActivityUnplanned unp, String name, int effort) {
        this.id = id;
        this.unp = unp;
        this.name = name;
        this.pomodoros = effort;
    }

    public ActivityTask(ActivityTask t) {
        this(t.getId(), t.getUnplanned(), t.getName(), t.getEffort());
    }

    public void to(ActivityTask t2) {
        this.deadLine = t2.getDeadLine();
        this.pomodoros = t2.getPomodoros();
        this.name = t2.getName();
        this.info = t2.getInfo();
    }

    public String getName() { return this.name; }
    public String getInfo() { return info; }
    public int getPomodoros() {
        return this.pomodoros;
    }
    public Date getDeadLine() { return deadLine; }

    public int getEffort() {
        return pomodoros;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ActivityUnplanned getUnplanned() {
        return unp;
    }
}
