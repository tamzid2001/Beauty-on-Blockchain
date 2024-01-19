@RestController
@RequestMapping("/api/blockchain")
public class BlockchainController {

    private Blockchain blockchain;

    @PostMapping("/add")
    public ResponseEntity<?> addData(@RequestBody String data) {
        // Add data to the blockchain
        return ResponseEntity.ok("Data added");
    }

    @GetMapping
    public List<Block> getBlockchain() {
        // Return the blockchain data
        return blockchain.getChain();
    }
}
