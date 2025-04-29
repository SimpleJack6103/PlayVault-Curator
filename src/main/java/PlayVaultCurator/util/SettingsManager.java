package PlayVaultCurator.util;

import java.util.prefs.Preferences;

public class SettingsManager {
    private static final Preferences prefs = Preferences.userNodeForPackage(SettingsManager.class);

    private static final String KEY_DRIVE_PATH = "drivePath";
    private static final String KEY_THRESHOLD = "threshold";
    private static final String KEY_STEAM_USER_ID = "steamUserId";
    private static final String KEY_GAME_DIRECTORY = "gameDirectory";

    private static final String DEFAULT_DRIVE = "";
    private static final double DEFAULT_THRESHOLD = 0.70;
    private static final String DEFAULT_STEAM_USER_ID = "";
    private static final String DEFAULT_GAME_DIRECTORY = "";

    public static void setDrivePath(String path) {
        prefs.put(KEY_DRIVE_PATH, path);
    }

    public static String getDrivePath() {
        return prefs.get(KEY_DRIVE_PATH, DEFAULT_DRIVE);
    }

    public static void setThreshold(double threshold) {
        prefs.putDouble(KEY_THRESHOLD, threshold);
    }

    public static double getThreshold() {
        return prefs.getDouble(KEY_THRESHOLD, DEFAULT_THRESHOLD);
    }

    public static void setSteamUserId(String steamUserId) {
        prefs.put(KEY_STEAM_USER_ID, steamUserId);
    }

    public static String getSteamUserId() {
        return prefs.get(KEY_STEAM_USER_ID, DEFAULT_STEAM_USER_ID);
    }

    public static void setGameDirectory(String directory) {
        prefs.put(KEY_GAME_DIRECTORY, directory);
    }

    public static String getGameDirectory() {
        return prefs.get(KEY_GAME_DIRECTORY, DEFAULT_GAME_DIRECTORY);
    }
}