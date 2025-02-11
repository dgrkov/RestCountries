package mk.ukim.finki.restcoutriesapp.web;

import mk.ukim.finki.restcoutriesapp.model.Country;
import mk.ukim.finki.restcoutriesapp.model.dto.CountryDTO;
import mk.ukim.finki.restcoutriesapp.model.dto.NameDTO;
import mk.ukim.finki.restcoutriesapp.model.dto.NativeNameDTO;
import mk.ukim.finki.restcoutriesapp.service.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;


class CountriesControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private CountriesController countriesController;

    @Mock
    private CountryService countryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(countriesController).build();
    }

    @Test
    void findAll() throws Exception {
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

        List<CountryDTO> countries = Arrays.asList(country1, country2, country3);

        Mockito.when(countryService.getAllCountries()).thenReturn(countries);

        mockMvc.perform(get("/countries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$[0].name.common").value("United Kingdom"))
                .andExpect(jsonPath("$[1].name.common").value("Germany"))
                .andExpect(jsonPath("$[2].name.common").value("Bolivia"))
                .andDo(print());
    }

    @Test
    void getCountriesByRegion() throws Exception {
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
        country1.setRegion("Europe");
        country2.setRegion("Europe");

        List<CountryDTO> countries = Arrays.asList(country1, country2);
        when(countryService.getCountriesByRegion("Europe")).thenReturn(countries);

        mockMvc.perform(get("/countries/Europe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name.common").value("United Kingdom"))
                .andExpect(jsonPath("$[1].name.common").value("Germany"))
                .andDo(print());

    }

    @Test
    void countByRegion() throws Exception {
        Map<String, Long> countedByRegion = new HashMap<>();
        countedByRegion.put("Europe", 4L);
        countedByRegion.put("Asia", 2L);
        countedByRegion.put("Africa", 6L);
        when(countryService.countCountriesByRegion()).thenReturn(countedByRegion);

        mockMvc.perform(get("/countries/region/count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Europe").value(4L))
                .andDo(print());
    }

    @Test
    void getFavouriteCountries() throws Exception {
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

        List<CountryDTO> favs = Arrays.asList(country1, country2, country3);
        when(countryService.getAllCountries()).thenReturn(favs);
        when(countryService.getFavouriteCountries(favs)).thenReturn(favs);

        mockMvc.perform(get("/countries/favourites"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$[0].name.common").value("United Kingdom"))
                .andExpect(jsonPath("$[1].name.common").value("Germany"))
                .andExpect(jsonPath("$[2].name.common").value("Bolivia"))
                .andDo(print());
    }

    @Test
    void save() throws Exception {
        Country country = new Country("France", "Paris", "Europe", "Euro", '€', "French");
        when(countryService.save(country)).thenReturn(country);

        mockMvc.perform(post("/countries/favourites")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"France\",\"capital\":\"Paris\",\"region\":\"Europe\", \"currencyName\": \"Euro\", \"currencySymbol\": \"€\", \"language\": \"French\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("France"))
                .andDo(print());

        verify(countryService, times(1)).save(country);
    }

    @Test
    void update() throws Exception {
        Country country = new Country("France", "Paris", "Europe", "Euro", '€', "French");
        country.setId(1L);
        when(countryService.update(eq(1L), any(Country.class))).thenReturn(Optional.of(country));

        mockMvc.perform(put("/countries/favourites/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"France\",\"capital\":\"Paris\",\"region\":\"Europe\",\"currencyName\": \"Euro\", \"currencySymbol\": \"€\", \"language\":\"French\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("France"))
                .andDo(print());

        verify(countryService, times(1)).update(eq(1L), any(Country.class));
    }

    @Test
    void delete() throws Exception {
        Country country = new Country("France", "Paris", "Europe", "Euro", '€', "French");
        Long countryId = 1L;
        country.setId(countryId);
        when(countryService.findCountryById(countryId)).thenReturn(Optional.of(country));

        ResponseEntity<Country> responseEntity = countriesController.delete(countryId);

        verify(countryService, times(1)).deleteCountryById(countryId);
        assertEquals(ResponseEntity.ok().build(), responseEntity);
    }

    @Test
    void shouldNotDelete() throws Exception {
        Long countryId = 1L;

        when(countryService.findCountryById(countryId)).thenReturn(Optional.empty());
        ResponseEntity<Country> responseEntity = countriesController.delete(countryId);
        verify(countryService, never()).deleteCountryById(countryId);
        assertEquals(ResponseEntity.notFound().build(), responseEntity);
    }
}