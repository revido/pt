package proj;

import java.io.BufferedReader;
import java.io.IOException;
import java.security.Key;
import java.util.Scanner;

class KeyListener implements Runnable {
//    private final PomodoroTimer t;
//    private final PomodoroBreak b;
//    private final BufferedReader in;
    private final Pomodoro p;

//    public KeyListener(PomodoroTimer t, BufferedReader in) {
//        this.t = t;
//        b = null;
//        this.in = in;
//    }

    public KeyListener(Pomodoro p) {
        this.p = p;
    }
    /*
    public KeyListener(PomodoroTimer t) {
        in = null;
        b = null;
        this.t = t;
    }

    public KeyListener(PomodoroBreak b) {
        in = null;
        t = null;
        this.b = b;
    }*/

    Scanner sin;

    @Override
    public void run() {
        try {
            sin = new Scanner(System.in);
            while (hasNextLine()) {
                if (sin.nextLine().equals("")) {
                    break;
                }
            }
            if(p != null)
                p.killTimer();
//            if (t != null)
//                t.killTimer();
//            if(b != null)
//                b.killTimer();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean hasNextLine() throws IOException {
        while (System.in.available() == 0) {
            // [variant 1
            try {
                Thread.currentThread().sleep(10);
            } catch (InterruptedException e) {
                return false;
            }// ]

            // [variant 2 - without sleep you get a busy wait which may load your cpu
            //if (this.isInterrupted()) {
            //    System.out.println("Thread is interrupted.. breaking from loop");
            //    return false;
            //}// ]
        }
        return sin.hasNextLine();
    }
}
