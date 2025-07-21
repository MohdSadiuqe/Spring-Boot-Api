package com.Saad.JournalApp.Repository;

import com.Saad.JournalApp.Entity.UserEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserEntryRepository extends MongoRepository<UserEntry, ObjectId> {
    UserEntry findByUsername(String username);
    void deleteByUsername(String username);
}