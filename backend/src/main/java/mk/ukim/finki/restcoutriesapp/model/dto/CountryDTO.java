package mk.ukim.finki.restcoutriesapp.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class CountryDTO {
    private NameDTO name;
    private String[] tld;
    private String cca2;
    private String ccn3;
    private String cca3;
    private boolean independent;
    private String status;
    private boolean unMember;
    private Map<String, CurrencyDTO> currencies;
    private IDDDTO idd;
    private String[] capital;
    private String[] altSpellings;
    private String region;
    private Map<String, String> languages;
    private Map<String, TranslationDTO> translations;
    private double[] latlng;
    private boolean landlocked;
    private int area;
    private DemonymDTO demonyms;
    private String flag;
    private Map<String, String> maps;
    private int population;
    private CarDTO car;
    private String[] timezones;
    private String[] continents;
    private FlagDTO flags;
    private String startOfWeek;
    private CapitalInfoDTO capitalInfo;

    public CountryDTO(NameDTO name, String[] tld, String cca2, String ccn3, String cca3, boolean independent, String status, boolean unMember, Map<String, CurrencyDTO> currencies, IDDDTO idd, String[] capital, String[] altSpellings, String region, Map<String, String> languages, Map<String, TranslationDTO> translations, double[] latlng, boolean landlocked, int area, DemonymDTO demonyms, String flag, Map<String, String> maps, int population, CarDTO car, String[] timezones, String[] continents, FlagDTO flags, String startOfWeek, CapitalInfoDTO capitalInfo) {
        this.name = name;
        this.tld = tld;
        this.cca2 = cca2;
        this.ccn3 = ccn3;
        this.cca3 = cca3;
        this.independent = independent;
        this.status = status;
        this.unMember = unMember;
        this.currencies = currencies;
        this.idd = idd;
        this.capital = capital;
        this.altSpellings = altSpellings;
        this.region = region;
        this.languages = languages;
        this.translations = translations;
        this.latlng = latlng;
        this.landlocked = landlocked;
        this.area = area;
        this.demonyms = demonyms;
        this.flag = flag;
        this.maps = maps;
        this.population = population;
        this.car = car;
        this.timezones = timezones;
        this.continents = continents;
        this.flags = flags;
        this.startOfWeek = startOfWeek;
        this.capitalInfo = capitalInfo;
    }

    public CountryDTO() {
    }
}

