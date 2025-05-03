package PlayVaultCurator.DataMapping;

import PlayVaultCurator.API.SteamAPI;
import PlayVaultCurator.API.SteamGame;
import PlayVaultCurator.API.SteamLibrary;
import Games2Delete.Game;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SteamGameConverter {

    public static List<Game> convertSteamGamesToGames(String steamID, File directoryRoot) throws IOException {
        SteamAPI steamAPI = new SteamAPI(steamID);
        SteamLibrary library = steamAPI.getOwnedGames();

        File vdf = findLibraryFoldersFile(directoryRoot);
        if (vdf != null && vdf.exists()) {
            String contents = Files.readString(vdf.toPath());
            Map<Integer, Long> sizeMap = library.parseLibraryFolder(contents);
            library.setGameSizes(sizeMap);
        }

        List<SteamGame> steamGames = library.getLibrary();
        List<Game> games = new ArrayList<>();

        for (SteamGame sg : steamGames) {
            boolean recentlyPlayed = sg.getPlaytime_2weeks() > 0;
            int totalPlaytime = sg.getPlaytime_forever() / 60;
            double gameSizeGB = sg.getGameSize() / (1024.0 * 1024 * 1024.0);

            if (gameSizeGB <= 0.1) {
                gameSizeGB = 5.0;
            }

            Game game = new Game(sg.getName(), gameSizeGB, recentlyPlayed, totalPlaytime);
            games.add(game);
        }
        return games;
    }

    private static File findLibraryFoldersFile(File directoryRoot) {
        if (directoryRoot == null || !directoryRoot.exists()) return null;
        File[] files = directoryRoot.listFiles();
        if (files == null) return null;

        for (File file : files) {
            if (file.isFile() && file.getName().equalsIgnoreCase("libraryfolders.vdf")) {
                return file;
            } else if (file.isDirectory()) {
                File nested = findLibraryFoldersFile(file);
                if (nested != null) return nested;
            }
        }
        return null;
    }
}
