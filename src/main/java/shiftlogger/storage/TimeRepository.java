package shiftlogger.storage;

import java.util.List;

import shiftlogger.model.Contract;
import shiftlogger.model.TimeEntry;

public interface TimeRepository {

    Contract loadContract();
    void saveContract(Contract contract);
    
    List<TimeEntry> loadEntries();
    void saveEntries(List<TimeEntry> entries);
}
