package net.engineeringdigest.journalApp.api.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuotesResponse {
    //    @JsonProperty("quote_xyz") -> It is used if property name is different in coming json
    private String quote;
    private String author;
    private String category;
}

