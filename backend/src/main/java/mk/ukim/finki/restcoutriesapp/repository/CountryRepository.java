package mk.ukim.finki.restcoutriesapp.repository;

import mk.ukim.finki.restcoutriesapp.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
