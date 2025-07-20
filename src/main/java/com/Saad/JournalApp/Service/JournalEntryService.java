package com.Saad.JournalApp.Service;

import com.Saad.JournalApp.Entity.JournalEntry;
import com.Saad.JournalApp.Entity.UserEntry;
import com.Saad.JournalApp.Repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserEntryService userEntryService;
    public void saveEntry(JournalEntry journalEntry, String username ) {
        UserEntry userEntry = userEntryService.findByUsername(username);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry savedJournalEntry = journalEntryRepository.save(journalEntry);
        userEntry.getJournalEntries().add(savedJournalEntry);
        userEntryService.saveEntry(userEntry);
    }

    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }
    public Optional<JournalEntry> findById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }
    public void deleteById(ObjectId id, String username) {
        UserEntry userEntry = userEntryService.findByUsername(username);
        userEntry.getJournalEntries().removeIf(x->x.getId().equals(id));
        userEntryService.saveEntry(userEntry);
        journalEntryRepository.deleteById(id);
    }
}