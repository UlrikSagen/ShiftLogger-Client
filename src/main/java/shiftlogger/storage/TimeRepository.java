package storage;

import java.util.List;

import model.Contract;
import model.TimeEntry;

public interface TimeRepository {

    Contract loadContract();
    void saveContract(Contract contract);
    
    List<TimeEntry> loadEntries();
    void saveEntries(List<TimeEntry> entries);
}
