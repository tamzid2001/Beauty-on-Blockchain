import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BlockchainApp {

    private static Node node; // Assuming you have a Node class implemented

    public static void main(String[] args) {
        // Initialize blockchain and node
        node = new Node();

        // Create the main window
        JFrame frame = new JFrame("Blockchain Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        // Create UI components
        JTextArea blockchainView = new JTextArea(15, 30);
        blockchainView.setEditable(false);
        JTextField dataField = new JTextField(20);
        JButton addButton = new JButton("Add to Blockchain");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String data = dataField.getText();
                // Simplified data for Block creation
                byte[] signature = new byte[0]; // Assuming you don't have a signature ready
                String validator = node.getNodeId(); // Node itself as validator
                int stake = node.getStake(); // Stake from the node
                String productId = "123"; // Example product ID
                String manufacturingDetails = "Details"; // Example manufacturing details
                String previousHash = node.getLatestBlock().getHash(); // Get previous hash from the latest block
        
                // Create a new block with available data
                Block newBlock = new Block(signature, validator, stake, data, productId, manufacturingDetails, previousHash);
        
                // Add block and update view
                if (node.validateBlock(newBlock)) {
                    node.addBlock(newBlock);
                    blockchainView.setText(node.getBlockchain().toString());
                }
                dataField.setText("");
            }
        });

        // Layout
        JPanel panel = new JPanel();
        panel.add(new JLabel("Enter Data:"));
        panel.add(dataField);
        panel.add(addButton);
        panel.add(new JScrollPane(blockchainView));

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }
}

