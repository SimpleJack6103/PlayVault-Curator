package Games2Delete;

import java.util.*;

public class Games2Delete {

    /**
     * Ranks the list of games in-place based on deletion priority.
     * Lower scores mean more likely to be deleted.
     */
    public static void rankGamesForDeletion(List<Game> games) {
        DeletionRank.rankGames(games);
    }

    /**
     * Suggests a list of games to uninstall in order to free up the specified amount of space.
     *
     * @param games       List of available games (already ranked)
     * @param neededSpace Amount of space to free, in GB
     * @return List of games to uninstall
     */
    public static List<Game> getSuggestedGamesToUninstall(List<Game> games, double neededSpace) {
        return PickGame.suggestGamesToUninstall(games, neededSpace);
    }

    /**
     * Calculates how much free space is available.
     */
    public static double calculateFreeSpace(double totalStorage, double usedStorage) {
        return totalStorage - usedStorage;
    }

    /**
     * Returns true if the user has enough space without uninstalling games.
     */
    public static boolean hasEnoughSpace(double totalStorage, double usedStorage, double neededSpace) {
        return calculateFreeSpace(totalStorage, usedStorage) >= neededSpace;
    }
}
