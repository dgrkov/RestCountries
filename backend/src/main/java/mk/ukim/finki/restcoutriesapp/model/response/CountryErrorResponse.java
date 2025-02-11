package mk.ukim.finki.restcoutriesapp.model.response;

import lombok.Data;

@Data
public class CountryErrorResponse {
    private int status;
    private String message;
    private long timestamp;

    public CountryErrorResponse(int status, String message, long timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    public CountryErrorResponse() {
    }

}
