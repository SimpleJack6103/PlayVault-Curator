package Games2Delete;

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
     * Suggests a list of games to uninstall to free up specified amount of space.
     *
     * @param games List of games with ranked deletion scores
     * @param neededSpace Amount of space to free (in GB)
     * @return List of suggested games to uninstall
     */
    public static List<Game> getSuggestedGamesToUninstall(List<Game> games, double neededSpace) {
        if (games == null || games.isEmpty()) {
            return List.of(); // Return empty list safely
        }
        return PickGame.suggestGamesToUninstall(games, neededSpace);
    }
}


  