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