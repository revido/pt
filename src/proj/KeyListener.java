package proj;

import java.io.BufferedReader;
import java.io.IOException;

class KeyListener implements Runnable {
    private final PomodoroTimer t;
    private final BufferedReader in;

    public KeyListener(PomodoroTimer t, BufferedReader in) {
        this.t = t;
        this.in = in;
    }

    @Override
    public void run() {
        try {
            int ascii = 0;
            while (ascii != 10) {
                ascii = in.read();
            }
            t.killTimer();
            System.out.println();
            System.out.print("pt> ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
