package com.Saad.JournalApp.Service;

import com.Saad.JournalApp.Entity.JournalEntry;
import com.Saad.JournalApp.Entity.UserEntry;
import com.Saad.JournalApp.Repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;
    @Transactional
    public void saveJournalForUser(JournalEntry journalEntry, String username ) {
        try{
            UserEntry userEntry = userService.findByUsername(username);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry savedJournalEntry = journalEntryRepository.save(journalEntry);
            userEntry.getJournalEntries().add(savedJournalEntry);
            userService.saveUser(userEntry);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void saveJournal(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAllJournals(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findJournalById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    public void deleteJournalById(ObjectId id, String username) {
        UserEntry userEntry = userService.findByUsername(username);
        userEntry.getJournalEntries().removeIf(x->x.getId().equals(id));
        userService.saveUser(userEntry);
        journalEntryRepository.deleteById(id);
    }
}