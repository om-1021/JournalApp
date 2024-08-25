package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String healthCheckup() {
        return "Ok";
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        if (!user.getUserName().isEmpty()) {
            User userExists = userService.findByUserName(user.getUserName());
            if (userExists != null) {
                return new ResponseEntity<>("User " + user.getUserName() + " already Exists", HttpStatus.NOT_ACCEPTABLE);
            }
            userService.saveNewUser(user);
            return new ResponseEntity<>("User " + user.getUserName() + " created Successfully !!!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User " + user.getUserName() + " can't be created :(", HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
