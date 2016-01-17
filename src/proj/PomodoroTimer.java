package proj;

import java.io.IOException;

class PomodoroTimer implements Runnable {
    private int breakTimer;
    private final String breakMsg;
    private boolean isWorking;
    private int maxSeconds = 1500;
    private final int longBreak = 900;
    private final int shortBreak = 300;
    private boolean continuous;
    private final Task t;
    private boolean alive;

    public PomodoroTimer(Task t, boolean isLongBreak) {
        setContinuous(false);
        this.t = t;
        if (isLongBreak) {
            breakTimer = longBreak;
            breakMsg = "Take_a_longer_break!";
        } else {
            breakTimer = shortBreak;
            breakMsg = "Take_a_short_break!";
        }
    }

    @Override
    public void run() {
        start();
    }

    private void start() {
        alive = true;
        isWorking = true;
        try {
            while (getMaxSeconds() >= 0) {
                Thread.sleep(1000);
                if (isContinuous()) {
                    String minutes = Integer.toString(Math.floorDiv(getMaxSeconds(), 60));
                    String seconds = Integer.toString(Math.floorMod(getMaxSeconds(), 60));

                    if (seconds.length() < 2) {
                        seconds = "0" + seconds;
                    }

                    if (minutes.length() < 2) {
                        minutes = "0" + minutes;
                    }

                    String time = minutes + ":" + seconds;
                    String output = "Work " + time;
                    String ANSI_CLEAR = "\033[2K";
                    String ANSI_HOME = "\033[" + output.length() + "D";
                    System.out.print(ANSI_CLEAR + ANSI_HOME + output);
                }
                maxSeconds -= 1;
            }
            t.addMark();

            breakTime();
            alive = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void breakTime() throws InterruptedException {
        isWorking = false;
        sendMsg(breakMsg);
        while (this.breakTimer >= 0) {
            Thread.sleep(1000);
            this.breakTimer--;
        }
        sendMsg("Working_time!");
    }

    private boolean isContinuous() {
        return continuous;
    }

    private void setContinuous(boolean continuous) {
        this.continuous = continuous;
    }

    private int getMaxSeconds() {
        return maxSeconds;
    }

    public String showTime() {
        if (alive) {
            String what, minutes, seconds;
            if (isWorking) {
                what = "Work ";
                minutes = Integer.toString(Math.floorDiv(getMaxSeconds(), 60));
                seconds = Integer.toString(Math.floorMod(getMaxSeconds(), 60));
            } else {
                what = "Break ";
                minutes = Integer.toString(Math.floorDiv(breakTimer, 60));
                seconds = Integer.toString(Math.floorMod(breakTimer, 60));
            }

            if (seconds.length() < 2) {
                seconds = "0" + seconds;
            }

            if (minutes.length() < 2) {
                minutes = "0" + minutes;
            }
            return what + minutes + ":" + seconds;
        }
        return "No timer";
    }

    private void sendMsg(String msg) {
        try {
            Runtime.getRuntime().exec("/home/alma/.config/alma/pt.sh 'Pomodoro' '" + msg + "' ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
