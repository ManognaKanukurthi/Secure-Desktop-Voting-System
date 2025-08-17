// --- Voter.java ---
import java.io.Serializable;
import java.util.Objects;

public class Voter implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String name;
    private final String voterId;
    private final int passwordHash;

    public Voter(String name, String voterId, String password) {
        this.name = name;
        this.voterId = voterId;
        this.passwordHash = password.hashCode(); // Simple hashing. Use a stronger algorithm like BCrypt in a real app.
    }
    
    // Constructor to use when loading from database
    public Voter(String name, String voterId, int passwordHash) {
        this.name = name;
        this.voterId = voterId;
        this.passwordHash = passwordHash;
    }

    public String getName() {
        return name;
    }

    public String getVoterId() {
        return voterId;
    }
    
    public int getPasswordHash() {
        return passwordHash;
    }

    public boolean checkPassword(String password) {
        return password.hashCode() == this.passwordHash;
    }

    @Override
    public String toString() {
        return "Voter{ " + "name='" + name + '\'' + ", voterId='" + voterId + '\'' + " }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voter voter = (Voter) o;
        return Objects.equals(voterId, voter.voterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voterId);
    }
}
