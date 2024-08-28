package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.api.response.QuotesResponse;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.QuotesService;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    QuotesService quotesService;
    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String healthCheckup() {
        return "Ok";
    }

    @GetMapping("/test-query")
    public List<User> getUserForSentimentAnalysis() {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,6}$"));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
        List<User> users = mongoTemplate.find(query, User.class);
        return users;
    }

    @GetMapping("/quote")
    public String getQuote() {
        QuotesResponse quotesResponse = quotesService.getAnimeQuote();

        return ("Quote : " + quotesResponse.getQuote() + " \nauthor: " + quotesResponse.getAuthor() + "\ncategory : " + quotesResponse.getCategory());
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
