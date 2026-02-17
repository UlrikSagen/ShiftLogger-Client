package shiftlogger.storage;

import java.util.List;

import shiftlogger.model.Contract;
import shiftlogger.model.LegacyTimeEntry;
import shiftlogger.model.TimeEntry;

public interface TimeRepository {

    Contract loadContract();
    void saveContract(Contract contract);
    
    List<LegacyTimeEntry> loadEntries();
    void saveEntries(List<LegacyTimeEntry> entries);
}
