import java.io.IOException;
import java.net.Socket;

public class MockClient {

    public void connectToServer(String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            // Interact with the server
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}