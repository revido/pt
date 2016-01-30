package pt.Menu;

import java.util.Arrays;

class Command {
    private String command;
    private String[] params;

    public Command(String line) {
        refactor(line);
    }

    private void refactor(String line) {
        try {
            String[] split = line.split(" ");

            this.command = split[0];
            this.params = Arrays.copyOfRange(split, 1, split.length);
        }
        catch (NullPointerException e) {
            // EDIT FOR PROPer EXCEPTION HANDLING
            System.err.println("Invalid command");
        }
    }

    public String getCommand() { return command; }

    public String[] getParams() { return params.clone(); } }
