package Games2Delete;
import java.util.Random;

/**
 * Represents a game candidate for deletion, with metadata used to rank it.
 */
public class Game {
    private String name;
    private double sizeGB;
    private boolean recentlyPlayed;
    private int totalPlaytimeHours;
    private double score;

    public Game(String name, double sizeGB, /*boolean recentlyPlayed,*/ int totalPlaytimeHours) {
        this.name = name;
        this.sizeGB = sizeGB;
        this.recentlyPlayed = randomizeRecentlyPlayed(); //recentlyPlayed;
        this.totalPlaytimeHours = totalPlaytimeHours;//randomizePlaytimeHours();
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


   private boolean randomizeRecentlyPlayed() {
        return new Random().nextBoolean();
    }

    /*private int randomizePlaytimeHours(){
        return new Random().nextInt(23) + 1;
    }*/

}
