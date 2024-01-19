public class NetworkTest {

    @Test
    public void testServerClientCommunication() {
        Thread serverThread = new Thread(new MockServer(8080));
        serverThread.start();

        MockClient client = new MockClient();
        client.connectToServer("localhost", 8080);

        // Assertions and verification of communication
        serverThread.interrupt();
    }
}