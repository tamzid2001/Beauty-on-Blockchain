import com.fasterxml.jackson.databind.ObjectMapper; // You can use Jackson for JSON serialization
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileSystemBlockchainStorage {

    private static final String FILE_PATH = "blockchain_data.json";
    private ObjectMapper objectMapper = new ObjectMapper();

    public void saveBlockchain(Blockchain blockchain) throws IOException {
        String blockchainJson = objectMapper.writeValueAsString(blockchain);
        Files.write(Paths.get(FILE_PATH), blockchainJson.getBytes());
    }

    public Blockchain loadBlockchain() throws IOException {
        String blockchainJson = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
        return objectMapper.readValue(blockchainJson, Blockchain.class);
    }
}