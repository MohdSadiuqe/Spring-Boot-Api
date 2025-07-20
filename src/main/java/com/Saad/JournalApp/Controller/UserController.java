package com.Saad.JournalApp.Controller;

import com.Saad.JournalApp.Entity.UserEntry;
import com.Saad.JournalApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserEntry> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping
    public void createUser(@RequestBody UserEntry userEntry) {
        userService.saveUser(userEntry);
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> updateUserByUsername(@RequestBody UserEntry userEntry, @PathVariable String username) {
        UserEntry UserInDb = userService.findByUsername(username);
        if (UserInDb != null) {
            UserInDb.setUsername(userEntry.getUsername());
            UserInDb.setPassword(userEntry.getPassword());
            userService.saveUser(UserInDb);
            return new ResponseEntity<>(UserInDb, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}