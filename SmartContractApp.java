import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SmartContractApp {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Beauty Industry Smart Contract Creator");
        frame.setSize(500, 400); // Adjusted size to accommodate more fields
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);

        placeComponents(panel);
        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        // Product Name
        JLabel nameLabel = new JLabel("Product Name:");
        nameLabel.setBounds(10, 20, 80, 25);
        panel.add(nameLabel);

        JTextField nameText = new JTextField(20);
        nameText.setBounds(100, 20, 165, 25);
        panel.add(nameText);

        // Product Description
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setBounds(10, 50, 80, 25);
        panel.add(descriptionLabel);

        JTextField descriptionText = new JTextField(20);
        descriptionText.setBounds(100, 50, 165, 25);
        panel.add(descriptionText);

        // Quantity
        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setBounds(10, 80, 80, 25);
        panel.add(quantityLabel);

        JTextField quantityText = new JTextField(20);
        quantityText.setBounds(100, 80, 165, 25);
        panel.add(quantityText);

        // Price
        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setBounds(10, 110, 80, 25);
        panel.add(priceLabel);

        JTextField priceText = new JTextField(20);
        priceText.setBounds(100, 110, 165, 25);
        panel.add(priceText);

        // Delivery Terms
        JLabel deliveryLabel = new JLabel("Delivery Terms:");
        deliveryLabel.setBounds(10, 140, 100, 25);
        panel.add(deliveryLabel);

        JTextField deliveryText = new JTextField(20);
        deliveryText.setBounds(100, 140, 165, 25);
        panel.add(deliveryText);

        // Create Contract Button
        JButton createButton = new JButton("Create Contract");
        createButton.setBounds(10, 170, 160, 25);
        panel.add(createButton);

        // Action listener for the create button
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productName = nameText.getText();
                String productDescription = descriptionText.getText();
                String quantity = quantityText.getText();
                String price = priceText.getText();
                String deliveryTerms = deliveryText.getText();

                // Validate data (Basic validation logic)
                if (productName.isEmpty() || productDescription.isEmpty() || quantity.isEmpty() || price.isEmpty() || deliveryTerms.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Please fill in all fields.");
                    return;
                }

                // Create and send the smart contract to the blockchain (Not implemented)
                JOptionPane.showMessageDialog(panel, "Contract Created Successfully (Not actually implemented)");
            }
        });
    }
}
