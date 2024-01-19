public class StressTestRunner {

    public static void main(String[] args) {
        StressTest test = new StressTest(1000); // For example, 1000 requests
        test.performLoadTest();
        // Analyze results
    }
}