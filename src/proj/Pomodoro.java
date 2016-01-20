package proj;

import jdk.nashorn.internal.runtime.Debug;

import java.io.IOException;

public class Pomodoro implements Runnable {

    private final Task currentTask;
    private int time = 1500;
    protected final int shortBreak = 300;
    protected final int longBreak = 900;

    private boolean SEND_MSG;
    private boolean continuous;
    Thread keyListenerThread;
    public String WORK_MSG = "Working_Time!";
    private String BREAK_MSG_LONG = "Take_a_longer_break!";
    private String BREAK_MSG_SHORT = "Take_a_short_break!";
    private boolean isLongBreak;
    private boolean onWork;


    public Pomodoro(boolean isLongBreak, Task currentTask) {
        this.currentTask = currentTask;
        this.isLongBreak = isLongBreak;
        setContinuous(true);
        this.SEND_MSG = true;
    }

    @Override
    public void run() {
        start();
    }

    private void start() {
        Debugger.log("Thread started");
        keyListenerThread = new Thread(new KeyListener(Thread.currentThread()));
        keyListenerThread.start();

        try {
            while (getTime() >= 0 || onWork) {
                printTime();
                countDown();
                if (getTime() <= 0 && onWork) {
                    sendMsg(getBreakMsg());
                    currentTask.addMark();
                    time = getBreakLength();
                    setOnWork(false);
                }
                sleep();
            }
        } catch (InterruptedException ignored) {
            Debugger.log("Thread interrupted");
        }
        if (getTime() <= 0)
            sendMsg(WORK_MSG);
        System.out.println();
        keyListenerThread.interrupt();
        Debugger.log("Thread finished");
    }

    public void setOnWork(boolean onWork) {
        Debugger.log("Setting onWork to " + ((onWork) ? "true" : "false"));
        this.onWork = onWork;
    }

    private void countDown() {
        time--;
    }

    private void sleep() throws InterruptedException {
        Thread.sleep(1000);
    }

    private int getBreakLength() {
        return (isLongBreak) ? longBreak : shortBreak;
    }

    private void printTime() {
        if (continuous) {
            String output = printTimeSingle();
            String ANSI_CLEAR = "\033[2K";
            String ANSI_HOME = "\033[" + output.length() + "D";
            System.out.print(ANSI_CLEAR + ANSI_HOME + output);
        }
    }

    public String printTimeSingle() {
        String type, minutes, seconds;
        if (isOnWork())
            type = "Work";
        else
            type = "Break";

        int tempTime = this.getTime();

        minutes = Integer.toString(Math.floorDiv(tempTime, 60));
        seconds = Integer.toString(Math.floorMod(tempTime, 60));

        if (seconds.length() < 2) {
            seconds = "0" + seconds;
        }

        if (minutes.length() < 2) {
            minutes = "0" + minutes;
        }
        return type + " " + minutes + ":" + seconds;
    }

    public void sendMsg(String msg) {
        try {
            if (SEND_MSG)
                Runtime.getRuntime().exec("/home/alma/.config/alma/pt.sh 'Pomodoro' '" + msg + "' ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getBreakMsg() {
        if (isLongBreak)
            return this.BREAK_MSG_LONG;
        else
            return this.BREAK_MSG_SHORT;
    }

    protected boolean isContinuous() {
        return continuous;
    }

    public void setContinuous(boolean continuous) {
        Debugger.log("Setting continuous to " + ((continuous) ? "true" : "false"));
        this.continuous = continuous;
    }

    public int getTime() {
        return this.time;
    }

    public boolean isOnWork() {
        return onWork;
    }

    public boolean isAlive() {
        return getTime() > 0;
    }
}
