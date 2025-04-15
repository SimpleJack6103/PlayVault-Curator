import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SteamAPI {
    // API key for all requests
    private static final String apiKey = "77825858F6B2685489BAE57E20F32411";

    // User steamID for all queries
    private String steamId;

    // For GetUserStatsForGame: the AppID of the game to query
    private int appId;

    // For GetOwnedGames: optional parameters to include additional game info, (default) true
    private boolean includeAppInfo = true;
    private boolean includePlayedFreeGames = true;

    // For GetRecentlyPlayedGames: optional count parameter to limit results
    private int count;

    // Response format flag: "json" (default) or "xml"
    private String responseFormat = "json";

    // Constructors
    public SteamAPI() {}

    // Will not query without a valid Steam ID
    public SteamAPI(String steamID){
        this.steamId = steamID;
    }

    // Getters and Setters
    public String getSteamId() {
        return steamId;
    }
    public void setSteamId(String steamId) {
        this.steamId = steamId;
    }

    public int getAppId() {
        return appId;
    }
    public void setAppId(int appId) {
        this.appId = appId;
    }

    public boolean isIncludeAppInfo() {
        return includeAppInfo;
    }
    public void setIncludeAppInfo(boolean flag) {
        this.includeAppInfo = flag;
    }

    public boolean isIncludePlayedFreeGames() {
        return includePlayedFreeGames;
    }
    public void setIncludePlayedFreeGames(boolean flag) {
        this.includePlayedFreeGames = flag;
    }

    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Count must be 0 or a positive integer.");
        }
        this.count = count;
    }

    public String getResponseFormat() {
        return responseFormat;
    }
    public void setResponseFormat(boolean flag) {
        if(flag)
            this.responseFormat = "json";
        else
            this.responseFormat = "xml";
    }

    // Private helper method to execute HTTP GET requests and return the raw response as a String
    private String executeRequest(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if(responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("HTTP request failed with code: " + responseCode);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder responseBuilder = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            responseBuilder.append(inputLine);
            responseBuilder.append('\n');
        }
        in.close();
        return responseBuilder.toString();
    }

    // Methods to call the API endpoints and return json or xml objects

    public String getUserStatsForGame() throws IOException {
        String baseUrl = "https://api.steampowered.com/ISteamUserStats/GetUserStatsForGame/v2/";
        return executeRequest(baseUrl + "?key=" + apiKey + "&steamid=" + steamId + "&appid=" + appId + "&format=" + responseFormat);
    }

    public String getOwnedGames() throws IOException {
        String baseUrl = "https://api.steampowered.com/IPlayerService/GetOwnedGames/v1/";
        StringBuilder url = new StringBuilder(baseUrl + "?key=" + apiKey + "&steamid=" + steamId + "&format=" + responseFormat);
        if (includeAppInfo) {
            url.append("&include_appinfo=1");
        }
        if (includePlayedFreeGames) {
            url.append("&include_played_free_games=1");
        }
        return executeRequest(url.toString());
    }

    public String getRecentlyPlayedGames() throws IOException {
        String baseUrl = "https://api.steampowered.com/IPlayerService/GetRecentlyPlayedGames/v1/";
        StringBuilder url = new StringBuilder(baseUrl + "?key=" + apiKey + "&steamid=" + steamId + "&format=" + responseFormat);
        if (count > 0) {
            url.append("&count=");
            url.append(count);
        }
        return executeRequest(url.toString());
    }

    // Response classes for each endpoint. These currently wrap the raw response,
    // but are not finished for use. Return: formatted or parsed json or xml contents
    // Consider making different wrapper classes/methods depending on format

    public static class UserStatsForGame {
        private String response;

        public UserStatsForGame(String response) {
            this.response = response;
        }

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }
    }

    public static class OwnedGames {
        private String response;

        public OwnedGames(String response) {
            this.response = response;
        }

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }
    }

    public static class RecentlyPlayedGames {
        private String response;

        public RecentlyPlayedGames(String response) {
            this.response = response;
        }

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }
    }
}
