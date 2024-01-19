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