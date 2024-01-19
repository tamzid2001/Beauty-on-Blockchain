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