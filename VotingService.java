// --- VotingService.java ---
// This should be in a file named VotingService.java
// ** UPDATED VERSION **

import java.sql.SQLException;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class VotingService {
    private final Map<String, Voter> voters = new HashMap<>();
    private final Map<Integer, Candidate> candidates = new HashMap<>();
    private final Set<String> votedVoters = new HashSet<>();

    /**
     * Loads all data (voters, candidates, votes) from the database.
     */
    public void loadData() throws SQLException {
        voters.clear();
        voters.putAll(DatabaseService.loadVoters());

        candidates.clear();
        candidates.putAll(DatabaseService.loadCandidates());

        votedVoters.clear();
        votedVoters.addAll(DatabaseService.loadVotedVoters());
    }

    // --- Voter Management ---

    public void registerVoter(String name, String voterId, String password) throws SQLException {
        if (voters.containsKey(voterId)) {
            throw new IllegalArgumentException("Voter ID already exists.");
        }
        if (name == null || name.trim().isEmpty() || voterId == null || voterId.trim().isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Name, Voter ID, and password cannot be empty.");
        }
        Voter newVoter = new Voter(name, voterId, password);
        DatabaseService.saveVoter(newVoter); // Persist to DB
        voters.put(voterId, newVoter); // Update in-memory map
    }

    public Voter authenticateVoter(String voterId, String password) {
        Voter voter = voters.get(voterId);
        if (voter != null && voter.checkPassword(password)) {
            return voter;
        }
        return null;
    }
    
    public String getVotersAsString() {
        if (voters.isEmpty()) {
            return "No voters have registered yet.";
        }
        return voters.values().stream()
            .map(Voter::toString)
            .collect(Collectors.joining("\n"));
    }

    // --- Candidate Management ---

    public void addCandidate(String name, String party) throws SQLException {
        if (name == null || name.trim().isEmpty() || party == null || party.trim().isEmpty()) {
            throw new IllegalArgumentException("Candidate name and party cannot be empty.");
        }
        // Save to DB and get the generated ID
        Candidate newCandidate = DatabaseService.saveCandidate(name, party);
        candidates.put(newCandidate.getId(), newCandidate); // Update in-memory map
    }

    public String getCandidatesAsString() {
        if (candidates.isEmpty()) {
            return "No candidates have been added yet.";
        }
        return candidates.values().stream()
            .map(Candidate::toString)
            .collect(Collectors.joining("\n"));
    }

    public Collection<Candidate> getAllCandidates() {
        return candidates.values();
    }

    // --- Voting Process ---

    public void castVote(String voterId, int candidateId) throws SQLException {
        if (!voters.containsKey(voterId)) {
            throw new IllegalArgumentException("Voter ID not found.");
        }
        if (votedVoters.contains(voterId)) {
            throw new IllegalArgumentException("This voter has already voted.");
        }
        Candidate candidate = candidates.get(candidateId);
        if (candidate == null) {
            throw new IllegalArgumentException("Candidate ID not found.");
        }

        DatabaseService.saveVote(voterId, candidateId); // Persist vote to DB
        candidate.incrementVoteCount(); // Update in-memory object
        votedVoters.add(voterId); // Update in-memory set
    }
    
    public boolean hasVoted(String voterId) {
        return votedVoters.contains(voterId);
    }

    // --- Admin Functionality ---

    public String getVoteCountAsString() {
        if (candidates.isEmpty()) {
            return "No candidates available.";
        }
        return candidates.values().stream()
                .sorted(Comparator.comparingInt(Candidate::getVoteCount).reversed())
                .map(c -> String.format("Candidate: %-20s | Party: %-15s | Votes: %d",
                        c.getName(), c.getParty(), c.getVoteCount()))
                .collect(Collectors.joining("\n"));
    }

    public String getResultsAsString() {
        if (candidates.isEmpty() || votedVoters.isEmpty()) {
            return "No votes have been cast yet. Cannot declare results.";
        }

        Candidate winner = candidates.values().stream()
                .max(Comparator.comparingInt(Candidate::getVoteCount))
                .orElse(null);

        StringBuilder results = new StringBuilder();
        results.append("--- Final Tally ---\n");
        results.append(getVoteCountAsString());
        
        if (winner != null) {
            results.append("\n\n--- Winner ---\n");
            results.append(String.format("The winner is %s from the %s party with %d votes!",
                    winner.getName(), winner.getParty(), winner.getVoteCount()));
        } else {
            results.append("\n\nThere was no clear winner.");
        }
        return results.toString();
    }
}
