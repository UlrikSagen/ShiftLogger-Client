package storage;

import model.TimeEntry;
import java.util.List;
import java.util.ArrayList;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.nio.file.Files;

import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.time.LocalDate;
import java.time.LocalTime;


public class CSVRepository {

    private static final Path DATA_FILE = Paths.get(System.getProperty("user.dir"), "entries.csv");

    public static List<TimeEntry> loadCSVEntries() {
        List<TimeEntry> entries = new ArrayList<>();

        if (!Files.exists(DATA_FILE)) {
            return entries; // Ingen fil ennå = ingen entries
        }

        try (CSVReader reader = new CSVReader(new FileReader(DATA_FILE.toFile()))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                TimeEntry entry = new TimeEntry(
                        LocalDate.parse(line[0]),
                        LocalTime.parse(line[1]),
                        LocalTime.parse(line[2])
                );
                entries.add(entry);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return entries;
    }


    public static void saveEntries(List<TimeEntry> entries) {
        try (CSVWriter writer = new CSVWriter(
                new FileWriter(DATA_FILE.toFile()),
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END)) {
                 
            for (TimeEntry entry : entries) {
                writer.writeNext(new String[]{
                        entry.getDate().toString(),
                        entry.getStart().toString(),
                        entry.getEnd().toString()
                }, false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String serialize(TimeEntry entry) {
        return entry.getDate() + ";" + entry.getStart() + ";" + entry.getEnd();
    }

    private static TimeEntry deserialize(String line) {
        String[] parts = line.split(";");
        return new TimeEntry(
            java.time.LocalDate.parse(parts[0]),
            java.time.LocalTime.parse(parts[1]),
            java.time.LocalTime.parse(parts[2])
        );
    }
}
