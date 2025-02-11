package mk.ukim.finki.restcoutriesapp.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "country")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "country_name")
    private String name;

    @Column(name = "capital")
    private String capital;

    @Column(name = "country_region")
    private String region;

    @Column(name = "currency_name")
    private String currencyName;

    @Column(name = "currency_symbol")
    private char currencySymbol;

    @Column(name = "default_language")
    private String language;

    public Country(String name,String capital, String region, String currencyName, char currencySymbol, String language) {
        this.name = name;
        this.capital = capital;
        this.region = region;
        this.currencyName = currencyName;
        this.currencySymbol = currencySymbol;
        this.language = language;
    }

    public Country() {

    }
}
