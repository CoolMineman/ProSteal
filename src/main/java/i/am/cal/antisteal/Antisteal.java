package i.am.cal.antisteal;

import java.nio.file.Path;
import java.util.Map;

public class Antisteal {
    public static void check(Path pathToFile, CloseEvent closeEvent, Map<String, String> whitelist, Class<?> yourClass) {
        // Yeet
    }

    @FunctionalInterface
    public interface CloseEvent {
        void close();
    }
}