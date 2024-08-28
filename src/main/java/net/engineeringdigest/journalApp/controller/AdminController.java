package net.engineeringdigest.journalApp.controller;

import java.util.*;

import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserService userService;

    @Autowired
    AppCache appCache;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        List<User> listOfUsers = userService.getAllUsers();
        if (listOfUsers.isEmpty()) {
            return new ResponseEntity<>("No users in the database found", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(listOfUsers.stream()
                    .map(User::getUserName) // Assuming your User class has a getUserName method
                    .toArray(String[]::new), HttpStatus.OK);
        }
    }

    @PostMapping("/create-admin-user")
    public ResponseEntity<?> createAdminUser(@RequestBody User user) {
        userService.createAdmin(user);
        return new ResponseEntity<>(user.getUserName() + " is now admin", HttpStatus.OK);
    }

    @GetMapping("/clear-app-cache")
    public String clearAppCache() {
        appCache.init();
        return "App cache cleared successfully";
    }
}
