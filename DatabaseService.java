// --- DatabaseService.java ---
import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DatabaseService {
    // !!! IMPORTANT: CONFIGURE YOUR DATABASE CONNECTION HERE !!!
    private static final String DB_URL = "jdbc:mysql://localhost:3306/voting_system";
    private static final String USER = "root"; // e.g., "root"
    private static final String PASSWORD = "Sairam@2004";

    /**
     * Establishes a connection to the database.
     * @return A Connection object.
     * @throws SQLException if a database access error occurs.
     */
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    // --- Load Methods ---

    public static Map<String, Voter> loadVoters() throws SQLException {
        Map<String, Voter> voters = new HashMap<>();
        String sql = "SELECT voter_id, name, password_hash FROM voters";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                String voterId = rs.getString("voter_id");
                String name = rs.getString("name");
                int passwordHash = rs.getInt("password_hash");
                voters.put(voterId, new Voter(name, voterId, passwordHash));
            }
        }
        return voters;
    }

    public static Map<Integer, Candidate> loadCandidates() throws SQLException {
        Map<Integer, Candidate> candidates = new HashMap<>();
        // SQL query to get candidates and their vote counts
        String sql = "SELECT c.id, c.name, c.party, COUNT(v.voter_id) as vote_count " +
                     "FROM candidates c " +
                     "LEFT JOIN votes v ON c.id = v.candidate_id " +
                     "GROUP BY c.id, c.name, c.party";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String party = rs.getString("party");
                int voteCount = rs.getInt("vote_count");
                candidates.put(id, new Candidate(id, name, party, voteCount));
            }
        }
        return candidates;
    }

    public static Set<String> loadVotedVoters() throws SQLException {
        Set<String> votedVoters = new HashSet<>();
        String sql = "SELECT voter_id FROM votes";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                votedVoters.add(rs.getString("voter_id"));
            }
        }
        return votedVoters;
    }

    // --- Save Methods ---

    public static void saveVoter(Voter voter) throws SQLException {
        String sql = "INSERT INTO voters (voter_id, name, password_hash) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, voter.getVoterId());
            pstmt.setString(2, voter.getName());
            pstmt.setInt(3, voter.getPasswordHash());
            pstmt.executeUpdate();
        }
    }

    public static Candidate saveCandidate(String name, String party) throws SQLException {
        String sql = "INSERT INTO candidates (name, party) VALUES (?, ?)";
        int generatedId = -1;
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, name);
            pstmt.setString(2, party);
            pstmt.executeUpdate();
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating candidate failed, no ID obtained.");
                }
            }
        }
        return new Candidate(generatedId, name, party);
    }

    public static void saveVote(String voterId, int candidateId) throws SQLException {
        String sql = "INSERT INTO votes (voter_id, candidate_id) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, voterId);
            pstmt.setInt(2, candidateId);
            pstmt.executeUpdate();
        }
    }
}
