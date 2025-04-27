import java.io.IOException;
import java.io.File;
import java.util.Map;

public class SteamAPIDriver {
    public static void main(String[] args) {
        // Create an instance of SteamAPI with a valid Steam ID.
        // Creating a SteamAPI object without a steamID will return errors on all queries.
        // If an object is created without a steamID, use setSteamId("<steamIdstring>")
        SteamAPI steamApi = new SteamAPI("76561198061599338");

        // Set optional parameters for the owned games and recently played games endpoints.
        // By default, SteamAPI object flags are initialized as true
        steamApi.setIncludeAppInfo(true);
        steamApi.setIncludePlayedFreeGames(true);

        // Choose the response format (true for JSON, false for XML).
        // By default, SteamAPI object flags are initialized as true
        steamApi.setResponseFormat(true);

        try {
            // Call the API endpoints and print the responses.      
            // Methods return list of games sorted by appId, not title name      
            SteamLibrary AllSteamGames = new SteamLibrary();

            AllSteamGames = steamApi.getOwnedGames();

            File directory = new File("C:\\Program Files (x86)\\Steam\\config\\libraryfolders.vdf"); // Insert path into string argument here
                                                                                                              // If file is not found/ no path found, all game sizes will be 0
            String libraryfoldersvdf = DirectorySearch.steamLookup(directory);          // Must store the contents into a string from DirectorySearch.steamLookup
                                                                                        // DirectorySearch method is static, doesnt need an object, just the path as an argument
            Map<Integer,Long> map = AllSteamGames.parseLibraryFolder(libraryfoldersvdf);// parseLibraryFolder is NOT static method, must be called on the SteamLibrary object
            AllSteamGames.setGameSizes(map); // Call setGameSizes while passing map object to allocate sizes to games
                                                    
            System.out.println("All Owned Games");
            System.out.println("Inlcude F2P?: " + steamApi.isIncludePlayedFreeGames());
            // Note the difference in getGame_count() vs getTotal_count()
            // getGame_count() returns the amount of games owned by a user
            // getTotal_count() returns the amount of games played by the user within the last 2 weeks
            // This is a distinction made by the Steam API
            System.out.println("Total Games: " + AllSteamGames.getGame_count());                   
            System.out.printf(
                    "%-70s  %8s  %12s  %15s  %12s%n",
                    "Game Name", "AppID", "Play(2w)", "Play(Forever)", "Size (GiB)");
            System.out.println("--------------------------------------------------------------------------------");
            for (SteamGame game : AllSteamGames.getLibrary()) {                
                System.out.printf(
                        "%-70s  %8d  %12d  %15d  %12.2f%n",
                        game.getName(),
                        game.getAppid(),
                        game.getPlaytime_2weeks(),                  // Playtime is in minutes
                        game.getPlaytime_forever(),
                        game.getGameSize() / (1024.0 * 1024 * 1024) // GameSize is in bytes, use 1024.0 to force double casting
                        );
            }
            System.out.println("--------------------------------------------------------------------------------");     


            SteamLibrary RecentSteamGames = new SteamLibrary();
            RecentSteamGames = steamApi.getRecentlyPlayedGames();            
            RecentSteamGames.setGameSizes(map); // We can use the same map from earlier

            System.out.println("Recently Played Games");
            System.out.println("Inlcude F2P?: " + steamApi.isIncludePlayedFreeGames());
            System.out.println("Total Games: " + RecentSteamGames.getTotal_count());
            System.out.printf(
                    "%-70s  %8s  %12s  %15s  %12s%n",
                    "Game Name", "AppID", "Play(2w)", "Play(Forever)", "Size (GiB)");
            System.out.println("--------------------------------------------------------------------------------");
            for (SteamGame game : RecentSteamGames.getLibrary()) {                
                System.out.printf(
                        "%-70s  %8d  %12d  %15d  %12.2f%n",
                        game.getName(),
                        game.getAppid(),
                        game.getPlaytime_2weeks(),                  // Playtime is in minutes
                        game.getPlaytime_forever(),
                        game.getGameSize() / (1024.0 * 1024 * 1024) // GameSize is in bytes, use 1024.0 to force double casting
                        );
            }            
            System.out.println("--------------------------------------------------------------------------------");

        } catch (IOException e) {
            System.err.println("An error occurred while fetching API data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
