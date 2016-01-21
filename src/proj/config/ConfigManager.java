package proj.config;

import java.io.*;
import java.util.Properties;

public class ConfigManager {
    private final String location;
    Properties prop;
    InputStream input;
    OutputStream output;
    private Config config;

    public ConfigManager() {
        prop = new Properties();
        location = System.getProperty("user.home") + File.separator + ".pt" + File.separator + "config";
    }

    public void saveDefaultConfig() {
        try {
            output = new FileOutputStream(location);
            prop.setProperty("debug", "false");
            prop.setProperty("pomodoro", "1500");
            prop.setProperty("short_break", "300");
            prop.setProperty("long_break", "900");
            prop.setProperty("work_msg", "Working_time!");
            prop.setProperty("short_break_msg", "Take_a_short_break!");
            prop.setProperty("long_break_msg", "Take_a_longer_break!");
            prop.setProperty("allow_msg", "true");

            prop.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void load() {
        try {
            input = new FileInputStream(location);
            prop.load(input);

            int pomodoro = Integer.parseInt(prop.getProperty("pomodoro"));
            int shortBreak = Integer.parseInt(prop.getProperty("short_break"));
            int longBreak = Integer.parseInt(prop.getProperty("long_break"));

            String workMsg = prop.getProperty("work_msg");
            String shortBreakMsg = prop.getProperty("short_break_msg");
            String longBreakMsg = prop.getProperty("long_break_msg");

            boolean debug = Boolean.parseBoolean(prop.getProperty("debug"));
            boolean allowMsg = Boolean.parseBoolean(prop.getProperty("allow_msg"));

            config = ConfigBuilder.config()
                    .withPomodoro(pomodoro)
                    .withShortBreak(shortBreak)
                    .withLongBreak(longBreak)
                    .withWorkMsg(workMsg)
                    .withShortMsg(shortBreakMsg)
                    .withLongMsg(longBreakMsg)
                    .withDebug(debug)
                    .withAllowMsg(allowMsg)
                    .build();

        } catch (FileNotFoundException e) {
            saveDefaultConfig();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Config getConfig() {
        return config;
    }
}
