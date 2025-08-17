// --- RegistrationPanel.java ---
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.function.Consumer;

public class RegistrationPanel extends ImagePanel {
    public RegistrationPanel(VotingService votingService, Consumer<String> navigator) {
        // A clean and simple background for a registration form
        super("https://images.unsplash.com/photo-1585336261022-680e2954cb8b?w=1200&q=80");

        JLabel titleLabel = new JLabel("Voter Registration", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 40));
        titleLabel.setBounds(0, 150, 1200, 50);
        add(titleLabel);

        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        nameLabel.setBounds(400, 250, 150, 30);
        add(nameLabel);
        JTextField nameField = new JTextField();
        nameField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        nameField.setBounds(550, 250, 250, 30);
        add(nameField);

        JLabel voterIdLabel = new JLabel("Voter ID:");
        voterIdLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        voterIdLabel.setBounds(400, 300, 150, 30);
        add(voterIdLabel);
        JTextField voterIdField = new JTextField();
        voterIdField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        voterIdField.setBounds(550, 300, 250, 30);
        add(voterIdField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        passwordLabel.setBounds(400, 350, 150, 30);
        add(passwordLabel);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        passwordField.setBounds(550, 350, 250, 30);
        add(passwordField);

        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        registerButton.setBounds(550, 400, 150, 40);
        add(registerButton);
        
        JButton backButton = new JButton("Back to Login");
        backButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        backButton.setBounds(380, 400, 150, 40);
        backButton.addActionListener(e -> navigator.accept("VOTER_LOGIN"));
        add(backButton);

        registerButton.addActionListener(e -> {
            String name = nameField.getText();
            String voterId = voterIdField.getText();
            String password = new String(passwordField.getPassword());
            try {
                votingService.registerVoter(name, voterId, password);
                JOptionPane.showMessageDialog(this, "Registration successful! You can now log in.");
                navigator.accept("VOTER_LOGIN");
            } catch (IllegalArgumentException | SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Registration Failed", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
