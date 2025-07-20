package com.Saad.JournalApp.Controller;

import com.Saad.JournalApp.Entity.UserEntry;
import com.Saad.JournalApp.Service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/user")
public class UserEntryController {
    @Autowired
    private UserEntryService userEntryService;

    @GetMapping
    public List<UserEntry> getUser(){
        return userEntryService.getAll();
    }

    @PostMapping
    public void createUser(@RequestBody UserEntry userEntry) {
        userEntryService.saveEntry(userEntry);
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> updateById(@RequestBody UserEntry userEntry, @PathVariable String username) {
        UserEntry UserInDb = userEntryService.findByUsername(username);
        if(UserInDb != null){
            UserInDb.setUsername(userEntry.getUsername());
            UserInDb.setPassword(userEntry.getPassword());
            userEntryService.saveEntry(UserInDb);
            return new ResponseEntity<>(UserInDb,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
