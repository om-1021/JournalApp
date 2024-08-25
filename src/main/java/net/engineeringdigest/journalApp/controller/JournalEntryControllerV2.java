package net.engineeringdigest.journalApp.controller;


import net.engineeringdigest.journalApp.entity.JournalEntry;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private UserService userService;

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping("{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUsers(@PathVariable String userName) {
        User user = userService.findByUserName(userName);
        List<JournalEntry> allJournals = user.getJournalEntries();
        if (allJournals != null && !allJournals.isEmpty())
            return new ResponseEntity<>(allJournals, HttpStatus.OK);
        else
            return new ResponseEntity<>("No Journals Found", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{userName}")
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntry, @PathVariable String userName) {
        try {
            journalEntryService.saveEntry(myEntry, userName);
            return new ResponseEntity<>("Journal with title " + myEntry.getTitle() + " created successfully!!!", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Journal can't be created something went wrong ", HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("id/{myId}")
    public ResponseEntity<?> getJournalEntryByID(@PathVariable ObjectId myId) {
        Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
        if (journalEntry.isPresent()) {
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        } else
            return new ResponseEntity<>("No journal Exists", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{userName}/{myId}")
    public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId myId, @PathVariable String userName) {
        Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
        if (journalEntry.isPresent()) {
            journalEntryService.deleteById(myId, userName);
            JournalEntry j = journalEntry.get();
            return new ResponseEntity<>("Journal with title : " + j.getTitle() + " is Deleted Successfully", HttpStatus.OK);
        } else
            return new ResponseEntity<>("Id not found", HttpStatus.NOT_FOUND);
    }

    @PutMapping("id/{userName}/{myId}")
    public ResponseEntity<?> updateJournalById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry, @PathVariable String userName) {
        JournalEntry oldEntry = journalEntryService.findById(myId).orElse(null);

        if (oldEntry != null) {
            oldEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : oldEntry.getContent());
            journalEntryService.saveEntry(oldEntry);
            return new ResponseEntity<>("Journal -> " + oldEntry.getTitle() + " has been updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Journal can't updated ", HttpStatus.NOT_FOUND);
        }

    }
}
