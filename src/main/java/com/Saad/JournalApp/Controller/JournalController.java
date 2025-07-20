package com.Saad.JournalApp.Controller;

import com.Saad.JournalApp.Entity.JournalEntry;
import com.Saad.JournalApp.Entity.UserEntry;
import com.Saad.JournalApp.Service.JournalService;
import com.Saad.JournalApp.Service.UserService;
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
public class JournalController {
    @Autowired
    private JournalService journalService;
    @Autowired
    private UserService userService;
    @GetMapping("/{username}")
    public ResponseEntity<?> getJournalEntriesByUser(@PathVariable String username) {
        UserEntry userEntry = userService.findByUsername(username);
        List<JournalEntry> journalEntries = userEntry.getJournalEntries();
        if (!journalEntries.isEmpty()&& journalEntries != null) {
            return new ResponseEntity<>(journalEntries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<?> getJournalById(@PathVariable ObjectId myId) {
        Optional<JournalEntry>isFound = journalService.findJournalById(myId);
        if (isFound != null) {
            return new ResponseEntity<>(isFound, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping({"/{username}"})
    public ResponseEntity<?> createJournal(@RequestBody JournalEntry journalEntry, @PathVariable String username) {
        try {
            journalEntry.setDate(LocalDateTime.now());
            journalService.saveJournalForUser(journalEntry,username);
            return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/id/{username}/{myId}")
    public ResponseEntity<?> deleteJournalById(@PathVariable ObjectId myId, @PathVariable String username) {
        UserEntry userEntry = userService.findByUsername(username);
        journalService.deleteJournalById(myId,username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/id/{username}/{myId}")
    public ResponseEntity<?> updateJournalById(@PathVariable ObjectId myId,
                                        @RequestBody JournalEntry updatedEntry,
                                        @PathVariable String username) {
         JournalEntry existingEntry = journalService.findJournalById(myId).orElse(null);

        if (existingEntry != null) {
            existingEntry.setTitle(updatedEntry.getTitle() != null && !updatedEntry.getTitle().equals("") ? updatedEntry.getTitle() : existingEntry.getTitle());
            existingEntry.setContent(updatedEntry.getContent() != null && !updatedEntry.getContent().equals("") ? updatedEntry.getContent() : existingEntry.getContent());
            journalService.saveJournal(existingEntry);
            return new ResponseEntity<>(existingEntry,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}