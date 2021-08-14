package i.am.cal.antisteal;

import java.nio.file.Path;
import java.util.Map;

public class Antisteal {
    static {
        System.out.println("bruh");
    }

    public static void check(Path pathToFile, CloseEvent closeEvent, Map<String, String> whitelist, Class<?> yourClass) {
        // Yeet
    }

    @FunctionalInterface
    public interface CloseEvent {
        void close();
    }
}