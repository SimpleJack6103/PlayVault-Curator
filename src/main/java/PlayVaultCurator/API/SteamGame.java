package PlayVaultCurator.API;

public class SteamGame {
    private int appid;
    private String name;
    private int playtime_2weeks;  // Playtime is measured in minutes
    private int playtime_forever;
    private long gameSize;        // gameSize is measured in bytes

    // Getters and setters
    public int getAppid() {
        return appid;
    }

    public String getName() {
        return name;
    }

    public int getPlaytime_2weeks() {
        return playtime_2weeks;
    }

    public int getPlaytime_forever() {
        return playtime_forever;
    }

    public long getGameSize(){
        return gameSize;
    }

    public void setGameSize(long gameSize){
        this.gameSize = gameSize;
    }
}