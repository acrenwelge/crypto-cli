package util;

public class CustomLogger {
    private CustomLogger() {}
    private static CustomLogger instance;
    public static CustomLogger getInstance() {
        if (instance == null) {
            instance = new CustomLogger();
        }
        return instance;
    }

    public static boolean LOGGING_LEVEL;

    public void print(String pattern, Object... params) {
        System.out.printf(pattern, params);
    }

    public void print() {
        System.out.println();
    }

    public void error(String pattern, Object... params) {
        System.err.printf(pattern, params);
    }

    public void info(String pattern, Object... params) {
        log(0, pattern, params);
    }

    public void debug(String pattern, Object... params) {
        log(1, pattern, params);
    }

    public void trace(String pattern, Object... params) {
        log(2, pattern, params);
    }

    private void log(int threshold, String pattern, Object... params) {
        if (LOGGING_LEVEL) {
            System.err.printf(pattern, params);
        }
    }
}