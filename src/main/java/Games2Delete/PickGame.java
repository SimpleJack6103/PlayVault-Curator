package Games2Delete;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PickGame {

    public static List<Game> suggestGamesToUninstall(List<Game> games, double neededSpace) {
        List<Game> sortedGames = new ArrayList<>(games); // Copy list
        sortedGames.sort(Comparator.comparingDouble(Game::getDeletionRanking));

        // If no extra space is needed (user is already under threshold),
        // then just return the top few highest ranked games (e.g., top 3).
        if (neededSpace <= 0) {
            int numSuggestions = Math.min(3, sortedGames.size());
            return sortedGames.subList(0, numSuggestions);
        }

        List<Game> suggestedGames = new ArrayList<>();
        double freedSpace = 0;

        for (Game game : sortedGames) {
            // If a single game is large enough, return it immediately.
            if (game.getGameSize() >= neededSpace) {
                return Collections.singletonList(game);
            }
            suggestedGames.add(game);
            freedSpace += game.getGameSize();

            if (freedSpace >= neededSpace) {
                return suggestedGames;
            }
        }

        return Collections.emptyList(); // No combination frees enough space
    }
}
