package proj;

import java.io.IOException;

public abstract class Pomodoro implements Runnable {

    private int time = 3;
    protected final int shortBreak = 300;
    protected final int longBreak = 900;

    private boolean SEND_MSG;
    private String msg;
    private boolean continuous;
    private boolean kill;
    Thread keyListenerThread;


    public Pomodoro() {
        setContinuous(true);
        this.SEND_MSG = false;
    }

    @Override
    public void run() {
        start();
    }

    private void start() {
        Debugger.log(this.timerType() + " Thread started.");

        keyListenerThread = new Thread(new KeyListener(this));
        keyListenerThread.start();

        sendMsg(this.msg);
        try {
            while (getTime() >= 0) {
                if (kill) {
                    kill = false;
                    continuous = false;
                    return;
                }
                Thread.sleep(1000);
                if (isContinuous()) {
                    printTime();
                }
                time--;
            }
            this.keyListenerThread.interrupt();
            work();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Debugger.log(this.timerType() + " Thread stopped.");
    }


    private void printTime() {
        String output = printTimeSingle();
        String ANSI_CLEAR = "\033[2K";
        String ANSI_HOME = "\033[" + output.length() + "D";
        System.out.print(ANSI_CLEAR + ANSI_HOME + output);
    }

    public String printTimeSingle() {
        if (this.getTime() > 0) {
            String type, minutes, seconds;
            type = this.timerType();
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
        return "";
    }

    private String timerType() {
        if (this instanceof PomodoroTimer) {
            return "Work";
        } else
            return "Break";
    }


    private void sendMsg(String msg) {
        try {
            if (SEND_MSG)
                Runtime.getRuntime().exec("/home/alma/.config/alma/pt.sh 'Pomodoro' '" + msg + "' ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    abstract void work();

    protected boolean isContinuous() {
        return continuous;
    }

    public void setContinuous(boolean continuous) {
        this.continuous = continuous;
    }

    public int getTime() {
        return this.time;
    }

    public boolean isAlive() {
        return this.getTime() > 0;
    }

    public void killTimer() {
        kill = true;
    }

    protected void setTimer(int time) {
        this.time = time;
    }

    protected void setMsg(String msg) {
        this.msg = msg;
    }
}
