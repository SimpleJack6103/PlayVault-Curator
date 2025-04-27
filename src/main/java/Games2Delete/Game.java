package Games2Delete;

public class Game {
    private String gameName;
    private double gameSize; // in GB
    private int daysSinceLastPlayed; // days ago
    private int totalPlaytime; // in hours
    private boolean isMultiplayer; // true if multiplayer, false if single-player
    private double deletionRanking; // Computed score for deciding uninstall priority

    // Constructor
    public Game(String gameName, double gameSize, int daysSinceLastPlayed, int totalPlaytime, boolean isMultiplayer) {
        this.gameName = gameName;
        this.gameSize = gameSize;
        this.daysSinceLastPlayed = daysSinceLastPlayed;
        this.totalPlaytime = totalPlaytime;
        this.isMultiplayer = isMultiplayer;
    }

    // Getters
    public String getGameName() { return gameName; }
    public double getGameSize() { return gameSize; }
    public int getDaysSinceLastPlayed() { return daysSinceLastPlayed; }
    public int getTotalPlaytime() { return totalPlaytime; }
    public boolean isMultiplayer() { return isMultiplayer; }
    public double getDeletionRanking() { return deletionRanking; }

    // Setter for deletion ranking (calculated by DeletionRank class)
    public void setDeletionRanking(double deletionRanking) {
        this.deletionRanking = deletionRanking;
    }
}
