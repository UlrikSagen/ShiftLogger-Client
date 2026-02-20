package shiftlogger.storage;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import shiftlogger.model.Settings;

public class SettingsLoader {

    private static final Path settingsPath = Path.of(System.getProperty("user.home"), ".shiftlogger", "settings.json");
    private static ObjectMapper objectMapper = new ObjectMapper();


    public static Settings loadSettings() {
        if (!Files.exists(settingsPath)) {
            return createDefault();
        }
        try (InputStream in = new FileInputStream(settingsPath.toFile())) {
            String json = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))
                    .lines().collect(Collectors.joining("\n"));

            Settings settings = objectMapper.readValue(json, Settings.class);

            return settings;

        } catch (Exception e) {
            throw new IllegalStateException("Feil ved lasting av settings. " + e.getMessage(), e);
        }
    }

    public static void saveSettings(Settings settings){
        try {
            Files.createDirectories(settingsPath.getParent());
            objectMapper.writeValue(settingsPath.toFile(), settings);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new IllegalStateException("Feil ved lagring av settings: " + e.getMessage(), e);
        }
    }

    public static Settings createDefault(){
        Settings defaults = new Settings("dark");
        saveSettings(defaults);
        return defaults;
    }
    
}
