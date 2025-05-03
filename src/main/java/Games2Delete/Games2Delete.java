package Games2Delete;

import PlayVaultCurator.DataMapping.SteamGameConverter;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class Games2Delete {

    /**
     * Ranks the list of games based on deletion priority.
     * Lower scores = more likely to be deleted.
     */
    public static void rankGamesForDeletion(List<Game> games) {
        if (games != null) {
            DeletionRank.rankGames(games);
        }
    }

    /**
     * Suggests a list of games to uninstall to free up the specified amount of space.
     *
     * @param games       List of games with ranked deletion scores
     * @param neededSpace Amount of space to free (in GB)
     * @return List of suggested games to uninstall
     */
    public static List<Game> getSuggestedGamesToUninstall(List<Game> games, double neededSpace) {
        if (games == null || games.isEmpty()) {
            return Collections.emptyList();
        }
        return PickGame.suggestGamesToUninstall(games, neededSpace);
    }

    /**
     * Fetches the user's owned games from the Steam API and converts them
     * into internal Game objects using Steam data only (including estimated size
     * and recent play flags derived from playtime_2weeks).
     *
     * @param steamID the Steam user ID
     * @return a List of Game objects representing the user's library
     * @throws IOException if the API call fails
     */
    public static List<Game> getGamesFromSteam(String steamID) throws IOException {
        return SteamGameConverter.convertSteamGamesToGames(steamID, null);
    }
}
