// Build pattern
package pt.config;

public class ConfigBuilder {
    int pomodoro;
    int shortBreak;
    int longBreak;

    String workMsg;
    String shortMsg;
    String longMsg;

    boolean debug;
    boolean allowMsg;

    public static ConfigBuilder config() {
        return new ConfigBuilder();
    }

    public ConfigBuilder withPomodoro(int pomodoro) {
        this.pomodoro = pomodoro;
        return this;
    }

    public ConfigBuilder withShortBreak(int shortBreak) {
        this.shortBreak = shortBreak;
        return this;
    }

    public ConfigBuilder withLongBreak(int longBreak) {
        this.longBreak = longBreak;
        return this;
    }

    public ConfigBuilder withWorkMsg(String workMsg) {
        this.workMsg = workMsg;
        return this;
    }

    public ConfigBuilder withShortMsg(String shortMsg) {
        this.shortMsg = shortMsg;
        return this;
    }

    public ConfigBuilder withLongMsg(String longMsg) {
        this.longMsg = longMsg;
        return this;
    }

    public ConfigBuilder withDebug(boolean debug) {
        this.debug = debug;
        return this;
    }

    public ConfigBuilder withAllowMsg(boolean allowMsg) {
        this.allowMsg = allowMsg;
        return this;
    }

    public Config build() {
        return new Config(this);
    }

}
