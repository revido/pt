/*
thread pomodoroWork
thread pomodoroBreak

Run Thread pomodoroWork. In there after maxSeconds=0 run Thread pomodoroBreak.

If break thread is in pomodoroWork then kill thread and create a new pomodoroWork thread that runs in background.
If break thread is in pomodoroBreak then kill thread and create a new pomodoroBreak thread that runs in background.
 */
package proj;

import java.io.IOException;
import java.util.Scanner;

class PomodoroBreak implements Runnable {
    private static final boolean SEND_MSG = false;
    private int maxSeconds;
    private final int longBreak = 9;
    private final int shortBreak = 300;
    private boolean continuous;
    private boolean kill;
    private boolean onBreak;
    private String breakMsg;

    public PomodoroBreak(boolean isLongBreak, boolean continuous) {
        setContinuous(true);
        setBreakLength(isLongBreak);

        this.continuous = continuous;
    }
    private void setBreakLength(boolean isLongBreak) {

        if (isLongBreak) {
            this.maxSeconds = this.longBreak;
            this.breakMsg = "Take_a_longer_break!";
        }
        else {
            this.maxSeconds = this.shortBreak;
            this.breakMsg = "Take_a_short_break!";
        }
    }

    Thread keyListenerThread;

    @Override
    public void run() {
        Debugger.log(">>>>>>>Break Thread started.");
        keyListenerThread = new Thread(new KeyListener(this));
        keyListenerThread.start();
        start();
        Debugger.log(">>>>>>>Break Thread killed.");
    }

    private void start() {
        sendMsg(breakMsg);
        try {
            while (getMaxSeconds() >= 0) {
                if (kill) {
                    kill = false;
                    continuous = false;
                    return;
                }
                Thread.sleep(1000);
                if (isContinuous()) {
                    displayTime("Break");
                }
                maxSeconds--;
            }
            sendMsg("Working_time!");
            onBreak = false;
            this.keyListenerThread.interrupt();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    public void promptEnterKey() {
        System.out.println("Press \"ENTER\" to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        scanner.nextLine();
    }
    public String showTime() {
        if (this.maxSeconds > 0) {
            String what, minutes, seconds;
            what = "Break ";
            minutes = Integer.toString(Math.floorDiv(maxSeconds, 60));
            seconds = Integer.toString(Math.floorMod(maxSeconds, 60));

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
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
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

    public boolean isAlive() {
        return this.getMaxSeconds() > 0;
    }
}
