package mk.ukim.finki.restcoutriesapp.service;

import mk.ukim.finki.restcoutriesapp.model.Country;
import mk.ukim.finki.restcoutriesapp.model.dto.CountryDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CountryService {
    List<CountryDTO> getAllCountries();
    List<CountryDTO> getCountriesByRegion(String region);
    Map<String, Long> countCountriesByRegion();
    List<CountryDTO> getFavouriteCountries(List<CountryDTO> countries);
    Optional<Country> findCountryById(long id);
    void deleteCountryById(Long id);
    Country save(Country country);
    Optional<Country> update(Long id, Country country);
    List<Country> getMyFavouriteCountries();
}
