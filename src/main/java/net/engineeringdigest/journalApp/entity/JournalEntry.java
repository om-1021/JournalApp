package net.engineeringdigest.journalApp.entity;


// this is called POJO - plain old java object

import lombok.Data;

import lombok.NoArgsConstructor;
import lombok.NonNull;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Document(collection = "journal_entries")
//@Getter
//@Setter
//@Data = Getter,setter,ToString, RequiredArgsConstructor, EqualsAndHashcode one annotation can generate these many annotations
// lombok annotations (eg:@Data) helps to generate getter and setters at the compile time so that we can write clean code
@Data
@NoArgsConstructor
public class JournalEntry {

    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private String content;
    private LocalDateTime date;

}
