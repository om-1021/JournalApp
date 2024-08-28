package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.api.response.QuotesResponse;
import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.constants.PlaceHolders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

//@Component old , use @Service its same but more readable tells that business logic is written here
@Service
public class QuotesService {

    private static final String animeName = "good";
    //    it is a class which processes a https request for us
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    AppCache appCache;
    // this annotation help us to store api keys in config files and retrieve it don't use static variable while storing it
    @Value("${quotes.api.key}")
    private String API_KEY;


//    @Bean
//    public HttpHeaders customHttpHeaders() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("X-Api-Key", API_KEY);
//        return headers;
//    }

    //Deserializing -> converting json to java POJO
    public QuotesResponse getAnimeQuote() {
        try {
//
            String API = appCache.APP_CACHE.get(AppCache.keys.QUOTES_API.toString());
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Api-Key", API_KEY);
            HttpEntity<?> httpEntity = new HttpEntity<>(headers);
            ResponseEntity<List<QuotesResponse>> response = restTemplate.exchange(API.replace(PlaceHolders.CATEGORY, "good"), HttpMethod.GET,
                    httpEntity, new ParameterizedTypeReference<List<QuotesResponse>>() {
                    });

            // Extract the first QuotesResponse from the list
            List<QuotesResponse> quotesList = response.getBody();
            if (quotesList != null && !quotesList.isEmpty()) {
                return quotesList.get(0); // Return the first quote
            } else {
                return null; // No quotes available
            }
        } catch (RestClientException e) {
            // Log the error message and return a custom response or null
            System.err.println("Error while calling the external API: " + e.getMessage());
            return null; // or throw a custom exception

        } catch (Exception e) {
            // General exception handling
            System.err.println("An unexpected error occurred: " + e.getMessage());
            return null; // or throw a custom exception
        }
    }
}
