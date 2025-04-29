package PlayVaultCurator.API;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SteamLibrary {
    // Private members match the Steam API return keys.
    private int total_count;    // Number of games played within the last 2 weeks
    private int game_count;     // Total number of games owned
    // Steam API returns an array, but we are using a List representation.
    private List<SteamGame> games;
    // Regex pattern to look for strings in local user file libraryfolders.vdf
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

    // Helper function to give a list of games their game sizes.
    // Parses the text contents of libraryfolder.vdf using regex.
    // Maps appids (as keys) to their game sizes (in bytes).
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
