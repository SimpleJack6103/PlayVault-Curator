package PlayVaultCurator.util;

import Games2Delete.Game;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;



public class DirectorySearch {

    private static List<Game> games = new ArrayList<>();

    public static List<Game> searchFiles(File rootDir) {
        /*List<Game> games = new ArrayList<>();
        if (rootDir == null || !rootDir.exists() || !rootDir.isDirectory()) {
            return games;
        }

        File[] subDirs = rootDir.listFiles(File::isDirectory);
        if (subDirs != null) {
            for (File dir : subDirs) {
                try {
                    searchFileD(dir);
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
        }*/

        games.clear(); // Clear the list before starting a new search
        if (rootDir == null || !rootDir.exists() || !rootDir.isDirectory()) {
            return games;
        }

        searchFileD(rootDir);
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




    //search for files given a directory
    public static void searchFileD(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    typeLookup(file);
                } else if (file.isDirectory()) {
                    searchFileD(file);
                }
            }
        }
    }

    public static String getExtension(Path filePath) {
        String fileName = filePath.getFileName().toString();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

    //checks type of file
    public static void typeLookup(File f_type){
        try {
            Path filePath = f_type.toPath();  // fetch path
            BasicFileAttributes attrs = Files.readAttributes(filePath, BasicFileAttributes.class); //read in attrs
            String fileName = f_type.getName();
            String fileType = getExtension(filePath);
            double fileSize = f_type.length();
            String lastAccessed = new SimpleDateFormat("MM-dd-yyyy  HH:mm:ss").format(attrs.lastAccessTime().toMillis());
            if (fileType.equals("exe")|| fileType.equals("Application")) {
                double sizeGB = fileSize / 1024.0;
                sizeGB = Math.round(sizeGB * 1000.0) / 1000.0;

                int totalPlaytime = 0; //placeholder
                boolean recentlyPlayed = false; //placeholder

                Game game = new Game(fileName, sizeGB, recentlyPlayed, totalPlaytime);
                games.add(game);
                System.out.printf("Name: %s, Type: %s, Size: %.2f MB, Last Accessed: %s%n",
                        fileName, fileType, fileSize, lastAccessed);
            } else {
                System.out.println("----- ");
            }
        }
        catch(Exception e){
            System.out.println("Error retrieving file attributes for " + f_type.getAbsolutePath());
        }
    }
}
