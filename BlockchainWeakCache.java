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