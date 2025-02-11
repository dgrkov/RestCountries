package mk.ukim.finki.restcoutriesapp.service.impl;

import mk.ukim.finki.restcoutriesapp.model.Country;
import mk.ukim.finki.restcoutriesapp.model.dto.CountryDTO;
import mk.ukim.finki.restcoutriesapp.model.dto.NameDTO;
import mk.ukim.finki.restcoutriesapp.model.dto.NativeNameDTO;
import mk.ukim.finki.restcoutriesapp.model.exception.InvalidRegionException;
import mk.ukim.finki.restcoutriesapp.repository.CountryRepository;
import mk.ukim.finki.restcoutriesapp.service.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CountryServiceImplTest {

    @InjectMocks
    private CountryServiceImpl countryService;

    @Mock
    @MockBean
    private CountryRepository countryRepository;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        // Telling the mockito framework that we want to start the mocks
          MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCountries() {
        CountryDTO country1 = new CountryDTO();
        CountryDTO country2 = new CountryDTO();

        NativeNameDTO englishName1 = new NativeNameDTO("United Kingdom of Great Britain and Northern Ireland", "United Kingdom");
        NativeNameDTO spanishName1 = new NativeNameDTO("Reino Unido de Gran Bretaña e Irlanda del Norte", "Reino Unido");
        NativeNameDTO englishName2 = new NativeNameDTO("Federal Republic of Germany", "Germany");
        NativeNameDTO spanishName2 = new NativeNameDTO("República Federal de Alemania.", "Alemania");

        Map<String, NativeNameDTO> nativeNameMap1 = new HashMap<>();
        nativeNameMap1.put("eng", englishName1);
        nativeNameMap1.put("spa", spanishName1);
        Map<String, NativeNameDTO> nativeNameMap2 = new HashMap<>();
        nativeNameMap2.put("eng", englishName2);
        nativeNameMap2.put("spa", spanishName2);

        NameDTO nameDTO1 = new NameDTO("United Kingdom", "United Kingdom of Great Britain and Northern Ireland", nativeNameMap1);
        NameDTO nameDTO2 = new NameDTO("Germany", "Bundesrepublik Deutschland", nativeNameMap2);

        country1.setName(nameDTO1);
        country2.setName(nameDTO2);

        CountryDTO[] mockCountries = new CountryDTO[] {
                country1,
                country2
        };

        when(restTemplate.getForObject(anyString(), eq(CountryDTO[].class))).thenReturn(mockCountries);

        List<CountryDTO> result = countryService.getAllCountries();

        assertEquals(2, result.size());
        assertEquals("United Kingdom of Great Britain and Northern Ireland", result.get(0).getName().getOfficial());


    }

    @Test
    void getCountriesByRegion() {
        CountryDTO country1 = new CountryDTO();
        CountryDTO country2 = new CountryDTO();
        CountryDTO country3 = new CountryDTO();

        NativeNameDTO englishName1 = new NativeNameDTO("United Kingdom of Great Britain and Northern Ireland", "United Kingdom");
        NativeNameDTO spanishName1 = new NativeNameDTO("Reino Unido de Gran Bretaña e Irlanda del Norte", "Reino Unido");
        NativeNameDTO englishName2 = new NativeNameDTO("Federal Republic of Germany", "Germany");
        NativeNameDTO spanishName2 = new NativeNameDTO("República Federal de Alemania.", "Alemania");
        NativeNameDTO englishName3 = new NativeNameDTO("Federal Republic of Bolivia", "Bolivia");
        NativeNameDTO spanishName3 = new NativeNameDTO("República Federal de Bolivia.", "Bolivia");

        Map<String, NativeNameDTO> nativeNameMap1 = new HashMap<>();
        nativeNameMap1.put("eng", englishName1);
        nativeNameMap1.put("spa", spanishName1);
        Map<String, NativeNameDTO> nativeNameMap2 = new HashMap<>();
        nativeNameMap2.put("eng", englishName2);
        nativeNameMap2.put("spa", spanishName2);
        Map<String, NativeNameDTO> nativeNameMap3 = new HashMap<>();
        nativeNameMap3.put("eng", englishName3);
        nativeNameMap3.put("spa", spanishName3);

        NameDTO nameDTO1 = new NameDTO("United Kingdom", "United Kingdom of Great Britain and Northern Ireland", nativeNameMap1);
        NameDTO nameDTO2 = new NameDTO("Germany", "Bundesrepublik Deutschland", nativeNameMap2);
        NameDTO nameDTO3 = new NameDTO("Bolivia", "Estado Plurinacional de Bolivia", nativeNameMap3);

        country1.setName(nameDTO1);
        country2.setName(nameDTO2);
        country3.setName(nameDTO3);
        country1.setRegion("Europe");
        country2.setRegion("Europe");
        country3.setRegion("South America");

        CountryDTO[] mockCountries = new CountryDTO[] {
                country1,
                country2,
                country3
        };

        when(restTemplate.getForObject(anyString(), eq(CountryDTO[].class))).thenReturn(mockCountries);
        List<CountryDTO> result = countryService.getCountriesByRegion("Europe");
        Exception exception = assertThrows(InvalidRegionException.class, () -> {
            countryService.getCountriesByRegion("InvalidRegion");
        });

        assertEquals(2, result.size());
        assertEquals("Europe", result.get(0).getRegion());

        assertEquals("Invalid region: InvalidRegion", exception.getMessage());
    }


    @Test
    void countCountriesByRegion() {
        CountryDTO country1 = new CountryDTO();
        CountryDTO country2 = new CountryDTO();
        CountryDTO country3 = new CountryDTO();

        NativeNameDTO englishName1 = new NativeNameDTO("United Kingdom of Great Britain and Northern Ireland", "United Kingdom");
        NativeNameDTO spanishName1 = new NativeNameDTO("Reino Unido de Gran Bretaña e Irlanda del Norte", "Reino Unido");
        NativeNameDTO englishName2 = new NativeNameDTO("Federal Republic of Germany", "Germany");
        NativeNameDTO spanishName2 = new NativeNameDTO("República Federal de Alemania.", "Alemania");
        NativeNameDTO englishName3 = new NativeNameDTO("Federal Republic of Bolivia", "Bolivia");
        NativeNameDTO spanishName3 = new NativeNameDTO("República Federal de Bolivia.", "Bolivia");

        Map<String, NativeNameDTO> nativeNameMap1 = new HashMap<>();
        nativeNameMap1.put("eng", englishName1);
        nativeNameMap1.put("spa", spanishName1);
        Map<String, NativeNameDTO> nativeNameMap2 = new HashMap<>();
        nativeNameMap2.put("eng", englishName2);
        nativeNameMap2.put("spa", spanishName2);
        Map<String, NativeNameDTO> nativeNameMap3 = new HashMap<>();
        nativeNameMap3.put("eng", englishName3);
        nativeNameMap3.put("spa", spanishName3);

        NameDTO nameDTO1 = new NameDTO("United Kingdom", "United Kingdom of Great Britain and Northern Ireland", nativeNameMap1);
        NameDTO nameDTO2 = new NameDTO("Germany", "Bundesrepublik Deutschland", nativeNameMap2);
        NameDTO nameDTO3 = new NameDTO("Bolivia", "Estado Plurinacional de Bolivia", nativeNameMap3);

        country1.setName(nameDTO1);
        country2.setName(nameDTO2);
        country3.setName(nameDTO3);
        country1.setRegion("Europe");
        country2.setRegion("Europe");
        country3.setRegion("South America");

        CountryDTO[] mockCountries = new CountryDTO[] {
                country1,
                country2,
                country3
        };

        when(restTemplate.getForObject(anyString(), eq(CountryDTO[].class))).thenReturn(mockCountries);

        Map<String, Long> result = countryService.countCountriesByRegion();

        assertEquals(2, result.get("Europe"));
        assertEquals(1, result.get("South America"));

    }

    @Test
    void getFavouriteCountries() {
        CountryDTO country1 = new CountryDTO();
        CountryDTO country2 = new CountryDTO();
        CountryDTO country3 = new CountryDTO();

        NativeNameDTO englishName1 = new NativeNameDTO("United Kingdom of Great Britain and Northern Ireland", "United Kingdom");
        NativeNameDTO spanishName1 = new NativeNameDTO("Reino Unido de Gran Bretaña e Irlanda del Norte", "Reino Unido");
        NativeNameDTO englishName2 = new NativeNameDTO("Federal Republic of Germany", "Germany");
        NativeNameDTO spanishName2 = new NativeNameDTO("República Federal de Alemania.", "Alemania");
        NativeNameDTO englishName3 = new NativeNameDTO("Federal Republic of Bolivia", "Bolivia");
        NativeNameDTO spanishName3 = new NativeNameDTO("República Federal de Bolivia.", "Bolivia");

        Map<String, NativeNameDTO> nativeNameMap1 = new HashMap<>();
        nativeNameMap1.put("eng", englishName1);
        nativeNameMap1.put("spa", spanishName1);
        Map<String, NativeNameDTO> nativeNameMap2 = new HashMap<>();
        nativeNameMap2.put("eng", englishName2);
        nativeNameMap2.put("spa", spanishName2);
        Map<String, NativeNameDTO> nativeNameMap3 = new HashMap<>();
        nativeNameMap3.put("eng", englishName3);
        nativeNameMap3.put("spa", spanishName3);

        NameDTO nameDTO1 = new NameDTO("United Kingdom", "United Kingdom of Great Britain and Northern Ireland", nativeNameMap1);
        NameDTO nameDTO2 = new NameDTO("Germany", "Bundesrepublik Deutschland", nativeNameMap2);
        NameDTO nameDTO3 = new NameDTO("Bolivia", "Estado Plurinacional de Bolivia", nativeNameMap3);

        country1.setName(nameDTO1);
        country2.setName(nameDTO2);
        country3.setName(nameDTO3);
        country1.setRegion("Europe");
        country2.setRegion("Europe");
        country3.setRegion("South America");

        CountryDTO[] mockCountries = new CountryDTO[] {
                country1,
                country2,
                country3
        };

        List<CountryDTO> allCountries = Arrays.asList(mockCountries);

        List<CountryDTO> result = countryService.getFavouriteCountries(allCountries);

        assertEquals(1, result.size());
        assertTrue(result.stream().map(c -> c.getName().getCommon()).collect(Collectors.toList()).contains("Germany"));
    }

    @Test
    void findCountryById() {
        //Given
        Long countryId = 1L;
        Country country = new Country(
                "Brazil",
                "Brasília",
                "South America",
                "Real",
                'R',
                "Portuguese"
        );
        country.setId(countryId);

        // Mock the calls
        when(countryService.findCountryById(countryId)).thenReturn(Optional.of(country));

        // When
        Optional<Country> responseCountry = countryService.findCountryById(countryId);

        // Then
        assertTrue(responseCountry.isPresent());
        assertEquals(countryId, responseCountry.get().getId());
        assertEquals("Brazil", responseCountry.get().getName());
        assertEquals(country.getName(), responseCountry.get().getName());
        assertEquals(country.getCapital(), responseCountry.get().getCapital());
        assertEquals(country.getRegion(), responseCountry.get().getRegion());
        assertEquals(country.getCurrencyName(), responseCountry.get().getCurrencyName());
        assertEquals(country.getCurrencySymbol(), responseCountry.get().getCurrencySymbol());
        assertEquals(country.getLanguage(), responseCountry.get().getLanguage());
        verify(countryRepository, times(1)).findById(countryId);
    }

    @Test
    void testFindCountryById_NotFound() {
        // Arrange
        long countryId = 1L;
        // Mock the behavior of the repository
        when(countryRepository.findById(countryId)).thenReturn(Optional.empty());
        // Act
        Optional<Country> result = countryService.findCountryById(countryId);
        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void deleteCountryById() {
        Long countryId = 1L;
        doNothing().when(countryRepository).deleteById(countryId);
        countryService.deleteCountryById(countryId);
        verify(countryRepository, times(1)).deleteById(countryId);
    }

    @Test
    void shouldSaveCountry() {
        //given
        Country country1 = new Country("Japan", "Tokyo", "Asia", "Yen", '¥', "Japanese");

        //mock the calls
        when(countryService.save(country1)).thenReturn(country1);

        //when
        Country responseCountry = countryService.save(country1);

        //then
        assertEquals(country1.getName(), responseCountry.getName());
        assertEquals(country1.getCapital(), responseCountry.getCapital());
        assertEquals(country1.getRegion(), responseCountry.getRegion());
        assertEquals(country1.getCurrencyName(), responseCountry.getCurrencyName());
        assertEquals(country1.getCurrencySymbol(), responseCountry.getCurrencySymbol());
        assertEquals(country1.getLanguage(), responseCountry.getLanguage());

        //making sure that the methods were not called multiple times
        verify(countryRepository  , times(1)).save(country1);
    }

    @Test
    void update() {
        Long countryId = 1L;
        Country mockCountry = new Country(
                "Macedonia",
                "Skopje",
                "Balkan",
                "Denar",
                'D',
                "Macedonian"
        );
        mockCountry.setId(countryId);

        Country updatedCountry = new Country(
                "Albania",
                "Prishtina",
                "Balkan",
                "Leke",
                'L',
                "Shqip"
        );

        // mock methods
        when(countryRepository.findById(countryId)).thenReturn(Optional.of(mockCountry));
        when(countryRepository.save(mockCountry)).thenReturn(mockCountry);

        // When
        Optional<Country> result = countryService.update(countryId, updatedCountry);

        // Then
        assertEquals("Albania", result.get().getName());
        assertEquals("Prishtina", result.get().getCapital());
        assertEquals("Balkan", result.get().getRegion());
        assertEquals("Leke", result.get().getCurrencyName());
        assertEquals('L', result.get().getCurrencySymbol());
        assertEquals("Shqip", result.get().getLanguage());

        verify(countryRepository, times(1)).findById(countryId);
        verify(countryRepository, times(1)).save(mockCountry);
    }

    @Test // when country is not found
    void testUpdateCountry_NotFound() {
        Long countryId = 1L;
        Country mockCountry = new Country();
        mockCountry.setId(2L);
        when(countryRepository.findById(countryId)).thenReturn(Optional.empty());
        try {
            countryService.update(countryId, mockCountry);
        } catch (RuntimeException e) {

        }

        verify(countryRepository, times(1)).findById(countryId);
        verify(countryRepository, times(0)).save(mockCountry);
    }
}