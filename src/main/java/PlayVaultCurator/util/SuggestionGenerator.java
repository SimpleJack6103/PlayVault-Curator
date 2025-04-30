package PlayVaultCurator.util;

import Games2Delete.Game;
import Games2Delete.Games2Delete;

import java.util.ArrayList;
import java.util.List;

/**
 * Generate up to N different “sets” of games whose combined sizes
 * free at least the desired amount.
 */
public class SuggestionGenerator {

    /**
     * @param games    the full ranked list (unsorted is fine—it will rank internally)
     * @param neededGB how many GB we must free
     * @param maxSets  how many alternative sets to return
     */
    public static List<List<Game>> getSuggestionSets(List<Game> games, double neededGB, int maxSets) {
        // first, rank once
        Games2Delete.rankGamesForDeletion(games);

        List<List<Game>> sets = new ArrayList<>();
        // we'll generate each set by excluding the top i games each time
        for (int i = 0; i < maxSets; i++) {
            // build a pool that skips the first i games
            List<Game> pool = new ArrayList<>();
            if (i < games.size()) {
                pool.addAll(games.subList(i, games.size()));
            }
            // ask the library for a greedy set from this pool
            List<Game> suggestion = Games2Delete.getSuggestedGamesToUninstall(pool, neededGB);
            if (suggestion.isEmpty()) break;
            // avoid duplicates
            boolean duplicate = sets.stream().anyMatch(existing -> existing.equals(suggestion));
            if (!duplicate) {
                sets.add(suggestion);
            }
        }
        return sets;
    }
}

