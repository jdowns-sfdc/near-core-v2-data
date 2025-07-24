import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class GenerateSampleCreditData {
    private static final String[] BANK_NAMES = {
        "First National Bank",
        "Citizens Trust Bank",
        "Metropolitan Savings",
        "Heritage Financial",
        "Liberty Bank and Trust",
        "Community First Bank",
        "Premier Credit Union",
        "Hill Valley National Bank",
        "Central Trust Company",
        "Riverside Bank",
        "Summit Financial Group",
        "Merchant Bank of America",
        "Golden State Bank",
        "Atlantic Savings Bank",
        "Pacific Coast Bank",
        "Midwest Community Bank",
        "Southern Trust Bank",
        "Northern Star Bank",
        "Eastern Financial Services",
        "Western Sky Bank",
        "Capital City Bank",
        "Federal Reserve Bank",
        "State Street Bank",
        "Commerce Bank",
        "US Bank",
        "PNC Bank",
        "TD Bank",
        "Regions Bank",
        "Fifth Third Bank",
        "BBT Bank",
        "SunTrust Bank"
    };

    public static void main(String[] args) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("demo.yaml", false))) {
            for (int u = 0; u < 10000; ++u) {
                writer.write("- userIdentifier: 000-11-");
                writer.write(String.format("%04d", u));
                writer.write("\n");
                writer.write("  accounts:\n");
                writeAccounts(writer, true, 3, 8, 60);
                writeAccounts(writer, false, 5, 12, 120);
            }
        } catch (IOException e) {
            System.err.println("Error opening file: " + e.getMessage());
        }
    }

    private static void writeAccounts(BufferedWriter writer, boolean active, int min, int max, int maxAge) throws IOException {
        Set<String> institutions = new HashSet<>(Arrays.asList(BANK_NAMES));
        for (int i = min; i <= max; ++i) {
            writer.write("    - institution: " + getRandomAndRemove(institutions));
            writer.write("\n");
            writer.write("      ageInMonths: " + getRandomAgeInMonths(4, maxAge));
            writer.write("\n");
            writer.write("      active: " + (active ? "true" : "false"));
            writer.write("\n");
            writer.write("      balance: " + (active ? getRandomBalance(500, 10000) : "0.00"));
            writer.write("\n");
            writer.write("      missedPayments: " + getRandomMissedPayments());
            writer.write("\n");
        }
    }

    private static int getRandomMissedPayments() {
        if ((int) (Math.random() * 100) < 5) {
            return (int) (Math.random() * 6) + 1;
        }
        return 0;
    }

    private static String getRandomBalance(int min, int max) {
        return String.format("%d.%02d", (int) (Math.random() * (max - min)) + min, (int) (Math.random() * 99));
    }

    private static int getRandomAgeInMonths(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    private static String getRandomAndRemove(Set<String> set) {
        long index = System.currentTimeMillis() % set.size();
        String picked = null;
        int i = 0;
        for (String item : set) {
            if (i == index) { picked = item; break; }
        }
        if (picked == null) picked = set.iterator().next();
        set.remove(picked);
        return picked;
    }
} 