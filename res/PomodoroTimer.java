package proj;

class PomodoroTimer extends Pomodoro {
    private final Task t;

    public PomodoroTimer(Task t) {
        super();
        this.t = t;
    }

    @Override
    void work() throws InterruptedException {
            t.addMark();
    }
}
