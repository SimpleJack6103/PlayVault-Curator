package Games2Delete;

import java.util.List;

public class DeletionRank {
    public static void rankGames(List<Game> games) {
        games.parallelStream().forEach(game -> {
            double minSize = games.stream().mapToDouble(Game::getGameSize).min().orElse(1);
            double maxSize = games.stream().mapToDouble(Game::getGameSize).max().orElse(1);
            int minLastPlayed = games.stream().mapToInt(Game::getDaysSinceLastPlayed).min().orElse(1);
            int maxLastPlayed = games.stream().mapToInt(Game::getDaysSinceLastPlayed).max().orElse(1);
            int minPlaytime = games.stream().mapToInt(Game::getTotalPlaytime).min().orElse(1);
            int maxPlaytime = games.stream().mapToInt(Game::getTotalPlaytime).max().orElse(1);
            //efficiency = playtime in minutes divided by size in gb
            double minEfficiency = games.stream().mapToDouble(g -> g.getTotalPlaytime() / g.getGameSize()).min().orElse(1);
            double maxEfficiency = games.stream().mapToDouble(g -> g.getTotalPlaytime() / g.getGameSize()).max().orElse(1);
            double efficiencyScore = normalize(game.getTotalPlaytime() / game.getGameSize(), minEfficiency, maxEfficiency, true);
            // w = weight of each factor
            double wEfficiency = 0.35;
            double wDaysSinceLastPlayed = 0.30;
            double wPlaytime = 0.20;
            double wSize = 0.15;

            double sizeScore = normalize(game.getGameSize(), minSize, maxSize, true);
            double lastPlayedScore = normalize(game.getDaysSinceLastPlayed(), minLastPlayed, maxLastPlayed, false);
            double playtimeScore = normalize(game.getTotalPlaytime(), minPlaytime, maxPlaytime, true);

            game.setDeletionRanking(
                    (wEfficiency * efficiencyScore) +
                            (wDaysSinceLastPlayed * lastPlayedScore) +
                            (wPlaytime * playtimeScore) +
                            (wSize * sizeScore)
            );
        });

        games.sort((g1, g2) -> Double.compare(g2.getDeletionRanking(), g1.getDeletionRanking()));
    }
    //Converts score to a value between 0 and 1
    private static double normalize(double value, double min, double max, boolean higherIsBetter) {
        if (max == min) return 0.5; // Prevent division by zero
        double normalized = (value - min) / (max - min);
        return higherIsBetter ? normalized : (1 - normalized);
    }
}