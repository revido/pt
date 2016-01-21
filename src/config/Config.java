package config;

public class Config {
    private int pomodoro;
    private int shortBreak;
    private int longBreak;

    private String workMsg;
    private String shortMsg;
    private String longMsg;

    private boolean debug;
    private boolean allowMsg;

    Config(ConfigBuilder builder) {
        if (builder.pomodoro == 0) throw new NullPointerException("pomodoro");
        if (builder.shortBreak == 0) throw new NullPointerException("pomodoro");
        if (builder.longBreak == 0) throw new NullPointerException("pomodoro");
        if (builder.workMsg == null) throw new NullPointerException("work message");
        if (builder.shortMsg == null) throw new NullPointerException("short break message");
        if (builder.longMsg == null) throw new NullPointerException("long break message");

        this.pomodoro = builder.pomodoro;
        this.shortBreak = builder.shortBreak;
        this.longBreak = builder.longBreak;
        this.workMsg = builder.workMsg;
        this.shortMsg = builder.shortMsg;
        this.longMsg = builder.longMsg;
        this.debug = builder.debug;
        this.allowMsg = builder.allowMsg;
    }

    public int getPomodoro() {
        return pomodoro;
    }

    public int getShortBreak() {
        return shortBreak;
    }

    public int getLongBreak() {
        return longBreak;
    }

    public String getWorkMsg() {
        return workMsg;
    }

    public String getShortMsg() {
        return shortMsg;
    }

    public String getLongMsg() {
        return longMsg;
    }

    public boolean getDebug() {
        return debug;
    }

    public boolean getAllowMsg() {
        return allowMsg;
    }
}

