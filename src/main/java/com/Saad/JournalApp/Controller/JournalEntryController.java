package com.Saad.JournalApp.Controller;

import com.Saad.JournalApp.Entity.JournalEntry;
import com.Saad.JournalApp.Entity.UserEntry;
import com.Saad.JournalApp.Service.JournalEntryService;
import com.Saad.JournalApp.Service.UserEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserEntryService userEntryService;
    @GetMapping("/{username}")
    public ResponseEntity<?> getJournalEntriesByUser(@PathVariable String username) {
        UserEntry userEntry = userEntryService.findByUsername(username);
        List<JournalEntry> journalEntries = userEntry.getJournalEntries();
        if (!journalEntries.isEmpty()&& journalEntries != null) {
            return new ResponseEntity<>(journalEntries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PostMapping({"/{username}"})
    public ResponseEntity<?> createUser(@RequestBody JournalEntry journalEntry, @PathVariable String username) {
        try {
            journalEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(journalEntry,username);
            return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/id/{myId}")
    public ResponseEntity<?> getById(@PathVariable ObjectId myId) {
        Optional<JournalEntry>isFound = journalEntryService.findById(myId);
        if (isFound != null) {
            return new ResponseEntity<>(isFound, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{username}/{myId}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId myId, @PathVariable String username) {
        UserEntry userEntry = userEntryService.findByUsername(username);
        journalEntryService.deleteById(myId,username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/id/{username}/{myId}")
    public ResponseEntity<?> updateById(@PathVariable ObjectId myId,
                                        @RequestBody JournalEntry updatedEntry,
                                        @PathVariable String username) {
         JournalEntry existingEntry = journalEntryService.findById(myId).orElse(null);

        if (existingEntry != null) {
            existingEntry.setTitle(updatedEntry.getTitle() != null && !updatedEntry.getTitle().equals("") ? updatedEntry.getTitle() : existingEntry.getTitle());
            existingEntry.setContent(updatedEntry.getContent() != null && !updatedEntry.getContent().equals("") ? updatedEntry.getContent() : existingEntry.getContent());
            journalEntryService.saveEntry(existingEntry);
            return new ResponseEntity<>(existingEntry,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}