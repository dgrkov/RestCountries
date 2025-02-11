package mk.ukim.finki.restcoutriesapp.model.dto;

import lombok.Data;

@Data
public class NativeNameDTO {
    private String official;
    private String common;

    // Getters and setters

    public NativeNameDTO(String official, String common) {
        this.official = official;
        this.common = common;
    }

    public NativeNameDTO() {
    }
}
