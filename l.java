import java.security.MessageDigest;

public class Block {
    private String hash;
    private String previousHash;
    private String data; // our data will be a simple message.
    private long timeStamp; // as number of milliseconds since 1/1/1970.
    private int nonce;

    // Block Constructor.
    public Block(String data, String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = System.currentTimeMillis();
        this.hash = calculateHash(); // Making sure we do this after setting the other values.
    }

    public String calculateHash() {
        String calculatedhash = applySha256( 
                previousHash +
                Long.toString(timeStamp) +
                Integer.toString(nonce) + 
                data 
                );
        return calculatedhash;
    }

    // Applies Sha256 to a string and returns the result. 
    public static String applySha256(String input) { 
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");            
            // Applies sha256 to our input, 
            byte[] hash = digest.digest(input.getBytes("UTF-8"));           
            StringBuffer hexString = new StringBuffer(); // This will contain hash as hexidecimal
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Add other getters and setters
}
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Node {
    private int port;
    private List<Block> blockchain;
    private List<Socket> peers;

    public Node(int port) {
        this.port = port;
        this.blockchain = new ArrayList<>();
        this.peers = new ArrayList<>();
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Node started on port " + port);

        while (true) {
            Socket peerSocket = serverSocket.accept();
            peers.add(peerSocket);
            // Handle the peer connection in a separate thread
        }
    }

    // Methods to handle blockchain operations and network communication
}
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Node {
    private int port;
    private List<Block> blockchain;
    private List<Socket> peers;

    public Node(int port) {
        this.port = port;
        this.blockchain = new ArrayList<>();
        this.peers = new ArrayList<>();
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Node started on port " + port);

        while (true) {
            Socket peerSocket = serverSocket.accept();
            peers.add(peerSocket);
            // Handle the peer connection in a separate thread
            new Thread(new PeerHandler(peerSocket, this)).start();
        }
    }

    // Method to broadcast new block to all peers
    public void broadcastNewBlock(Block block) {
        for (Socket socket : peers) {
            try {
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                out.writeObject(block);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to add block to blockchain
    public synchronized void addBlock(Block block) {
        blockchain.add(block);
        // Further validation and consensus checks should be added here
    }

    // Method to get the latest block
    public Block getLatestBlock() {
        return blockchain.get(blockchain.size() - 1);
    }

    // PeerHandler class
    class PeerHandler implements Runnable {
        private Socket socket;
        private Node node;

        public PeerHandler(Socket socket, Node node) {
            this.socket = socket;
            this.node = node;
        }

        @Override
        public void run() {
            try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
                while (true) {
                    Object object = in.readObject();
                    if (object instanceof Block) {
                        Block newBlock = (Block) object;
                        // Handle new block, validate and add to blockchain
                        node.addBlock(newBlock);
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    // Main method to start the Node
    public static void main(String[] args) throws IOException {
        int port = 8080; // Example port number
        Node node = new Node(port);
        node.start();
    }
}
public class Block {
    // Existing fields and methods...

    // Method to mine a block
    public void mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * "0" 
        while(!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Block Mined!!! : " + hash);
    }
}
public class Node {
    // Existing fields and methods...

    private int difficulty = 4; // Example difficulty level

    // Modified method to add block to blockchain
    public synchronized void addBlock(Block block) {
        if (isValidNewBlock(block, getLatestBlock())) {
            blockchain.add(block);
        }
    }

    // Method to validate a new block
    private boolean isValidNewBlock(Block newBlock, Block previousBlock) {
        if (previousBlock != null && !newBlock.getPreviousHash().equals(previousBlock.getHash())) {
            return false; // Check if the new block's previous hash matches the latest block's hash
        }
        if (!newBlock.getHash().equals(newBlock.calculateHash())) {
            return false; // Check if the new block's hash is valid
        }
        if (!newBlock.getHash().substring(0, difficulty).equals(new String(new char[difficulty]).replace('\0', '0'))) {
            return false; // Check if the hash meets the difficulty level
        }
        return true;
    }

    // Rest of the Node class...
}

public class Node {
    // Existing fields and methods...

    // Method to create and mine a new block
    public void createAndMineBlock(String data) {
        Block newBlock = new Block(data, getLatestBlock().getHash());
        newBlock.mineBlock(difficulty);
        addBlock(newBlock);
        broadcastNewBlock(newBlock);
    }

    // Rest of the Node class...
}

public class Block {
    private String hash;
    private String previousHash;
    private String data; // our data will be a simple message.
    private String productId;
    private String manufacturingDetails;
    private long timeStamp;
    private int nonce;

    // Block Constructor.
    public Block(String data, String productId, String manufacturingDetails, String previousHash) {
        this.data = data;
        this.productId = productId;
        this.manufacturingDetails = manufacturingDetails;
        this.previousHash = previousHash;
        this.timeStamp = System.currentTimeMillis();
        this.hash = calculateHash(); // Make sure this is done after setting the other values.
    }

    // Existing methods...

    // Getters and setters

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getManufacturingDetails() {
        return manufacturingDetails;
    }

    public void setManufacturingDetails(String manufacturingDetails) {
        this.manufacturingDetails = manufacturingDetails;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getNonce() {
        return nonce;
    }

    public void setNonce(int nonce) {
        this.nonce = nonce;
    }
}
import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    private List<Block> chain;

    public Blockchain() {
        this.chain = new ArrayList<>();
        // Create the genesis block
        chain.add(createGenesisBlock());
    }

    private Block createGenesisBlock() {
        return new Block("Genesis Block", "0", "", "0");
    }

    // Method to add a block after validating
    public void addBlock(Block newBlock) {
        if (isValidNewBlock(newBlock, getLatestBlock())) {
            chain.add(newBlock);
        }
    }

    // Method to validate the new block
    private boolean isValidNewBlock(Block newBlock, Block previousBlock) {
        if (previousBlock != null && !newBlock.getPreviousHash().equals(previousBlock.getHash())) {
            return false; // Check if the previous hash is correct
        }
        if (!newBlock.getHash().equals(newBlock.calculateHash())) {
            return false; // Check if the hash is valid
        }
        return true;
    }

    // Method to get the latest block in the chain
    public Block getLatestBlock() {
        return chain.size() > 0 ? chain.get(chain.size() - 1) : null;
    }

    // Method to check the integrity of the entire chain
    public boolean isChainValid() {
        for (int i = 1; i < chain.size(); i++) {
            Block currentBlock = chain.get(i);
            Block previousBlock = chain.get(i - 1);

            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                return false; // Checks for changes to the current block
            }
            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                return false; // Checks for changes to the previous block
            }
        }
        return true;
    }

    // Method to replace the blockchain with a longer one
    public boolean replaceChain(List<Block> newChain) {
        if (newChain.size() > chain.size() && isChainValid(newChain)) {
            chain = newChain;
            return true;
        }
        return false;
    }

    // Helper method to validate a new chain
    private boolean isChainValid(List<Block> newChain) {
        // Start by checking the genesis block
        if (!newChain.get(0).getHash().equals(chain.get(0).getHash())) {
            return false;
        }

        for (int i = 1; i < newChain.size(); i++) {
            Block currentBlock = newChain.get(i);
            Block previousBlock = newChain.get(i - 1);

            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                return false; // Checks for changes to the current block
            }
            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                return false; // Checks for changes to the previous block
            }
        }
        return true;
    }

    // Getters and setters
    public List<Block> getChain() {
        return chain;
    }

    public void setChain(List<Block> chain) {
        this.chain = chain;
    }
}
import java.security.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Block {
    // ... existing fields ...

    private byte[] signature;

    // ... existing constructor and methods ...

    private byte[] getBlockData() {
        // Combine block data fields into a byte array
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            outputStream.write(previousHash.getBytes(StandardCharsets.UTF_8));
            outputStream.write(data.getBytes(StandardCharsets.UTF_8));
            outputStream.write(productId.getBytes(StandardCharsets.UTF_8));
            outputStream.write(manufacturingDetails.getBytes(StandardCharsets.UTF_8));
            outputStream.write(Long.toString(timeStamp).getBytes(StandardCharsets.UTF_8));
            outputStream.write(Integer.toString(nonce).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            // Handle exception
        }
        return outputStream.toByteArray();
    }

    // Getters and setters
    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    // Existing getters and setters for other fields...
}
import javax.net.ssl.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore;

public class SecureNode {
    private static final int SERVER_PORT = 8080; // Example port number
    private SSLContext sslContext;
    private Blockchain blockchain;

    public SecureNode() throws Exception {
        this.sslContext = setupSSLContext();
        this.blockchain = new Blockchain(); // Assuming you have a Blockchain class implemented
    }

    private SSLContext setupSSLContext() throws Exception {
        // Load keystore and truststore
        KeyStore keyStore = KeyStore.getInstance("JKS");
        KeyStore trustStore = KeyStore.getInstance("JKS");

        // Keystore and truststore passwords
        char[] keyStorePassword = "keyStorePassword".toCharArray();
        char[] trustStorePassword = "trustStorePassword".toCharArray();

        keyStore.load(new FileInputStream("keystore.jks"), keyStorePassword);
        trustStore.load(new FileInputStream("truststore.jks"), trustStorePassword);

        // Set up key manager factory and trust manager factory
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, keyStorePassword);

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trustStore);

        // Initialize SSLContext
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
        return context;
    }

    public void startServer() throws IOException {
        SSLServerSocketFactory ssf = sslContext.getServerSocketFactory();
        SSLServerSocket serverSocket = (SSLServerSocket) ssf.createServerSocket(SERVER_PORT);
        System.out.println("Secure server started on port " + SERVER_PORT);

        while (true) {
            SSLSocket clientSocket = (SSLSocket) serverSocket.accept();
            // Handle client connection in a separate thread
            new Thread(new ClientHandler(clientSocket)).start();
        }
    }

    public void connectToPeer(String host, int port) throws IOException {
        SSLSocketFactory sf = sslContext.getSocketFactory();
        SSLSocket socket = (SSLSocket) sf.createSocket(host, port);
        System.out.println("Connected to peer: " + host + ":" + port);

        // Handle peer connection
        // ...
    }

    // ClientHandler class for handling client connections
    class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try (ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
                 ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {

                // Handle communication with the client
                // ...

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Main method to start the SecureNode server
    public static void main(String[] args) throws Exception {
        SecureNode node = new SecureNode();
        node.startServer();
    }

    // ... other methods ...
}
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
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDBBlockchainStorage {

    private MongoCollection<Document> collection;

    public MongoDBBlockchainStorage(MongoDatabase database) {
        this.collection = database.getCollection("blocks");
    }

    public void saveBlock(Block block) {
        Document document = new Document("hash", block.getHash())
                .append("previousHash", block.getPreviousHash())
                // ... other block properties
                ;
        collection.insertOne(document);
    }

    // Method to load the blockchain data from the database
    // ...
}
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BlockTest {

    @Test
    void testBlockCreation() {
        Block block = new Block("Test Data", "Product123", "Details", "00000");
        assertNotNull(block.getHash(), "Block hash should not be null");
        // Add more assertions to validate block integrity
    }
    
    // More tests...
}
class BlockchainIntegrationTest {

    @Test
    void testAddBlock() {
        Blockchain blockchain = new Blockchain();
        Block newBlock = new Block("Test Data", "Product123", "Details", blockchain.getLatestBlock().getHash());
        blockchain.addBlock(newBlock);
        
        assertEquals(newBlock, blockchain.getLatestBlock(), "The new block should be the latest in the chain");
        // More complex tests...
    }
    
    // More tests...
}
class BlockchainSecurityTest {

    @Test
    void testInvalidBlockInsertion() {
        Blockchain blockchain = new Blockchain();
        Block invalidBlock = new Block("Invalid Data", "ProductXYZ", "Invalid Details", "InvalidHash");
        
        blockchain.addBlock(invalidBlock);
        assertNotEquals(invalidBlock, blockchain.getLatestBlock(), "Invalid block should not be added");
    }
    
    // More tests...
}
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MockServer implements Runnable {

    private int port;

    public MockServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    // Handle client connection
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
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
public class StressTestRunner {

    public static void main(String[] args) {
        StressTest test = new StressTest(1000); // For example, 1000 requests
        test.performLoadTest();
        // Analyze results
    }
}
import java.util.concurrent.ConcurrentHashMap;

public class BlockchainCache {
    private ConcurrentHashMap<String, Block> cache = new ConcurrentHashMap<>();

    public void addBlockToCache(Block block) {
        cache.put(block.getHash(), block);
    }

    public Block getBlockFromCache(String hash) {
        return cache.getOrDefault(hash, null);
    }
}
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BlockchainWeakCache {
    private Map<String, WeakReference<Block>> cache = new ConcurrentHashMap<>();

    public void addBlockToCache(Block block) {
        cache.put(block.getHash(), new WeakReference<>(block));
    }

    public Block getBlockFromCache(String hash) {
        WeakReference<Block> blockRef = cache.get(hash);
        return (blockRef != null) ? blockRef.get() : null;
    }
}
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class BlockchainValidator {
    private ExecutorService executor = Executors.newCachedThreadPool();

    public Future<Boolean> validateBlockAsync(Block block) {
        return executor.submit(() -> {
            // Block validation logic
            return true; // Return validation result
        });
    }
}
public class BlockchainDatabase {
    // Assuming you have a method to get your database connection
    public void saveBlocks(List<Block> blocks) {
        // Use batch processing to insert multiple blocks in one go
    }
}
import java.util.zip.GZIPOutputStream;
import java.io.ByteArrayOutputStream;

public class NetworkUtils {
    public static byte[] compressData(byte[] data) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(data.length);
        try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)) {
            gzipOutputStream.write(data);
        }
        return byteArrayOutputStream.toByteArray();
    }
}
public class PerformanceLogger {
    public static void logPerformanceMetrics(String action, long startTime) {
        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("Action: " + action + ", Elapsed Time: " + elapsedTime + "ms");
    }
}

