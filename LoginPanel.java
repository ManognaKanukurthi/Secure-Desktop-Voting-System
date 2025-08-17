// --- LoginPanel.java ---
import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class LoginPanel extends ImagePanel {
    public LoginPanel(String title, VotingService votingService, boolean isVoterLogin,
                      Consumer<String> navigator, Consumer<Voter> voterSetter) {
        // A professional and secure-looking background for logging in
        super("https://images.unsplash.com/photo-1554224155-6726b3ff858f?w=1200&q=80");

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 40));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBounds(0, 150, 1200, 50);
        add(titleLabel);

        JLabel idLabel = new JLabel(isVoterLogin ? "Voter ID:" : "Admin Username:");
        idLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        idLabel.setForeground(Color.BLACK);
        idLabel.setBounds(400, 250, 150, 30);
        add(idLabel);

        JTextField idField = new JTextField();
        idField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        idField.setBounds(550, 250, 250, 30);
        add(idField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        passwordLabel.setForeground(Color.BLACK);
        passwordLabel.setBounds(400, 300, 150, 30);
        add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        passwordField.setBounds(550, 300, 250, 30);
        add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        loginButton.setBounds(550, 350, 120, 40);
        add(loginButton);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        backButton.setBounds(400, 350, 100, 40);
        backButton.addActionListener(e -> navigator.accept("WELCOME"));
        add(backButton);

        if (isVoterLogin) {
            JButton registerLink = new JButton("New user? Register here.");
            registerLink.setFont(new Font("SansSerif", Font.PLAIN, 14));
            registerLink.setBounds(550, 400, 250, 30);
            registerLink.setForeground(Color.BLUE);
            registerLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
            registerLink.setBorderPainted(false);
            registerLink.setContentAreaFilled(false);
            registerLink.addActionListener(e -> navigator.accept("REGISTER"));
            add(registerLink);
        }

        loginButton.addActionListener(e -> {
            String id = idField.getText();
            String password = new String(passwordField.getPassword());
            if (isVoterLogin) {
                Voter voter = votingService.authenticateVoter(id, password);
                if (voter != null) {
                    JOptionPane.showMessageDialog(this, "Login successful! Welcome, " + voter.getName() + ".");
                    voterSetter.accept(voter);
                    navigator.accept("VOTER_DASHBOARD");
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid Voter ID or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                if ("admin".equals(id) && "admin123".equals(password)) {
                    JOptionPane.showMessageDialog(this, "Admin login successful!");
                    navigator.accept("ADMIN_DASHBOARD");
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid admin username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
