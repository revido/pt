package pt;

import java.io.IOException;
import java.util.Scanner;

class KeyListener implements Runnable {
    private final Thread pomThread;
    private Scanner sin;

    public KeyListener(Thread pomThread) {
        this.pomThread = pomThread;
    }

    @Override
    public void run() {
        Debugger.log("pt.KeyListener running.");
        try {
            sin = new Scanner(System.in);
            while (hasNextLine()) {
                if (sin.nextLine().equals("")) {
                    break;
                }
            }
            pomThread.interrupt();
            Debugger.log("KeyListener killed.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean hasNextLine() throws IOException {
        while (System.in.available() == 0) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Debugger.log("KeyListener killed.");
                return false;
            }
        }
        return sin.hasNextLine();
    }
}
