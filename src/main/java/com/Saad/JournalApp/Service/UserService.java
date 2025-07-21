package com.Saad.JournalApp.Service;
import com.Saad.JournalApp.Entity.UserEntry;
import com.Saad.JournalApp.Repository.UserEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    private UserEntryRepository userEntryRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntry saveNewUser(UserEntry user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        return userEntryRepository.save(user);
    }

    public UserEntry saveAdmin(UserEntry user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        return userEntryRepository.save(user);
    }

    public void saveUser(UserEntry userEntry) {
        userEntryRepository.save(userEntry);
    }

    public List<UserEntry> getAllUsers(){
        return userEntryRepository.findAll();
    }

    public Optional<UserEntry> findUserById(ObjectId id) {
        return userEntryRepository.findById(id);
    }

    public void deleteUserByUsername(ObjectId id) {
        userEntryRepository.deleteById(id);
    }

    public UserEntry findByUsername(String userEntry) {
        return userEntryRepository.findByUsername(userEntry);
    }
}