package com.Saad.JournalApp.Controller;

import com.Saad.JournalApp.Entity.UserEntry;
import com.Saad.JournalApp.Repository.UserEntryRepository;
import com.Saad.JournalApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserEntryRepository userEntryRepository;

    @GetMapping
    public List<UserEntry> getAllUsers(){
        return userService.getAllUsers();
    }

    @PutMapping
    public ResponseEntity<?> updateUserByUsername(@RequestBody UserEntry userEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username =authentication.getName();
        UserEntry UserInDb = userService.findByUsername(username);
        UserInDb.setUsername(userEntry.getUsername());
        UserInDb.setPassword(userEntry.getPassword());
        userService.saveNewUser(UserInDb);
        return new ResponseEntity<>(UserInDb, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<? >DeleteUserByUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userEntryRepository.deleteByUsername(authentication.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}