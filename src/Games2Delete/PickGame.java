package Games2Delete;

import java.util.*;

public class PickGame {
    public static List<Game> suggestGamesToUninstall(List<Game> games, double neededSpace) {
        List<Game> suggestedGames = new ArrayList<>();
        double freedSpace = 0;

        // Sort games by deletion ranking (lowest score = most likely to be deleted)
        games.sort(Comparator.comparingDouble(Game::getDeletionRanking));

        for (Game game : games) {
            if (game.getGameSize() >= neededSpace) {
                // If a single game is enough, return just that one game
                return Collections.singletonList(game);
            }

            // If not add multiple games until enough space is freed
            suggestedGames.add(game);
            freedSpace += game.getGameSize();

            if (freedSpace >= neededSpace) {
                return suggestedGames; // Stop as soon as we reach the required space
            }
        }

        return suggestedGames; // If not enough space is freed, return whatever was selected
    }
}
