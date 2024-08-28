package net.engineeringdigest.journalApp.entity;


// this is called POJO - plain old java object

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Document(collection = "users")

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private ObjectId id;
    @Indexed(unique = true) // helps to search faster and maintains unique usernames i.e. why we do indexing
    // ,but it will not do indexing automatically we need to configure application.properties for this
    // just write spring.data.mongodb.auto-index-creation=true
    @NonNull // comes from lombok
    private String userName;
    @NonNull
    private String password;
    @DBRef // used to create reference
    // this means our list will keep reference of journal entries which are stored in journal entries collection
    // it will not store the whole journal it will just keep the ObjectId for reference
    private List<JournalEntry> journalEntries = new ArrayList<>();
    private List<String> roles; // used for authorization

    private String email;
    private boolean sentimentAnalysis;
}
