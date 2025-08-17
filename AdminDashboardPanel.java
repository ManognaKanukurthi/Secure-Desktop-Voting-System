// --- AdminDashboardPanel.java ---
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.function.Consumer;

public class AdminDashboardPanel extends ImagePanel {
    private final VotingService votingService;

    public AdminDashboardPanel(VotingService service, Consumer<String> navigator) {
        // A corporate/data-centric background for the admin panel
        super("https://images.unsplash.com/photo-1551288049-bebda4e38f71?w=1200&q=80");
        this.votingService = service;

        JLabel titleLabel = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 40));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 50, 1200, 50);
        add(titleLabel);

        JButton addCandidateButton = createDashboardButton("Add Candidate", 150);
        JButton viewCandidatesButton = createDashboardButton("View Candidates", 220);
        JButton viewVotersButton = createDashboardButton("View Voters", 290);
        JButton monitorVotesButton = createDashboardButton("Monitor Votes", 360);
        JButton declareResultsButton = createDashboardButton("Declare Results", 430);
        JButton logoutButton = createDashboardButton("Logout", 500);
        
        add(addCandidateButton);
        add(viewCandidatesButton);
        add(viewVotersButton);
        add(monitorVotesButton);
        add(declareResultsButton);
        add(logoutButton);

        addCandidateButton.addActionListener(e -> addCandidate());
        logoutButton.addActionListener(e -> navigator.accept("WELCOME"));
        
        viewCandidatesButton.addActionListener(e -> showDataDialog("Candidates", votingService.getCandidatesAsString()));
        viewVotersButton.addActionListener(e -> showDataDialog("Registered Voters", votingService.getVotersAsString()));
        monitorVotesButton.addActionListener(e -> showDataDialog("Live Vote Count", votingService.getVoteCountAsString()));
        declareResultsButton.addActionListener(e -> showDataDialog("Final Results", votingService.getResultsAsString()));
    }

    private JButton createDashboardButton(String text, int yPos) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 18));
        button.setBounds(450, yPos, 300, 50);
        return button;
    }

    private void addCandidate() {
        String name = JOptionPane.showInputDialog(this, "Enter candidate name:");
        if (name == null || name.trim().isEmpty()) return;
        String party = JOptionPane.showInputDialog(this, "Enter candidate party:");
        if (party == null || party.trim().isEmpty()) return;
        try {
            votingService.addCandidate(name, party);
            JOptionPane.showMessageDialog(this, "Candidate added successfully.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error adding candidate: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void showDataDialog(String title, String data) {
        JTextArea textArea = new JTextArea(data);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        JOptionPane.showMessageDialog(this, scrollPane, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
