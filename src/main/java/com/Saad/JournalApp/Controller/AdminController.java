package com.Saad.JournalApp.Controller;

import com.Saad.JournalApp.Entity.UserEntry;
import com.Saad.JournalApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        List<UserEntry> users = userService.getAllUsers();
        if (users != null && !users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/create-admin")
    public void createAdmin(@RequestBody UserEntry userEntry) {
        userService.saveAdmin(userEntry);
    }
}
