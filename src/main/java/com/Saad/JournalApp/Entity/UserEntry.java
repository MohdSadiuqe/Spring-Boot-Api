package com.Saad.JournalApp.Entity;
import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
@Document(collection = "user_entry")
@Data
public class UserEntry {
    @Id
    public ObjectId id;
    @Indexed(unique = true)
    @NonNull
    public String username;
    @NonNull
    public String password;
    public List<String> roles;
    private List<JournalEntry>journalEntries=new ArrayList<>();
}