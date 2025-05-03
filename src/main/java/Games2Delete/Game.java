package Games2Delete;

/**
 * Represents a game candidate for deletion, with metadata used to rank it.
 */
public class Game {
    private String name;
    private double sizeGB;
    private boolean recentlyPlayed;
    private int totalPlaytimeHours;
    private double score;

    public Game(String name, double sizeGB, boolean recentlyPlayed, int totalPlaytimeHours) {
        this.name = name;
        this.sizeGB = sizeGB;
        this.recentlyPlayed = recentlyPlayed;
        this.totalPlaytimeHours = totalPlaytimeHours;
    }

    public String getName() {
        return name;
    }

    public double getSizeGB() {
        return sizeGB;
    }

    public boolean isRecentlyPlayed() {
        return recentlyPlayed;
    }

    public int getTotalPlaytimeHours() {
        return totalPlaytimeHours;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setDeletionRanking(double ranking) {
        setScore(ranking);
    }

    public double getDeletionRanking() {
        return getScore();
    }
}
