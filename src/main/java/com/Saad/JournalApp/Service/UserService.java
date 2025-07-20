package com.Saad.JournalApp.Service;
import com.Saad.JournalApp.Entity.UserEntry;
import com.Saad.JournalApp.Repository.UserEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    private UserEntryRepository userEntryRepository;
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