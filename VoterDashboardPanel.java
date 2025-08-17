// --- VoterDashboardPanel.java ---
import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class VoterDashboardPanel extends ImagePanel {
    private static Voter currentVoter;
    private static JLabel welcomeLabel;

    public VoterDashboardPanel(Consumer<String> navigator) {
        // An inspiring, patriotic background for the voter dashboard
        super("https://images.unsplash.com/photo-1524492412937-b28074a5d7da?w=1200&q=80");

        welcomeLabel = new JLabel("Welcome!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 40));
        welcomeLabel.setForeground(Color.BLACK);
        welcomeLabel.setBounds(0, 150, 1200, 50);
        add(welcomeLabel);

        JButton castVoteButton = new JButton("Cast Your Vote");
        castVoteButton.setFont(new Font("SansSerif", Font.BOLD, 22));
        castVoteButton.setBounds(450, 300, 300, 60);
        add(castVoteButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("SansSerif", Font.BOLD, 22));
        logoutButton.setBounds(450, 400, 300, 60);
        add(logoutButton);

        castVoteButton.addActionListener(e -> navigator.accept("CAST_VOTE"));
        logoutButton.addActionListener(e -> {
            currentVoter = null;
            navigator.accept("WELCOME");
        });
    }

    public static void setCurrentVoter(Voter voter) {
        currentVoter = voter;
    }

    public static void updateWelcomeMessage() {
        if (currentVoter != null) {
            welcomeLabel.setText("Welcome, " + currentVoter.getName() + "!");
        }
    }
}
