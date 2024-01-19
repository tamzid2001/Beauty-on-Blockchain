public class PerformanceLogger {
    public static void logPerformanceMetrics(String action, long startTime) {
        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("Action: " + action + ", Elapsed Time: " + elapsedTime + "ms");
    }
}