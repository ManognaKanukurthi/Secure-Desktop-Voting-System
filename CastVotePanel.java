// --- CastVotePanel.java ---
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.function.Consumer;

public class CastVotePanel extends ImagePanel {
    private static Voter currentVoter;
    private static VotingService votingService;
    private static JPanel candidateButtonsPanel;
    private static Consumer<String> navigator;

    public CastVotePanel(VotingService service, Consumer<String> nav) {
        // A simple, focused background for the voting action
        super("https://images.unsplash.com/photo-1560132803-a4caf5876778?w=1200&q=80");
        votingService = service;
        navigator = nav;

        JLabel titleLabel = new JLabel("Choose Your Candidate", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 40));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBounds(0, 50, 1200, 50);
        add(titleLabel);

        candidateButtonsPanel = new JPanel();
        candidateButtonsPanel.setLayout(new BoxLayout(candidateButtonsPanel, BoxLayout.Y_AXIS));
        candidateButtonsPanel.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(candidateButtonsPanel);
        scrollPane.setBounds(350, 150, 500, 400);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        add(scrollPane);
        
        JButton backButton = new JButton("Back to Dashboard");
        backButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        backButton.setBounds(500, 580, 200, 40);
        backButton.addActionListener(e -> navigator.accept("VOTER_DASHBOARD"));
        add(backButton);
    }

    public static void setCurrentVoter(Voter voter) {
        currentVoter = voter;
    }

    public static void refreshCandidates() {
        candidateButtonsPanel.removeAll();
        if (votingService.hasVoted(currentVoter.getVoterId())) {
            JLabel votedLabel = new JLabel("You have already cast your vote.", SwingConstants.CENTER);
            votedLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
            votedLabel.setForeground(Color.RED);
            candidateButtonsPanel.add(votedLabel);
        } else {
            for (Candidate candidate : votingService.getAllCandidates()) {
                JButton candidateButton = new JButton(
                    String.format("<html><div style='text-align: center;'><b>%s</b><br/>%s</html>",
                        candidate.getName(), candidate.getParty()));
                candidateButton.setFont(new Font("SansSerif", Font.PLAIN, 18));
                candidateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                candidateButton.setMaximumSize(new Dimension(400, 80));
                candidateButton.setMinimumSize(new Dimension(400, 80));
                candidateButton.setMargin(new Insets(10, 10, 10, 10));
                candidateButton.addActionListener(e -> {
                    int choice = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to vote for " + candidate.getName() + "?",
                        "Confirm Vote", JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        try {
                            votingService.castVote(currentVoter.getVoterId(), candidate.getId());
                            JOptionPane.showMessageDialog(null, "Thank you for voting!");
                            navigator.accept("VOTER_DASHBOARD");
                        } catch (SQLException | IllegalArgumentException ex) {
                            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Voting Failed", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
                candidateButtonsPanel.add(candidateButton);
                candidateButtonsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
            }
        }
        candidateButtonsPanel.revalidate();
        candidateButtonsPanel.repaint();
    }
}
