package Games2Delete;

import java.util.List;

public class DeletionRank {
    public static void rankGames(List<Game> games) {
        games.parallelStream().forEach(game -> {
            double minSize = games.stream().mapToDouble(Game::getSizeGB).min().orElse(1);
            double maxSize = games.stream().mapToDouble(Game::getSizeGB).max().orElse(1);
            int minPlaytime = games.stream().mapToInt(Game::getTotalPlaytimeHours).min().orElse(1);
            int maxPlaytime = games.stream().mapToInt(Game::getTotalPlaytimeHours).max().orElse(1);
            double minEfficiency = games.stream().mapToDouble(g -> g.getTotalPlaytimeHours() / g.getSizeGB()).min().orElse(1);
            double maxEfficiency = games.stream().mapToDouble(g -> g.getTotalPlaytimeHours() / g.getSizeGB()).max().orElse(1);

            double wEfficiency = 0.45;
            double wPlaytime = 0.25;
            double wSize = 0.15;
            double wRecent = 0.15;

            double efficiencyScore = normalize(game.getTotalPlaytimeHours() / game.getSizeGB(), minEfficiency, maxEfficiency, true);
            double playtimeScore = normalize(game.getTotalPlaytimeHours(), minPlaytime, maxPlaytime, true);
            double sizeScore = normalize(game.getSizeGB(), minSize, maxSize, true);
            double recentScore = game.isRecentlyPlayed() ? 1.0 : 0.0;

            game.setScore(
                    (wEfficiency * efficiencyScore) +
                            (wPlaytime * playtimeScore) +
                            (wSize * sizeScore) +
                            (wRecent * recentScore)
            );
        });

        games.sort((g1, g2) -> Double.compare(g2.getScore(), g1.getScore()));
    }

    private static double normalize(double value, double min, double max, boolean higherIsBetter) {
        if (max == min) return 0.5;
        double normalized = (value - min) / (max - min);
        return higherIsBetter ? normalized : (1 - normalized);
    }
}
