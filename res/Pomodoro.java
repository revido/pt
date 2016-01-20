package proj;

import java.io.IOException;

public abstract class Pomodoro implements Runnable {

    private int time = 15;
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

        keyListenerThread = new Thread(new KeyListener(Thread.currentThread()));
        keyListenerThread.start();

        sendMsg(this.msg);
        try {
            while (getTime() >= 0) {
                if (isContinuous()) {
                    printTime();
                }
                Thread.sleep(1000);
                time--;
            }
            // break
            this.time = this.shortBreak;
            while (getTime() >= 0) {
                if (isContinuous()) {
                    printTime();
                }
                Thread.sleep(1000);
                time--;
            }
//            work();
//            this.keyListenerThread.interrupt();

        } catch (InterruptedException e) {
            continuous = false;
            Debugger.log(this.timerType() + " Thread was interrupted.");
            Debugger.log(this.timerType() + " Thread stopped.");
            return;
        }
        Debugger.log(this.timerType() + " Thread stopped.");
    }


    private void printTime() {
        String output = printTimeSingle(work);
        String ANSI_CLEAR = "\033[2K";
        String ANSI_HOME = "\033[" + output.length() + "D";
        System.out.print(ANSI_CLEAR + ANSI_HOME + output);
    }

    public String printTimeSingle() {
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

    abstract void work() throws InterruptedException;

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
