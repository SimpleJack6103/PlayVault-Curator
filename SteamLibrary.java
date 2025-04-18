import java.util.List;

public class SteamLibrary {
    // Private Members are named in such a way to match the SteamAPI return calls
    // total_count = # of games played within the last 2 weeks
    private int total_count;
    // game_count = # of games owned
    private int game_count;
    // games is a list of games; note that steamAPI returns an array, but a list is used in this implementation
    private List<SteamGame> games;

    SteamLibrary(){
        total_count = 0;
        games = null;
    }

    public int getTotal_count() {
        return total_count;
    }

    public int getGame_count(){
        return game_count;
    }

    public List<SteamGame> getLibrary() {
        return games;
    }

    public SteamGame getGame(int i){
        return games.get(i);
    }

}
