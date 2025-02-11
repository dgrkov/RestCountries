package mk.ukim.finki.restcoutriesapp.web;

import mk.ukim.finki.restcoutriesapp.model.Country;
import mk.ukim.finki.restcoutriesapp.model.dto.CountryDTO;
import mk.ukim.finki.restcoutriesapp.service.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class CountriesController {
    private final CountryService countryService;

    public CountriesController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping(path = {"/", "/countries"})
    public List<CountryDTO> findAll() {
        return countryService.getAllCountries();
    }

    @GetMapping("/countries/{region}")
    public List<CountryDTO> getCountriesByRegion(@PathVariable String region) {
        String trimmedParam = region.replaceAll(" ", "");
        return countryService.getCountriesByRegion(trimmedParam);
    }

    @GetMapping("/countries/region/count")
    public Map<String, Long> countByRegion() {
        return countryService.countCountriesByRegion();
    }
    // CRUD functionalities
    @GetMapping("/countries/favourites")
    public List<CountryDTO> getFavouriteCountries() {
        List<CountryDTO> allCountries = countryService.getAllCountries();
        return countryService.getFavouriteCountries(allCountries);
    }

    @GetMapping("/countries/myFavourites")
    public List<Country> getMyFavouriteCountries() {
        return this.countryService.getMyFavouriteCountries();
    }

    @PostMapping("/countries/favourites")
    public Country save(@RequestBody Country country) {
        country.setId(null);
        return this.countryService.save(country);
    }

    @PutMapping("/countries/favourites/{id}")
    public ResponseEntity<Country> update(@PathVariable Long id, @RequestBody Country country) {
        return this.countryService.update(id, country)
                .map(country1 -> ResponseEntity.ok().body(country1))
                .orElse(ResponseEntity.notFound().build());

    }


    @DeleteMapping("/countries/favourites/{id}")
    public ResponseEntity<Country> delete(@PathVariable Long id) {
        if (this.countryService.findCountryById(id).isPresent()) {
            this.countryService.deleteCountryById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
