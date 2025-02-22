import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class APIdemo {
    private static final String API_KEY = "77825858F6B2685489BAE57E20F32411";
    private static final String STEAM_ID = "76561198061599338";

    public static void main(String[] args) throws Exception {
        //Get Player Summary
        //String url = "https://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key=" + API_KEY + "&steamids=" + STEAM_ID + "&format=json";

        //Get Owned Games
        String url = "https://api.steampowered.com/IPlayerService/GetOwnedGames/v0001/?key=" + API_KEY + "&steamid=" + STEAM_ID + "&format=json";

        try{
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            int responseCode = connection.getResponseCode();

            if(responseCode == 200){
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                StringBuilder response = new StringBuilder();
                String line;

                while((line = reader.readLine()) != null){
                    response.append(line);
                }

                reader.close();

                FileWriter file = new FileWriter("steam_data.json");
                file.write(response.toString());
                file.close();
            }else{
                System.out.println("Error: " + responseCode);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
