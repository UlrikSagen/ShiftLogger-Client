package shiftlogger.service;

import java.util.List;
import java.util.UUID;

import shiftlogger.http.ApiClient;
import shiftlogger.model.LegacyTimeEntry;
import shiftlogger.model.TimeEntry;
import shiftlogger.storage.TimeRepository;

public class SyncService {

    private List<TimeEntry> entries;
    private final TimeRepository repo;
    private final ApiClient api;

    public SyncService(TimeRepository repo, ApiClient api){
        this.repo = repo;
        this.api = api;
        //entries = repo.loadEntries();
    }

    
    public void syncOffline(List<LegacyTimeEntry> oldEntries) throws Exception{
        for (LegacyTimeEntry entry : oldEntries){
            //api.postEntry(entry.date(), entry.start(), entry.end());
        }
    }
}
