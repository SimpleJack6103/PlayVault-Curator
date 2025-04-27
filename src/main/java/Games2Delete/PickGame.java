package Games2Delete;

import java.util.*;

public class PickGame {

    public static List<Game> suggestGamesToUninstall(List<Game> games, double neededSpace) {
        List<Game> sortedGames = new ArrayList<>(games); // Copy list
        sortedGames.sort(Comparator.comparingDouble(Game::getDeletionRanking));

        List<Game> suggestedGames = new ArrayList<>();
        double freedSpace = 0;

        for (Game game : sortedGames) {
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
