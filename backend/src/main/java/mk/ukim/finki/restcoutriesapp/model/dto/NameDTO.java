package mk.ukim.finki.restcoutriesapp.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class NameDTO {
    public String common;
    private String official;
    private Map<String, NativeNameDTO> nativeName;

    // Getters and setters

    public NameDTO(String common, String official, Map<String, NativeNameDTO> nativeName) {
        this.common = common;
        this.official = official;
        this.nativeName = nativeName;
    }

    public NameDTO() {
    }
}
