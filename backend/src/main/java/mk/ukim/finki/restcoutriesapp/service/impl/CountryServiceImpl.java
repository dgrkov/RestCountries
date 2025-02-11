package mk.ukim.finki.restcoutriesapp.service.impl;

import mk.ukim.finki.restcoutriesapp.model.Country;
import mk.ukim.finki.restcoutriesapp.model.dto.*;
import mk.ukim.finki.restcoutriesapp.model.exception.InvalidRegionException;
import mk.ukim.finki.restcoutriesapp.repository.CountryRepository;
import mk.ukim.finki.restcoutriesapp.service.CountryService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService {

    private final RestTemplate restTemplate;
    private final CountryRepository countryRepository;

    // External API URL to fetch country data
    private static final String API_URL = "https://restcountries.com/v3.1/all";

    public CountryServiceImpl(RestTemplate restTemplate, CountryRepository countryRepository) {
        this.restTemplate = restTemplate;
        this.countryRepository = countryRepository;
    }

    // Method to fetch all countries and map to CountryDTO
    public List<CountryDTO> getAllCountries() {
        CountryDTO[] countries = restTemplate.getForObject(API_URL, CountryDTO[].class);
        return Arrays.asList(countries);  // Convert array to List
    }

    @Override
    public List<CountryDTO> getCountriesByRegion(String region) {
        String[] regions = {"Americas", "Asia", "Europe", "Africa", "Antarctic", "Oceania"};
        String finalRegion = region.substring(0, 1).toUpperCase() + region.substring(1);
        for (int i = 0; i < regions.length; i++)
            if (finalRegion.equals(regions[i])) {
                List<CountryDTO> allCountries = getAllCountries();
                return allCountries.stream()
                        .filter(country -> finalRegion.equals(country.getRegion()))
                        .collect(Collectors.toList());
            }
        throw new InvalidRegionException("Invalid region: " + region);
    }

    @Override
    public Map<String, Long> countCountriesByRegion() {
        List<CountryDTO> allCountries = getAllCountries();
        return allCountries.stream().collect(Collectors.groupingBy(CountryDTO::getRegion, Collectors.counting()));
    }

    @Override
    public List<CountryDTO> getFavouriteCountries(List<CountryDTO> countries) {
        List<String> favourites = new ArrayList<>();
        favourites.add("Switzerland");
        favourites.add("Germany");
        favourites.add("Italy");
         return countries.stream()
                .filter(country -> favourites.contains(country.getName().getCommon()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Country> findCountryById(long id) {
        return this.countryRepository.findById(id);
    }

    @Override
    public void deleteCountryById(Long id) {
        this.countryRepository.deleteById(id);
    }

    @Override
    public Country save(Country country) {
        country.setId(null);
        return this.countryRepository.save(country);
    }

    @Override
    public Optional<Country> update(Long id, Country country) {
        Country oldCountry = findCountryById(id).orElseThrow(RuntimeException::new);
        oldCountry.setName(country.getName());
        oldCountry.setCapital(country.getCapital());
        oldCountry.setRegion(country.getRegion());
        oldCountry.setLanguage(country.getLanguage());
        oldCountry.setCurrencySymbol(country.getCurrencySymbol());
        oldCountry.setCurrencyName(country.getCurrencyName());
        return Optional.of(countryRepository.save(oldCountry));
    }

    @Override
    public List<Country> getMyFavouriteCountries() {
        return countryRepository.findAll();
    }

}
