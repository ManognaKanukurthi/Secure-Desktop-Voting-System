// --- GuiApp.java ---
// This is the new main entry point for the application.

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class GuiApp {
    // --- Central Components ---
    private static JFrame frame;
    private static CardLayout cardLayout;
    private static JPanel mainPanel;
    private static VotingService votingService;

    public static void main(String[] args) {
        // Initialize the voting service and load data from the database
        try {
            votingService = new VotingService();
            votingService.loadData();
        } catch (SQLException e) {
            // Show an error message if the database connection fails
            JOptionPane.showMessageDialog(null,
                "Could not connect to the database. Please check your settings and ensure the database server is running.",
                "Database Connection Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return; // Exit the application
        }

        // --- Create the Main Window (JFrame) ---
        frame = new JFrame("Secure Online Voting System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 700);
        frame.setLocationRelativeTo(null); // Center the window

        // --- Create the Main Panel with CardLayout ---
        // CardLayout allows us to switch between different panels (screens)
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // --- Create Instances of All Screen Panels ---
        // We pass the votingService and a reference to this GuiApp to each panel
        // so they can perform actions and trigger navigation.
        WelcomePanel welcomePanel = new WelcomePanel(GuiApp::showPanel);
        LoginPanel voterLoginPanel = new LoginPanel("Voter Login", votingService, true, GuiApp::showPanel, GuiApp::setCurrentVoter);
        LoginPanel adminLoginPanel = new LoginPanel("Admin Login", votingService, false, GuiApp::showPanel, null);
        RegistrationPanel registrationPanel = new RegistrationPanel(votingService, GuiApp::showPanel);
        AdminDashboardPanel adminDashboardPanel = new AdminDashboardPanel(votingService, GuiApp::showPanel);
        VoterDashboardPanel voterDashboardPanel = new VoterDashboardPanel(GuiApp::showPanel);
        CastVotePanel castVotePanel = new CastVotePanel(votingService, GuiApp::showPanel);

        // --- Add Panels to the CardLayout ---
        // Each panel is added with a unique name (string) so we can switch to it.
        mainPanel.add(welcomePanel, "WELCOME");
        mainPanel.add(voterLoginPanel, "VOTER_LOGIN");
        mainPanel.add(adminLoginPanel, "ADMIN_LOGIN");
        mainPanel.add(registrationPanel, "REGISTER");
        mainPanel.add(adminDashboardPanel, "ADMIN_DASHBOARD");
        mainPanel.add(voterDashboardPanel, "VOTER_DASHBOARD");
        mainPanel.add(castVotePanel, "CAST_VOTE");


        // Add the main panel to the frame and make it visible
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    /**
     * A central method to switch which panel is currently visible.
     * @param panelName The name of the panel to show (e.g., "WELCOME", "LOGIN").
     */
    public static void showPanel(String panelName) {
        // If we are navigating to the voter dashboard, we need to refresh its content
        if ("VOTER_DASHBOARD".equals(panelName)) {
            VoterDashboardPanel.updateWelcomeMessage();
        }
        // If we are navigating to the cast vote screen, refresh the candidate list
        if ("CAST_VOTE".equals(panelName)) {
            CastVotePanel.refreshCandidates();
        }
        cardLayout.show(mainPanel, panelName);
    }

    /**
     * A central method to set the currently logged-in voter.
     * @param voter The voter object who has just logged in.
     */
    public static void setCurrentVoter(Voter voter) {
        VoterDashboardPanel.setCurrentVoter(voter);
        CastVotePanel.setCurrentVoter(voter);
    }
}
