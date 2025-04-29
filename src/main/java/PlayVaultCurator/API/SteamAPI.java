package PlayVaultCurator.API;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SteamAPI {
    // API key for all requests
    private static final String apiKey = "77825858F6B2685489BAE57E20F32411";

    // User steamID for all queries
    private String steamId;

    // For GetOwnedGames: optional parameters to include additional game info, (default) true
    private boolean includeAppInfo = true;
    private boolean includePlayedFreeGames = true;

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

    public String getResponseFormat() {
        return responseFormat;
    }

    public void setResponseFormat(boolean flag) {
        if(flag)
            this.responseFormat = "json";
        else
            this.responseFormat = "xml";
    }

    // Private helper method to execute HTTP GET requests and return the raw response as a string
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
        while ((inputLine = in.readLine()) != null)
            responseBuilder.append(inputLine);
        in.close();
        String newString = responseBuilder.toString();
        // Strip leading {
        String listofGames = newString.substring(newString.indexOf("{"));
        return listofGames;
    }

    // Methods to call the API endpoints and return json or xml objects

    public SteamLibrary getOwnedGames() throws IOException {
        String url = ("https://api.steampowered.com/IPlayerService/GetOwnedGames/v1/"
                + "?key=" + apiKey + "&steamid=" + steamId + "&format=" + responseFormat);
        if (includeAppInfo)
            url = url + "&include_appinfo=1";
        if (includePlayedFreeGames)
            url = url + "&include_played_free_games=1";
        String responseString = executeRequest(url);
        JsonObject steamObject = JsonParser.parseString(responseString).getAsJsonObject();
        JsonObject respObject = steamObject.getAsJsonObject("response");
        Gson gson = new Gson();
        SteamLibrary library = gson.fromJson(respObject, SteamLibrary.class);
        return library;
    }

    // When called with an integer parameter, count limits the number of games returned
    // Returns a SteamLibrary object: a list of SteamGames
    public SteamLibrary getRecentlyPlayedGames(int count) throws IOException {
        String url = ("https://api.steampowered.com/IPlayerService/GetRecentlyPlayedGames/v1/"
                + "?key=" + apiKey + "&steamid=" + steamId + "&format=" + responseFormat);
        if (count > 0)
            url = url + "&count=" + count;
        String responseString = executeRequest(url);
        JsonObject steamObject = JsonParser.parseString(responseString).getAsJsonObject();
        JsonObject respObject = steamObject.getAsJsonObject("response");
        Gson gson = new Gson();
        SteamLibrary library = gson.fromJson(respObject, SteamLibrary.class);
        return library;
    }

    // When called with no parameter, there is no limit on the amount of games returned
    // Returns a SteamLibrary object: a list of SteamGames
    public SteamLibrary getRecentlyPlayedGames() throws IOException {
        String url = ("https://api.steampowered.com/IPlayerService/GetRecentlyPlayedGames/v1/"
                + "?key=" + apiKey + "&steamid=" + steamId + "&format=" + responseFormat);
        String responseString = executeRequest(url);
        JsonObject steamObject = JsonParser.parseString(responseString).getAsJsonObject();
        JsonObject respObject = steamObject.getAsJsonObject("response");
        Gson gson = new Gson();
        SteamLibrary library = gson.fromJson(respObject, SteamLibrary.class);
        return library;
    }
}
