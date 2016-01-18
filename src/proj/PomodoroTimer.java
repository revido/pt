package proj;

import java.io.IOException;

class PomodoroTimer implements Runnable {
    private final int breakTimer;
    private final String breakMsg;
    private int maxSeconds = 1500;
    private final int longBreak = 900;
    private final int shortBreak = 300;
    private boolean continuous;
    private final Task t;
    private boolean kill;
    private boolean onBreak;

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
        if (!onBreak)
            start();
        else
            start();
    }

    private void displayTime(String msg) {
        String minutes = Integer.toString(Math.floorDiv(getMaxSeconds(), 60));
        String seconds = Integer.toString(Math.floorMod(getMaxSeconds(), 60));

        if (seconds.length() < 2) {
            seconds = "0" + seconds;
        }

        if (minutes.length() < 2) {
            minutes = "0" + minutes;
        }

        String time = minutes + ":" + seconds;
        String output = msg + " " + time;
        String ANSI_CLEAR = "\033[2K";
        String ANSI_HOME = "\033[" + output.length() + "D";
        System.out.print(ANSI_CLEAR + ANSI_HOME + output);
    }

    private void start() {
        try {
            while (getMaxSeconds() >= 0) {
                if (kill) {
                    kill = false;
                    continuous = false;
                    return;
                }
                Thread.sleep(1000);
                if (isContinuous()) {
                    if (onBreak)
                        displayTime("Break");
                    else
                        displayTime("Work");
                }
                maxSeconds--;
            }
            if (!onBreak) {
                t.addMark();
                sendMsg(breakMsg);
                this.maxSeconds = breakTimer;
                onBreak = true;
            } else {
                sendMsg("Working_time!");
                onBreak = false;
                System.out.println();
                System.out.println("Press Enter");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String showTime() {
        if (this.maxSeconds> 0) {
            String what, minutes, seconds;
            if (!onBreak) {
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

    public void killTimer() {
        kill = true;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private boolean isContinuous() {
        return continuous;
    }
    public void setContinuous(boolean continuous) {
        this.continuous = continuous;
    }
    public int getMaxSeconds() {
        return maxSeconds;
    }
}
