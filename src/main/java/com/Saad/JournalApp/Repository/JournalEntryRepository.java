package com.Saad.JournalApp.Repository;

import com.Saad.JournalApp.Entity.JournalEntry;
import com.Saad.JournalApp.Entity.UserEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {
}