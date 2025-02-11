package mk.ukim.finki.restcoutriesapp.web.controllerAdvice;

import mk.ukim.finki.restcoutriesapp.model.exception.InvalidRegionException;
import mk.ukim.finki.restcoutriesapp.model.response.CountryErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CountryRESTExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<CountryErrorResponse> handleException(InvalidRegionException ex) {
        CountryErrorResponse response = new CountryErrorResponse();
        response.setMessage(ex.getMessage());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
