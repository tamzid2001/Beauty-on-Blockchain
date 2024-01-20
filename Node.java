import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Node {
    private int port;
    private List<Block> blockchain;
    private List<Socket> peers;
    private String nodeId;
    private int stake;

    public Node(String nodeId, int port, int initialStake, List<Block> blockchain) {
        this.port = port;
        this.blockchain = new ArrayList<>();
        this.peers = new ArrayList<>();
        this.nodeId = nodeId;
        this.stake = initialStake;
    }

    public Node(){
        this.port = 8080;
        this.blockchain = new ArrayList<>();
        this.peers = new ArrayList<>();
        this.nodeId = "1234";
        this.stake = 0;
    }

        // Getters and setters
        public String getNodeId() {
            return nodeId;
        }
    
        public void setNodeId(String nodeId) {
            this.nodeId = nodeId;
        }
    
        public int getStake() {
            return stake;
        }
    
        public void setStake(int stake) {
            this.stake = stake;
        }
    
        public List<Block> getBlockchain() {
            return blockchain;
        }
    
        public void setBlockchain(List<Block> blockchain) {
            this.blockchain = blockchain;
        }

        // Method to validate a new block
    public boolean validateBlock(Block newBlock) {
        String expectedValidator = selectValidator();

        if (newBlock.getValidator().equals(expectedValidator)) {
            // Additional validation checks can be added here (e.g., hash, previous hash)
            return true;
        }

        return false;
    }

    // Method to select a block validator based on stake
    private String selectValidator() {
        // Implement the stake-based validator selection logic
        // This is a simplified version and should be more sophisticated in a real implementation
        int totalStake = blockchain.stream().mapToInt(Block::getStake).sum();
        int randomPoint = new Random().nextInt(totalStake);
        int runningSum = 0;

        for (Block block : blockchain) {
            runningSum += block.getStake();
            if (runningSum > randomPoint) {
                return block.getValidator();
            }
        }

        return null; // Fallback in case no validator is selected
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

        // Method to create and mine a new block
        public void createAndMineBlock(String data) {
            Block newBlock = new Block();
            newBlock.mineBlock(difficulty);
            addBlock(newBlock);
            broadcastNewBlock(newBlock);
        }

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
        Node node = new Node();
        node.start();
    }
}