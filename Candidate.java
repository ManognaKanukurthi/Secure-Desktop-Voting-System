// --- Candidate.java ---
import java.io.Serializable;

public class Candidate implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int id;
    private final String name;
    private final String party;
    private int voteCount;

    public Candidate(int id, String name, String party) {
        this.id = id;
        this.name = name;
        this.party = party;
        this.voteCount = 0;
    }
    
    public Candidate(int id, String name, String party, int voteCount) {
        this.id = id;
        this.name = name;
        this.party = party;
        this.voteCount = voteCount;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public String getParty() {
        return party;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void incrementVoteCount() {
        this.voteCount++;
    }

    @Override
    public String toString() {
        return String.format("ID: %-3d | Name: %-20s | Party: %-15s", id, name, party);
    }
}
