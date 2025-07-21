package com.Saad.JournalApp.Controller;

import com.Saad.JournalApp.Entity.UserEntry;
import com.Saad.JournalApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserService userService;
    @PostMapping("/create-user")
    public void createUser(@RequestBody UserEntry userEntry) {
        userService.saveNewUser(userEntry);
    }
}
