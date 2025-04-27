import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SteamLibrary {
    // Private Members are named in such a way to match the SteamAPI return calls
    // total_count = # of games played within the last 2 weeks
    private int total_count;
    // game_count = # of games owned
    private int game_count;
    // games is a list of games; note that steamAPI returns an array, but a list is used in this implementation
    private List<SteamGame> games;
    // regex pattern to look for certain strings in local user file libraryfolders.vdf
    private static final Pattern line = Pattern.compile("(?m)^\\s*\"(\\d+)\"\\s*\"(\\d+)\"");

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

    // Helper function to give list of games game sizes
    // Takes the contents of libraryfolder.vdf as a string and parses for appids and their sizes
    // Creates a hashmap using appids as keys and gamesize (bytes) as stored information
    public Map<Integer, Long> parseLibraryFolder(String libraryfoldervdf){
        Map<Integer, Long> map = new HashMap<>();
        Matcher match = line.matcher(libraryfoldervdf);
        while(match.find()){
            int appid = Integer.parseInt(match.group(1));
            long gamesize = Long.parseLong(match.group(2));
            map.put(appid, gamesize);
        }
        return map;
    }


    public void setGameSizes(Map<Integer, Long> map){
        for(SteamGame game : games){
            long gameSize = map.getOrDefault(game.getAppid(), 0L);
            game.setGameSize(gameSize);
        }
    }
}
