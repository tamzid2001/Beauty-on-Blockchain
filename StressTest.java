public class StressTest {

    private final int numberOfRequests;

    public StressTest(int numberOfRequests) {
        this.numberOfRequests = numberOfRequests;
    }

    public void performLoadTest() {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < numberOfRequests; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    // Code to send a request to your blockchain application
                }
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}