package proj;

class PomodoroBreak extends Pomodoro {

    public PomodoroBreak(boolean isLongBreak, boolean isContinuous) {
        this.setContinuous(isContinuous);
        this.setBreakLength(isLongBreak);
    }

    private void setBreakLength(boolean isLongBreak) {

        if (isLongBreak) {
            this.setTimer(this.longBreak);
            this.setMsg("Take_a_longer_break!");
        } else {
            this.setTimer(this.shortBreak);
            this.setMsg("Take_a_short_break!");
        }
    }

    @Override
    void work() {

    }
}
