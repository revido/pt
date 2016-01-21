class Debugger {
    public static boolean debug;

    public static void log(Object o) {
        if (debug) {
            System.out.println("[INFO] " + o.toString());
        }
    }
}