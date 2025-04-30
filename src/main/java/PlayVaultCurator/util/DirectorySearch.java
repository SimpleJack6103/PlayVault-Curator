package PlayVaultCurator.util;

import Games2Delete.Game;
import java.io.File;
import java.nio.file.Files;
import java.util.Scanner;
import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class DirectorySearch {

    /**
     * Scans the given root directory for subdirectories (each assumed to represent a game)
     * and returns a list of Game objects based on folder metadata.
     *
     * @param rootDir the game installation directory selected by the user.
     * @return a List of Game objects.
     */
    public static List<Game> searchFiles(File rootDir) {
        List<Game> games = new ArrayList<>();
        if (rootDir == null || !rootDir.exists() || !rootDir.isDirectory()) {
            return games;
        }

        File[] subDirs = rootDir.listFiles(File::isDirectory);
        if (subDirs != null) {
            for (File dir : subDirs) {
                try {
                    Path filePath = dir.toPath();
                    BasicFileAttributes attrs = Files.readAttributes(filePath, BasicFileAttributes.class);

                    // Calculate folder size (converted to GB)
                    double sizeGB = getFolderSizeInBytes(dir) / (1024.0 * 1024 * 1024.0);

                    // Use last access time for calculating days since last played
                    long lastAccessedMillis = attrs.lastAccessTime().toMillis();
                    int daysSinceLastPlayed = (int) ((System.currentTimeMillis() - lastAccessedMillis) / (1000 * 60 * 60 * 24));

                    int totalPlaytime = 0;  // Default if no historical playtime is available.
                    boolean isMultiplayer = false; // Default assumption.

                    // Use the folder name as the game name.
                    Game game = new Game(dir.getName(), sizeGB, daysSinceLastPlayed, totalPlaytime, isMultiplayer);
                    games.add(game);
                } catch (IOException e) {
                    System.err.println("Error scanning directory: " + dir.getAbsolutePath());
                    e.printStackTrace();
                }
            }
        }
        return games;
    }

    /**
     * Recursively calculates the size (in bytes) of a folder.
     *
     * @param folder the folder to measure.
     * @return total size in bytes.
     */
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


    /**
     * Retrieves the file extension from a given file path.
     *
     * @param filePath the path of the file.
     * @return the file extension as a string, or an empty string if no extension is found.
     */
    public static String getExtension(Path filePath) {
        String fileName = filePath.getFileName().toString();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }


    /**
     * Searches for Steam files within a given directory and its subdirectories.
     *
     * @param directory the directory to search within.
     */
    public static void searchSteamFolder(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    steamLookup(file);
                } else if (file.isDirectory()) {
                    searchFiles(file);
                }
            }
        }
    }


    /**
     * Checks if a given file is a Steam library file and retrieves its contents if it matches.
     *
     * @param f_type the file to check.
     * @return the contents of the Steam library file as a string, or an empty string if the file does not match.
     */
    public static String steamLookup(File f_type){
        try {
            Path filePath = f_type.toPath();  // fetch path
            BasicFileAttributes attrs = Files.readAttributes(filePath, BasicFileAttributes.class); //read in attrs
            String fileName = f_type.getName();
            String fileType = getExtension(filePath);
            //  long fileSize = f_type.length();
            // String lastAccessed = new SimpleDateFormat("MM-dd-yyyy  HH:mm:ss").format(attrs.lastAccessTime().toMillis());
            if (fileType.equals("vdf") && fileName.equals("libraryfolders.vdf")) {

                // Read file contents as a string
                String content = Files.readString(filePath);
                return "\n--- File Contents ---\n" + content;
            } else {
                return " "; //file not the target
            }
        }
        catch(Exception e){
            System.out.println("Error retrieving file attributes for " + f_type.getAbsolutePath());
            e.printStackTrace();
            return " ";
        }
    }
}
