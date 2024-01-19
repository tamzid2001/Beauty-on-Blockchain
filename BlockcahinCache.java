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