// --- WelcomePanel.java ---
import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class WelcomePanel extends ImagePanel {
    public WelcomePanel(Consumer<String> navigator) {
        // A modern, welcoming background image
        super("https://images.unsplash.com/photo-1590883884322-3b7a5a8a1b3a?w=1200&q=80");

        JLabel titleLabel = new JLabel("Online Voting System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 48));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 100, 1200, 60);
        add(titleLabel);

        JLabel subtitleLabel = new JLabel("Please select your role to continue", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 24));
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setBounds(0, 160, 1200, 40);
        add(subtitleLabel);

        JButton voterButton = new JButton("I am a Voter");
        voterButton.setFont(new Font("SansSerif", Font.BOLD, 22));
        voterButton.setBounds(450, 300, 300, 60);
        voterButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        voterButton.addActionListener(e -> navigator.accept("VOTER_LOGIN"));
        add(voterButton);

        JButton adminButton = new JButton("I am an Admin");
        adminButton.setFont(new Font("SansSerif", Font.BOLD, 22));
        adminButton.setBounds(450, 400, 300, 60);
        adminButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        adminButton.addActionListener(e -> navigator.accept("ADMIN_LOGIN"));
        add(adminButton);
    }
}
