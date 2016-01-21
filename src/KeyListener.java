import java.io.IOException;
import java.util.Scanner;

class KeyListener implements Runnable {
    private final Thread pomThread;

    public KeyListener(Thread pomThread) {
        this.pomThread = pomThread;
    }

    private Scanner sin;

    @Override
    public void run() {
        Debugger.log("KeyListener running.");
        try {
            sin = new Scanner(System.in);
            while (hasNextLine()) {
                if (sin.nextLine().equals("")) {
                    Debugger.log("KeyListener killed.");
                    break;
                }
            }

            pomThread.interrupt();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean hasNextLine() throws IOException {
        while (System.in.available() == 0) {
            try {
                Thread.currentThread().sleep(10);
            } catch (InterruptedException e) {
                Debugger.log("KeyListener killed.");
                return false;
            }
        }
        return sin.hasNextLine();
    }
}
