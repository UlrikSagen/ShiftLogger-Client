package shiftlogger.storage;

import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import shiftlogger.model.Contract;
import shiftlogger.model.TimeEntry;


public class CSVRepository implements TimeRepository{

    private static final Path DATA_FILE = Paths.get(System.getProperty("user.dir"), "entries.csv");
    private final ContractLoader contractLoader = new ContractLoader();

    public List<TimeEntry> loadEntries() {
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
            throw new RuntimeException("Failed to read CSV file: " + DATA_FILE, e);
        }
        return entries;
    }


    public void saveEntries(List<TimeEntry> entries) {
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
            throw new RuntimeException("Failed to write CSV file: " + DATA_FILE, e);
        }
    }
    public Contract loadContract(){
        return contractLoader.loadContract();
    }

    public void saveContract(Contract contract){
        contractLoader.saveContract(contract);
    }
}
