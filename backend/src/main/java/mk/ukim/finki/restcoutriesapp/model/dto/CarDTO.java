package mk.ukim.finki.restcoutriesapp.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class CarDTO {
    private List<String> signs;
    private String side;

    // Getters and setters
}
