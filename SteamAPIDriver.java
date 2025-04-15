import java.io.IOException;

public class SteamAPIDriver {
    public static void main(String[] args) {
        // Create an instance of SteamAPI with a valid Steam ID.
        SteamAPI steamApi = new SteamAPI("76561198061599338");

        // Set the AppID for the game (for example, 440 corresponds to Team Fortress 2).
        steamApi.setAppId(440);

        // Set optional parameters for the owned games and recently played games endpoints.
        steamApi.setIncludeAppInfo(true);
        steamApi.setIncludePlayedFreeGames(true);
        steamApi.setCount(5);

        // Choose the response format (true for JSON, false for XML).
        steamApi.setResponseFormat(true);

        try {
            // Call the API endpoints and print the responses.
            // userStatsResponse = steamApi.getUserStatsForGame();
            //System.out.println("User Stats for Game:");
            //System.out.println(userStatsResponse);
            //System.out.println("--------------------------------------------------");

            //String ownedGamesResponse = steamApi.getOwnedGames();
            //System.out.println("Owned Games:");
            //System.out.println(ownedGamesResponse);
            //System.out.println("--------------------------------------------------");

            Response recentlyPlayedResponse = steamApi.getRecentlyPlayedGames();
            System.out.println("Recently Played Games:");
            System.out.println("Total Games:" + recentlyPlayedResponse.getTotal_count());
            for(Game game : recentlyPlayedResponse.getGames()){
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
