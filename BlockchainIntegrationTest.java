import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BlockchainIntegrationTest {

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