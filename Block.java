import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

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

        // Method to mine a block
        public void mineBlock(int difficulty) {
            String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * "0" 
            while(!hash.substring(0, difficulty).equals(target)) {
                nonce++;
                hash = calculateHash();
            }
            System.out.println("Block Mined!!! : " + hash);
        }

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

    // Add other getters and setters
}
