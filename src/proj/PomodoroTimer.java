package proj;

class PomodoroTimer extends Pomodoro {
    private final Task t;
    private PomodoroBreak pomBreak;
    boolean isLongBreak;
    Thread breakThread;

    public PomodoroTimer(Task t, boolean isLongBreak) {
        super();
        this.t = t;
        this.isLongBreak = isLongBreak;
    }

    @Override
    void work() {
        try {
            t.addMark();
            pomBreak = new PomodoroBreak(isLongBreak, this.isContinuous());
            breakThread = new Thread(pomBreak);
            breakThread.start();
            breakThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isBreakAlive() {
        return this.getPomBreak() != null && getPomBreak().isAlive();
    }

    public boolean isTimerRunning() {
        return this.isAlive() || this.isBreakAlive();
    }

    public PomodoroBreak getPomBreak() {
        return pomBreak;
    }
}
