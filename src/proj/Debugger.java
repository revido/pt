package proj;

class Debugger {
    public static boolean debug;

    public static void log(Object o) {
        if (debug) {
            System.out.println();
            System.out.println("----------------------------------------");
            System.out.println("DEBUG: " + o.toString());
            System.out.println("----------------------------------------");
        }
    }
}