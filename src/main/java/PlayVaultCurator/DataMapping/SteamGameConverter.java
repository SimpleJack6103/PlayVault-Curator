package PlayVaultCurator.DataMapping;

import PlayVaultCurator.API.SteamAPI;
import PlayVaultCurator.API.SteamGame;
import PlayVaultCurator.API.SteamLibrary;
import Games2Delete.Game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SteamGameConverter {

    /**
     * Converts Steam API owned games into your internal Game objects.
     * Uses the provided steamID to query the API.
     * Optionally, a local game directory (directoryRoot) may be used to supplement local metadata,
     * though this example uses default local values if no matching data is found.
     *
     * @param steamID the Steam user ID
     * @param directoryRoot the local game installation directory (can be used for local metadata)
     * @return a list of Game objects representing the Steam library
     * @throws IOException if an API call fails
     */
    public static List<Game> convertSteamGamesToGames(String steamID, File directoryRoot) throws IOException {
        SteamAPI steamAPI = new SteamAPI(steamID);
        // Retrieve the owned games via the Steam API.
        SteamLibrary library = steamAPI.getOwnedGames();
        List<SteamGame> steamGames = library.getLibrary();

        List<Game> games = new ArrayList<>();
        // For each Steam game, create an internal Game object.
        // Here we use default values for daysSinceLastPlayed and gameSize since local mapping may not be available.
        // You could optionally use a local directory scan (for example, via DirectorySearch) to enrich this data.
        for (SteamGame sg : steamGames) {
            // Default values: if no local metadata is available:
            int daysSinceLastPlayed = 0;
            // Convert playtime from minutes to hours.
            int totalPlaytime = sg.getPlaytime_forever() / 60;
            // Default game size: if no local metadata exists, default to 5.0 GB.
            double gameSizeGB = 5.0;

            Game game = new Game(sg.getName(), gameSizeGB, daysSinceLastPlayed, totalPlaytime, false);
            games.add(game);
        }
        return games;
    }
}
