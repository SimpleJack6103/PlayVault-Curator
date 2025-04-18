import java.io.IOException;

public class SteamAPIDriver {
    public static void main(String[] args) {
        // Create an instance of SteamAPI with a valid Steam ID.
        // Creating a SteamAPI object without a steamID will return errors on all queries.
        // If an object is created without a steamID, use setSteamId("<steamIdstring>")
        SteamAPI steamApi = new SteamAPI("76561198061599338");

        // Set optional parameters for the owned games and recently played games endpoints.
        // By default, SteamAPI objects are initialized as true
        steamApi.setIncludeAppInfo(true);
        steamApi.setIncludePlayedFreeGames(true);

        // Choose the response format (true for JSON, false for XML).
        // By default, SteamAPI objects are initialized as true
        steamApi.setResponseFormat(true);

        try {
            // Call the API endpoints and print the responses.      
            // Methods return list of games sorted by appId, not title name      
            SteamLibrary SteamLibrary = new SteamLibrary();
            
            SteamLibrary = steamApi.getOwnedGames();
            System.out.println("All Owned Games");
            System.out.println("Inlcude F2P?: " + steamApi.isIncludePlayedFreeGames());
            // Note the difference in getGame_count() vs getTotal_count()
            // getGame_count() returns the amount of games owned by a user
            // getTotal_count() returns the amount of games played by the user within the last 2 weeks
            // This is a distinction made by the Steam API
            System.out.println("Total Games: " + SteamLibrary.getGame_count());
            for(SteamGame game : SteamLibrary.getLibrary()){
                System.out.println("Game: " + game.getName() 
                + " (AppID: " + game.getAppid() + ")"
                + " (Playtime Last 2 Weeks: " + game.getPlaytime_2weeks() + ")"
                + " (Playtime Forever: " + game.getPlaytime_forever() + ")");
            }
            System.out.println("--------------------------------------------------");            

            SteamLibrary = steamApi.getRecentlyPlayedGames();
            System.out.println("Recently Played Games");
            System.out.println("Inlcude F2P?: " + steamApi.isIncludePlayedFreeGames());
            System.out.println("Total Games: " + SteamLibrary.getTotal_count());
            for(SteamGame game : SteamLibrary.getLibrary()){
                System.out.println("Game: " + game.getName() 
                + " (AppID: " + game.getAppid() + ")"
                + " (Playtime Last 2 Weeks: " + game.getPlaytime_2weeks() + ")"
                + " (Playtime Forever: " + game.getPlaytime_forever() + ")");
            }
            System.out.println("--------------------------------------------------");

        } catch (IOException e) {
            System.err.println("An error occurred while fetching API data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
