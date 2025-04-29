/*
package PlayVaultCurator.DataMapping;

import PlayVaultCurator.API.SteamGame;
import Games2Delete.Game;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;


public class SteamGameConverter {


    public static List<Game> convertSteamGamesToGames(List<SteamGame> steamGames, File directoryRoot) {
        Map<String, LocalGameInfo> localGameInfoMap = scanDirectoryForLocalGames(directoryRoot);

        List<Game> games = new ArrayList<>();
        long currentTimeMillis = System.currentTimeMillis();

        for (SteamGame sg : steamGames) {
            LocalGameInfo info = localGameInfoMap.getOrDefault(
                    sg.getName(),
                    new LocalGameInfo(5.0, currentTimeMillis) // Default size: 5GB and "now" if not found
            );

            int daysSinceLastPlayed = calculateDaysSince(info.getLastAccessedMillis(), currentTimeMillis);

            Game g = new Game(
                    sg.getName(),
                    info.getSizeGB(),
                    daysSinceLastPlayed,
                    sg.getPlaytime_forever() / 60, // Convert minutes to hours
                    false // Assume not multiplayer
            );

            games.add(g);
        }
        return games;
    }

    private static int calculateDaysSince(long lastAccessedMillis, long currentTimeMillis) {
        long diffMillis = currentTimeMillis - lastAccessedMillis;
        long days = diffMillis / (1000 * 60 * 60 * 24);
        return (int) days;
    }

    private static Map<String, LocalGameInfo> scanDirectoryForLocalGames(File rootDir) {
        Map<String, LocalGameInfo> map = new HashMap<>();
        scanRecursive(rootDir, map);
        return map;
    }

    private static void scanRecursive(File directory, Map<String, LocalGameInfo> map) {
        if (directory == null || !directory.exists() || !directory.isDirectory()) {
            return;
        }

        File[] files = directory.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                try {
                    Path filePath = file.toPath();
                    BasicFileAttributes attrs = Files.readAttributes(filePath, BasicFileAttributes.class);

                    double sizeInGB = getFolderSizeInBytes(file) / (1024.0 * 1024.0 * 1024.0);
                    long lastAccessMillis = attrs.lastAccessTime().toMillis();

                    map.put(file.getName(), new LocalGameInfo(sizeInGB, lastAccessMillis));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                scanRecursive(file, map); // Recursive search
            }
        }
    }

    private static long getFolderSizeInBytes(File folder) {
        long length = 0;
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    length += file.length();
                } else {
                    length += getFolderSizeInBytes(file); // Recursive
                }
            }
        }
        return length;
    }
}
*/
