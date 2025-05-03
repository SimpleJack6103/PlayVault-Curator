package PlayVaultCurator.util;

import Games2Delete.Game;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DirectorySearch {

    public static List<Game> searchFiles(File rootDir) {
        List<Game> games = new ArrayList<>();
        if (rootDir == null || !rootDir.exists() || !rootDir.isDirectory()) {
            return games;
        }

        File[] subDirs = rootDir.listFiles(File::isDirectory);
        if (subDirs != null) {
            for (File dir : subDirs) {
                try {
                    double sizeGB = getFolderSizeInBytes(dir) / (1024.0 * 1024 * 1024.0);
                    int totalPlaytime = 0;
                    boolean recentlyPlayed = false;

                    Game game = new Game(dir.getName(), sizeGB, recentlyPlayed, totalPlaytime);
                    games.add(game);
                } catch (Exception e) {
                    System.err.println("Error scanning directory: " + dir.getAbsolutePath());
                    e.printStackTrace();
                }
            }
        }
        return games;
    }

    private static long getFolderSizeInBytes(File folder) {
        long size = 0;
        File[] files = folder.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isFile()) {
                    size += f.length();
                } else {
                    size += getFolderSizeInBytes(f);
                }
            }
        }
        return size;
    }
}
