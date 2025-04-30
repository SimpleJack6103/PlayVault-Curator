// File: Games2Delete/Game.java
package Games2Delete;

/**
 * Represents a game candidate for deletion, with metadata used to rank it.
 */
public class Game {
    private String name;
    private double sizeGB;
    private int daysSinceLastPlayed;
    private int totalPlaytimeHours;
    private boolean multiplayer;
    private double score;

    public Game(String name, double sizeGB, int daysSinceLastPlayed, int totalPlaytimeHours, boolean multiplayer) {
        this.name = name;
        this.sizeGB = sizeGB;
        this.daysSinceLastPlayed = daysSinceLastPlayed;
        this.totalPlaytimeHours = totalPlaytimeHours;
        this.multiplayer = multiplayer;
    }

    // --- Existing getters & setters ---

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSizeGB() {
        return sizeGB;
    }

    public void setSizeGB(double sizeGB) {
        this.sizeGB = sizeGB;
    }

    public int getDaysSinceLastPlayed() {
        return daysSinceLastPlayed;
    }

    public void setDaysSinceLastPlayed(int daysSinceLastPlayed) {
        this.daysSinceLastPlayed = daysSinceLastPlayed;
    }

    public int getTotalPlaytimeHours() {
        return totalPlaytimeHours;
    }

    public void setTotalPlaytimeHours(int totalPlaytimeHours) {
        this.totalPlaytimeHours = totalPlaytimeHours;
    }

    public boolean isMultiplayer() {
        return multiplayer;
    }

    public void setMultiplayer(boolean multiplayer) {
        this.multiplayer = multiplayer;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    // --- Added for backwards compatibility with old API ---

    /**
     * @return the size of the game in GB (alias for getSizeGB)
     */
    public double getGameSize() {
        return getSizeGB();
    }

    /**
     * @return total playtime in hours (alias for getTotalPlaytimeHours)
     */
    public int getTotalPlaytime() {
        return getTotalPlaytimeHours();
    }

    /**
     * @param ranking the deletion ranking score to assign (alias for setScore)
     */
    public void setDeletionRanking(double ranking) {
        setScore(ranking);
    }

    /**
     * @return the deletion ranking score (alias for getScore)
     */
    public double getDeletionRanking() {
        return getScore();
    }
}


