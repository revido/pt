package pt;

import pt.config.Config;
import pt.taskManagement.TodoTask;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

class Pomodoro implements Runnable {

    private final TodoTask currentTask;
    private int time;

    private boolean continuous;
    private final boolean isLongBreak;
    private boolean onWork;
    private final Config config;


    // Initialize the pomodoro
    public Pomodoro(boolean isLongBreak, TodoTask currentTask, Config config) {
        this.config = config;
        time = config.getPomodoro();

        this.currentTask = currentTask;
        this.isLongBreak = isLongBreak;
        setContinuous(true);
    }

    @Override
    public void run() {
        start();
    }

    // Run the pomodoro timer
    private void start() {
        Debugger.log("Thread started");
        Thread keyListenerThread = new Thread(new KeyListener(Thread.currentThread()));
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
            sendMsg(config.getWorkMsg());
        System.out.println();
        keyListenerThread.interrupt();
        Debugger.log("Thread finished");
    }

    // When the pomodoro (not in break) is started onWork is set to true
    public void setOnWork(boolean onWork) {
        Debugger.log("Setting onWork to " + ((onWork) ? "true" : "false"));
        this.onWork = onWork;
    }

    // Count one second down
    private void countDown() {
        time--;
    }

    // A second
    private void sleep() throws InterruptedException {
        Thread.sleep(1000);
    }

    private int getBreakLength() {
        return (isLongBreak) ? config.getLongBreak() : config.getShortBreak();
    }

    // Prints the current time on the screen
    private void printTime() {
        if (continuous) {
            String output = printTimeSingle();
            String ANSI_CLEAR = "\033[2K";
            String ANSI_HOME = "\033[" + output.length() + "D";
            System.out.print(ANSI_CLEAR + ANSI_HOME + output);
        }
    }

    // Returns the current time as string in the format: mm:ss
    public String printTimeSingle() {
        String type, minutes, seconds;
        if (isOnWork())
            type = "Work";
        else
            type = "Break";

        int tempTime = this.getTime();

        Calendar c = Calendar.getInstance();
        c.setTime(new Date(tempTime*1000));
        minutes = Integer.toString(c.get(Calendar.MINUTE));
        seconds= Integer.toString(c.get(Calendar.SECOND));

        if (seconds.length() < 2) {
            seconds = "0" + seconds;
        }

        if (minutes.length() < 2) {
            minutes = "0" + minutes;
        }
        return type + " " + minutes + ":" + seconds;
    }

    // Sends a message to PushBullet
    private void sendMsg(String msg) {
        try {
            if (config.getAllowMsg()) {
                String heading = "Pomodoro";
                String fullHeading = "'" + heading + "'";
                String location = System.getProperty("user.home") + File.separator + ".pt" + File.separator + "send.sh";
                String run = location + " " + fullHeading + " '" + msg + "'";

                Runtime.getRuntime().exec(run);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Returns the message for long or short break
    private String getBreakMsg() {
        if (isLongBreak)
            return config.getLongMsg();
        else
            return config.getShortMsg();
    }

    // Sets a variable that determines of the output is printed continuously
    public void setContinuous(boolean continuous) {
        Debugger.log("Setting continuous to " + ((continuous) ? "true" : "false"));
        this.continuous = continuous;
    }

    // Returns the current time of the active pomodoro
    private int getTime() {
        return this.time;
    }

    // Checks if the current pomodoro is active
    private boolean isOnWork() {
        return onWork;
    }

    // Tests if the pomodoro (incl. break) is ticking
    public boolean isAlive() {
        return getTime() > 0;
    }
}
