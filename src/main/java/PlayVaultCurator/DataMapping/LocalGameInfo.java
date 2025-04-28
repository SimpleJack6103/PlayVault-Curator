package PlayVaultCurator.DataMapping;

/**
 * LocalGameInfo holds metadata about a locally installed game:
 * - Size in GB
 * - Last Accessed Time (as millis since epoch)
 */
public class LocalGameInfo {
    private double sizeGB;
    private long lastAccessedMillis;

    public LocalGameInfo(double sizeGB, long lastAccessedMillis) {
        this.sizeGB = sizeGB;
        this.lastAccessedMillis = lastAccessedMillis;
    }

    public double getSizeGB() {
        return sizeGB;
    }

    public long getLastAccessedMillis() {
        return lastAccessedMillis;
    }
}
