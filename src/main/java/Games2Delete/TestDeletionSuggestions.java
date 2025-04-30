/*
package Games2Delete;

import java.util.ArrayList;
import java.util.List;

public class TestDeletionSuggestions {

    public static void main(String[] args) {
        // Create dummy Game objects.
        // Assuming your Game class has a constructor like:
        // Game(String gameName, double gameSize, int daysSinceLastPlayed, int totalPlaytime, boolean isMultiplayer)
        List<Game> dummyGames = new ArrayList<>();
        dummyGames.add(new Game("Dummy Game 1", 10.0, 365, 0, false));  // 10 GB, not played in a year.
        dummyGames.add(new Game("Dummy Game 2", 5.0, 30, 90, false));     // 5 GB, played 30 days ago.
        dummyGames.add(new Game("Dummy Game 3", 3.0, 100, 0, false));     // 3 GB, played long ago.
        dummyGames.add(new Game("Dummy Game 4", 2.5, 60, 50, false));     // 2.5 GB, a more recent game.

        // Define how much space needs to be freed (e.g., 7GB).
        double neededSpace = 7.0;

        // Get deletion suggestions using your PickGame logic.
        List<Game> deletionSuggestions = PickGame.suggestGamesToUninstall(dummyGames, neededSpace);

        // Output the results to the console.
        System.out.println("Deletion suggestions to free up at least " + neededSpace + " GB:");
        for (Game game : deletionSuggestions) {
            System.out.println(game.getGameName() + " - " + game.getGameSize() + " GB");
        }
    }
}
*/
