import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class guidraft extends JFrame implements ActionListener {
    private JComboBox<String> itemComboBox;
    private JTextField quantityField;
    private JButton addItemButton;
    private JButton calculateButton;
    private JButton payButton;
    private JTextArea itemListArea;
    private JLabel totalPriceLabel;
    private JRadioButton cashRadioButton;
    private JRadioButton cardRadioButton;
    private JTextField cardHolderNameField;
    private JTextField accountNumberField;
    private JTextField amountPaidField;
    private double totalPrice;
    private boolean isTotalCalculated;

    private Map<String, Double> menuItems;

    public guidraft() {
        
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.CYAN);
        JLabel greetingLabel = new JLabel("RESTAURANT MANAGEMENT SYSTEM");
        greetingLabel.setFont(new Font("Courier", Font.ITALIC, 20));
        greetingLabel.setForeground(Color.BLUE);
        headerPanel.add(greetingLabel);
 
        setTitle("Restaurant Management System");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        menuItems = new HashMap<>();
        menuItems.put("Grilled Roast Beef", 2000.00);
        menuItems.put("Seafood Pack", 1580.00);
        menuItems.put("Spicy Pork Rim", 1330.00);
        menuItems.put("Baked Fish With Chili Sauce", 700.00);
        menuItems.put("Buttered Shirimp Bucket", 1645.78);

        JLabel itemLabel = new JLabel("Item:");
        itemComboBox = new JComboBox<>(menuItems.keySet().toArray(new String[0]));
        itemComboBox.addActionListener(this);

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityField = new JTextField(5);

        addItemButton = new JButton("Add Item");
        addItemButton.addActionListener(this);
        calculateButton = new JButton("Calculate Total");
        calculateButton.addActionListener(this);
        payButton = new JButton("Pay");
        payButton.addActionListener(this);
        payButton.setEnabled(false);


        itemListArea = new JTextArea();
        itemListArea.setEditable(false);
        itemListArea.setBackground(Color.CYAN);
        totalPriceLabel = new JLabel("Total Price: P0.00");

        cashRadioButton = new JRadioButton("Cash");
        cardRadioButton = new JRadioButton("Card");
        cashRadioButton.setVisible(false);
        cardRadioButton.setVisible(false);
        cashRadioButton.setSelected(true);
        JLabel cardHolderNameLabel = new JLabel("Card Holder's Name:");
        cardHolderNameField = new JTextField(20);
        JLabel accountNumberLabel = new JLabel("Account Number:");
        accountNumberField = new JTextField(20);

        JLabel amountPaidLabel = new JLabel("Amount Paid:");
        amountPaidField = new JTextField(10);

        ButtonGroup paymentGroup = new ButtonGroup();
        paymentGroup.add(cashRadioButton);
        paymentGroup.add(cardRadioButton);

  
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBackground(Color.white);

  
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        inputPanel.add(itemLabel, constraints);

        constraints.gridx = 1;
        inputPanel.add(itemComboBox, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        inputPanel.add(quantityLabel, constraints);

        constraints.gridx = 1;
        inputPanel.add(quantityField, constraints);

        constraints.gridx = 2;
        inputPanel.add(addItemButton, constraints);

        constraints.gridy = 2;
        constraints.gridx = 1;
        inputPanel.add(calculateButton, constraints);

        constraints.gridy = 3;
        inputPanel.add(payButton, constraints);

        constraints.gridy = 4;
        inputPanel.add(cashRadioButton, constraints);

        constraints.gridx = 2;
        inputPanel.add(cardRadioButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        inputPanel.add(cardHolderNameLabel, constraints);

        constraints.gridy = 6;
        inputPanel.add(cardHolderNameField, constraints);

        constraints.gridy = 7;
        inputPanel.add(accountNumberLabel, constraints);

        constraints.gridy = 8;
        inputPanel.add(accountNumberField, constraints);

        constraints.gridy = 9;
        constraints.gridwidth = 1;
        inputPanel.add(amountPaidLabel, constraints);

        constraints.gridx = 1;
        inputPanel.add(amountPaidField, constraints);

    

        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.add(new JScrollPane(itemListArea), BorderLayout.CENTER);
        listPanel.add(totalPriceLabel, BorderLayout.SOUTH);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(headerPanel, BorderLayout.NORTH);
        getContentPane().add(inputPanel, BorderLayout.CENTER);
        getContentPane().add(listPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addItemButton) {
            String itemName = (String) itemComboBox.getSelectedItem();
            int quantity = Integer.parseInt(quantityField.getText());
            double itemPrice = menuItems.get(itemName);

 
            double subtotal = itemPrice * quantity;


            itemListArea.append(itemName + " - P" + itemPrice + " x " + quantity + " = P" + subtotal + "\n");

            totalPrice += subtotal;
            itemComboBox.setSelectedIndex(0);
            quantityField.setText("");

            calculateButton.setEnabled(true);
            payButton.setEnabled(true);
        } else if (e.getSource() == calculateButton) {

            totalPriceLabel.setText("Total Price: P" + String.format("%.2f", totalPrice));
            cashRadioButton.setVisible(true);
            cardRadioButton.setVisible(true);
            calculateButton.setEnabled(false);
            isTotalCalculated = true;
        } else if (e.getSource() == payButton) {
            
            if (isTotalCalculated) {
               
                if (cashRadioButton.isSelected()) {
                    
                    
                    double amountPaid = Double.parseDouble(amountPaidField.getText());
                    double change = amountPaid - totalPrice;

                    String paymentInfo = "Payment Method: Cash" +
                            "\nAmount Paid: P" + String.format("%.2f", amountPaid) +
                            "\nChange: P" + String.format("%.2f", change);
                    itemListArea.append(paymentInfo + "\n");
                } else if (cardRadioButton.isSelected()) {
                   
                    String cardHolderName = cardHolderNameField.getText();
                    String accountNumber = accountNumberField.getText();
                    String paymentInfo = "Payment Method: Card" +
                            "\nCard Holder's Name: " + cardHolderName +
                            "\nAccount Number: " + accountNumber;
                    itemListArea.append(paymentInfo + "\n");
                }

               
                itemListArea.append("Payment received. Thank you!");
                payButton.setEnabled(false);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            guidraft system = new guidraft();
            system.setVisible(true);
        });
    }
}
