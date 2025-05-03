package Games2Delete;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PickGame {

    public static List<Game> suggestGamesToUninstall(List<Game> games, double neededSpace) {
        List<Game> sortedGames = new ArrayList<>(games); // Copy list
        sortedGames.sort(Comparator.comparingDouble(Game::getScore));

        if (neededSpace <= 0) {
            int numSuggestions = Math.min(3, sortedGames.size());
            return sortedGames.subList(0, numSuggestions);
        }

        List<Game> suggestedGames = new ArrayList<>();
        double freedSpace = 0;

        for (Game game : sortedGames) {
            if (game.getSizeGB() >= neededSpace) {
                return Collections.singletonList(game);
            }
            suggestedGames.add(game);
            freedSpace += game.getSizeGB();

            if (freedSpace >= neededSpace) {
                return suggestedGames;
            }
        }

        return Collections.emptyList(); // No combination frees enough space
    }
}
