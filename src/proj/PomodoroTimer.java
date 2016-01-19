/*
thread pomodoroWork
thread pomodoroBreak

Run Thread pomodoroWork. In there after maxSeconds=0 run Thread pomodoroBreak.

If break thread is in pomodoroWork then kill thread and create a new pomodoroWork thread that runs in background.
If break thread is in pomodoroBreak then kill thread and create a new pomodoroBreak thread that runs in background.
 */
package proj;

import java.io.IOException;

class PomodoroTimer implements Runnable {
    private static final boolean SEND_MSG = false;
    private int time = 2;
    private boolean continuous;
    private final Task t;
    private boolean kill;
    private boolean onBreak;
    private PomodoroBreak pomBreak;
    boolean isLongBreak;

    public PomodoroTimer(Task t, boolean isLongBreak) {
        setContinuous(true);
        this.t = t;
        this.isLongBreak = isLongBreak;
    }

    Thread keyListenerThread;
    Thread breakThread;

    @Override
    public void run() {
        Debugger.log(">>>>>>>Timer Thread started.");
        KeyListener listener = new KeyListener(this);
        keyListenerThread = new Thread(listener);
        keyListenerThread.start();
        start();
        Debugger.log(">>>>>>>Timer Thread killed.");
    }

    private void displayTime(String msg) {
        String minutes = Integer.toString(Math.floorDiv(getTime(), 60));
        String seconds = Integer.toString(Math.floorMod(getTime(), 60));

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
            while (getTime() >= 0) {
                if (kill) {
                    kill = false;
                    continuous = false;
                    return;
                }
                Thread.sleep(1000);
                if (isContinuous()) {
                    displayTime("Work");
                }
                time--;
            }
            t.addMark();
            onBreak = true;
            keyListenerThread.interrupt();
            pomBreak = new PomodoroBreak(isLongBreak, continuous);
            breakThread = new Thread(getPomBreak());
            breakThread.start();
            breakThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public String showTime() {
        if (this.time > 0) {
            String what, minutes, seconds;
            what = "Work ";
            minutes = Integer.toString(Math.floorDiv(getTime(), 60));
            seconds = Integer.toString(Math.floorMod(getTime(), 60));

            if (seconds.length() < 2) {
                seconds = "0" + seconds;
            }

            if (minutes.length() < 2) {
                minutes = "0" + minutes;
            }
            return what + minutes + ":" + seconds;
        } else if (this.getPomBreak() != null && this.getPomBreak().getMaxSeconds() > 0) {
            String what, minutes, seconds;
            what = "Work ";
            minutes = Integer.toString(Math.floorDiv(this.getPomBreak().getMaxSeconds(), 60));
            seconds = Integer.toString(Math.floorMod(this.getPomBreak().getMaxSeconds(),60));

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
            if (SEND_MSG)
                Runtime.getRuntime().exec("/home/alma/.config/alma/pt.sh 'Pomodoro' '" + msg + "' ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void killTimer() {
        kill = true;
        PomodoroBreak b = this.getPomBreak();
        if (b != null) {
            b.killTimer();
        }
    }

    private boolean isContinuous() {
        return continuous;
    }

    public void setContinuous(boolean continuous) {
        this.continuous = continuous;
        if (getPomBreak() != null) {
            getPomBreak().setContinuous(continuous);
        }
    }

    public int getTime() {
        return time;
    }

    public boolean isAlive() {
        return this.getTime() > 0;
    }

    public boolean breakAlive() {
        return this.getPomBreak().isAlive();
    }

    public Thread getBreakThread() {
        return breakThread;
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
