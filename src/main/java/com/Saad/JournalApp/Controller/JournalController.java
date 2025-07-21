package com.Saad.JournalApp.Controller;

import com.Saad.JournalApp.Entity.JournalEntry;
import com.Saad.JournalApp.Entity.UserEntry;
import com.Saad.JournalApp.Service.JournalService;
import com.Saad.JournalApp.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalController {
    @Autowired
    private JournalService journalService;
    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<?> getJournalEntriesByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username =authentication.getName();
        UserEntry userEntry = userService.findByUsername(username);
        List<JournalEntry> journalEntries = userEntry.getJournalEntries();
        if (!journalEntries.isEmpty()&& journalEntries != null) {
            return new ResponseEntity<>(journalEntries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<?> getJournalById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username =authentication.getName();
        UserEntry userEntry = userService.findByUsername(username);
        List<JournalEntry> collect = userEntry.getJournalEntries().stream().filter(x->x.getId().equals(myId)).collect(Collectors.toList( ));
        if(!collect.isEmpty()){
            Optional<JournalEntry>isFound = journalService.findJournalById(myId);
            if (isFound != null) {
                return new ResponseEntity<>(isFound, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createJournal(@RequestBody JournalEntry journalEntry) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username =authentication.getName();
            journalService.saveJournalForUser(journalEntry,username);
            return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteJournalById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username =authentication.getName();
        boolean remove = journalService.deleteJournalById(myId, username);
        if (remove) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/id/{myId}")
    public ResponseEntity<?> updateJournalById(@PathVariable ObjectId myId,
                                        @RequestBody JournalEntry updatedEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username =authentication.getName();
        UserEntry userEntry = userService.findByUsername(username);
        List<JournalEntry> collect = userEntry.getJournalEntries().stream().filter(x->x.getId().equals(myId)).collect(Collectors.toList( ));
        if(!collect.isEmpty()){
            Optional<JournalEntry>isFound = journalService.findJournalById(myId);
            if(isFound.isPresent()){
                JournalEntry existingEntry = isFound.get();
                existingEntry.setTitle(updatedEntry.getTitle() != null && !updatedEntry.getTitle().equals("") ? updatedEntry.getTitle() : existingEntry.getTitle());
                existingEntry.setContent(updatedEntry.getContent() != null && !updatedEntry.getContent().equals("") ? updatedEntry.getContent() : existingEntry.getContent());
                journalService.saveJournal(existingEntry);
                return new ResponseEntity<>(existingEntry,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}