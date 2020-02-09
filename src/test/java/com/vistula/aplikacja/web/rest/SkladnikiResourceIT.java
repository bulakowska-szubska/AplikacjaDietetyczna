package com.vistula.aplikacja.web.rest;

import com.vistula.aplikacja.AplikacjaDietetycznaApp;
import com.vistula.aplikacja.domain.Skladniki;
import com.vistula.aplikacja.repository.SkladnikiRepository;
import com.vistula.aplikacja.service.SkladnikiService;
import com.vistula.aplikacja.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.vistula.aplikacja.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.vistula.aplikacja.domain.enumeration.SkladnikiJednostkaEnum;
import com.vistula.aplikacja.domain.enumeration.SkladnikiKategoriaEnum;
/**
 * Integration tests for the {@link SkladnikiResource} REST controller.
 */
@SpringBootTest(classes = AplikacjaDietetycznaApp.class)
public class SkladnikiResourceIT {

    private static final String DEFAULT_NAZWA = "AAAAAAAAAA";
    private static final String UPDATED_NAZWA = "BBBBBBBBBB";

    private static final byte[] DEFAULT_ZDJECIE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ZDJECIE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ZDJECIE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ZDJECIE_CONTENT_TYPE = "image/png";

    private static final SkladnikiJednostkaEnum DEFAULT_JEDNOSTKA = SkladnikiJednostkaEnum.GRAMY;
    private static final SkladnikiJednostkaEnum UPDATED_JEDNOSTKA = SkladnikiJednostkaEnum.MILILITRY;

    private static final SkladnikiKategoriaEnum DEFAULT_KATEGORIA = SkladnikiKategoriaEnum.OWOCE;
    private static final SkladnikiKategoriaEnum UPDATED_KATEGORIA = SkladnikiKategoriaEnum.WARZYWA;

    private static final Double DEFAULT_KALORIE_STO = 1D;
    private static final Double UPDATED_KALORIE_STO = 2D;

    private static final Double DEFAULT_KALORIE_JEDNOSTKA = 1D;
    private static final Double UPDATED_KALORIE_JEDNOSTKA = 2D;

    @Autowired
    private SkladnikiRepository skladnikiRepository;

    @Autowired
    private SkladnikiService skladnikiService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restSkladnikiMockMvc;

    private Skladniki skladniki;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SkladnikiResource skladnikiResource = new SkladnikiResource(skladnikiService);
        this.restSkladnikiMockMvc = MockMvcBuilders.standaloneSetup(skladnikiResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Skladniki createEntity(EntityManager em) {
        Skladniki skladniki = new Skladniki()
            .nazwa(DEFAULT_NAZWA)
            .zdjecie(DEFAULT_ZDJECIE)
            .zdjecieContentType(DEFAULT_ZDJECIE_CONTENT_TYPE)
            .jednostka(DEFAULT_JEDNOSTKA)
            .kategoria(DEFAULT_KATEGORIA)
            .kalorieSto(DEFAULT_KALORIE_STO)
            .kalorieJednostka(DEFAULT_KALORIE_JEDNOSTKA);
        return skladniki;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Skladniki createUpdatedEntity(EntityManager em) {
        Skladniki skladniki = new Skladniki()
            .nazwa(UPDATED_NAZWA)
            .zdjecie(UPDATED_ZDJECIE)
            .zdjecieContentType(UPDATED_ZDJECIE_CONTENT_TYPE)
            .jednostka(UPDATED_JEDNOSTKA)
            .kategoria(UPDATED_KATEGORIA)
            .kalorieSto(UPDATED_KALORIE_STO)
            .kalorieJednostka(UPDATED_KALORIE_JEDNOSTKA);
        return skladniki;
    }

    @BeforeEach
    public void initTest() {
        skladniki = createEntity(em);
    }

    @Test
    @Transactional
    public void createSkladniki() throws Exception {
        int databaseSizeBeforeCreate = skladnikiRepository.findAll().size();

        // Create the Skladniki
        restSkladnikiMockMvc.perform(post("/api/skladnikis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skladniki)))
            .andExpect(status().isCreated());

        // Validate the Skladniki in the database
        List<Skladniki> skladnikiList = skladnikiRepository.findAll();
        assertThat(skladnikiList).hasSize(databaseSizeBeforeCreate + 1);
        Skladniki testSkladniki = skladnikiList.get(skladnikiList.size() - 1);
        assertThat(testSkladniki.getNazwa()).isEqualTo(DEFAULT_NAZWA);
        assertThat(testSkladniki.getZdjecie()).isEqualTo(DEFAULT_ZDJECIE);
        assertThat(testSkladniki.getZdjecieContentType()).isEqualTo(DEFAULT_ZDJECIE_CONTENT_TYPE);
        assertThat(testSkladniki.getJednostka()).isEqualTo(DEFAULT_JEDNOSTKA);
        assertThat(testSkladniki.getKategoria()).isEqualTo(DEFAULT_KATEGORIA);
        assertThat(testSkladniki.getKalorieSto()).isEqualTo(DEFAULT_KALORIE_STO);
        assertThat(testSkladniki.getKalorieJednostka()).isEqualTo(DEFAULT_KALORIE_JEDNOSTKA);
    }

    @Test
    @Transactional
    public void createSkladnikiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = skladnikiRepository.findAll().size();

        // Create the Skladniki with an existing ID
        skladniki.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSkladnikiMockMvc.perform(post("/api/skladnikis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skladniki)))
            .andExpect(status().isBadRequest());

        // Validate the Skladniki in the database
        List<Skladniki> skladnikiList = skladnikiRepository.findAll();
        assertThat(skladnikiList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSkladnikis() throws Exception {
        // Initialize the database
        skladnikiRepository.saveAndFlush(skladniki);

        // Get all the skladnikiList
        restSkladnikiMockMvc.perform(get("/api/skladnikis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skladniki.getId().intValue())))
            .andExpect(jsonPath("$.[*].nazwa").value(hasItem(DEFAULT_NAZWA)))
            .andExpect(jsonPath("$.[*].zdjecieContentType").value(hasItem(DEFAULT_ZDJECIE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].zdjecie").value(hasItem(Base64Utils.encodeToString(DEFAULT_ZDJECIE))))
            .andExpect(jsonPath("$.[*].jednostka").value(hasItem(DEFAULT_JEDNOSTKA.toString())))
            .andExpect(jsonPath("$.[*].kategoria").value(hasItem(DEFAULT_KATEGORIA.toString())))
            .andExpect(jsonPath("$.[*].kalorieSto").value(hasItem(DEFAULT_KALORIE_STO.doubleValue())))
            .andExpect(jsonPath("$.[*].kalorieJednostka").value(hasItem(DEFAULT_KALORIE_JEDNOSTKA.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getSkladniki() throws Exception {
        // Initialize the database
        skladnikiRepository.saveAndFlush(skladniki);

        // Get the skladniki
        restSkladnikiMockMvc.perform(get("/api/skladnikis/{id}", skladniki.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(skladniki.getId().intValue()))
            .andExpect(jsonPath("$.nazwa").value(DEFAULT_NAZWA))
            .andExpect(jsonPath("$.zdjecieContentType").value(DEFAULT_ZDJECIE_CONTENT_TYPE))
            .andExpect(jsonPath("$.zdjecie").value(Base64Utils.encodeToString(DEFAULT_ZDJECIE)))
            .andExpect(jsonPath("$.jednostka").value(DEFAULT_JEDNOSTKA.toString()))
            .andExpect(jsonPath("$.kategoria").value(DEFAULT_KATEGORIA.toString()))
            .andExpect(jsonPath("$.kalorieSto").value(DEFAULT_KALORIE_STO.doubleValue()))
            .andExpect(jsonPath("$.kalorieJednostka").value(DEFAULT_KALORIE_JEDNOSTKA.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSkladniki() throws Exception {
        // Get the skladniki
        restSkladnikiMockMvc.perform(get("/api/skladnikis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSkladniki() throws Exception {
        // Initialize the database
        skladnikiService.save(skladniki);

        int databaseSizeBeforeUpdate = skladnikiRepository.findAll().size();

        // Update the skladniki
        Skladniki updatedSkladniki = skladnikiRepository.findById(skladniki.getId()).get();
        // Disconnect from session so that the updates on updatedSkladniki are not directly saved in db
        em.detach(updatedSkladniki);
        updatedSkladniki
            .nazwa(UPDATED_NAZWA)
            .zdjecie(UPDATED_ZDJECIE)
            .zdjecieContentType(UPDATED_ZDJECIE_CONTENT_TYPE)
            .jednostka(UPDATED_JEDNOSTKA)
            .kategoria(UPDATED_KATEGORIA)
            .kalorieSto(UPDATED_KALORIE_STO)
            .kalorieJednostka(UPDATED_KALORIE_JEDNOSTKA);

        restSkladnikiMockMvc.perform(put("/api/skladnikis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSkladniki)))
            .andExpect(status().isOk());

        // Validate the Skladniki in the database
        List<Skladniki> skladnikiList = skladnikiRepository.findAll();
        assertThat(skladnikiList).hasSize(databaseSizeBeforeUpdate);
        Skladniki testSkladniki = skladnikiList.get(skladnikiList.size() - 1);
        assertThat(testSkladniki.getNazwa()).isEqualTo(UPDATED_NAZWA);
        assertThat(testSkladniki.getZdjecie()).isEqualTo(UPDATED_ZDJECIE);
        assertThat(testSkladniki.getZdjecieContentType()).isEqualTo(UPDATED_ZDJECIE_CONTENT_TYPE);
        assertThat(testSkladniki.getJednostka()).isEqualTo(UPDATED_JEDNOSTKA);
        assertThat(testSkladniki.getKategoria()).isEqualTo(UPDATED_KATEGORIA);
        assertThat(testSkladniki.getKalorieSto()).isEqualTo(UPDATED_KALORIE_STO);
        assertThat(testSkladniki.getKalorieJednostka()).isEqualTo(UPDATED_KALORIE_JEDNOSTKA);
    }

    @Test
    @Transactional
    public void updateNonExistingSkladniki() throws Exception {
        int databaseSizeBeforeUpdate = skladnikiRepository.findAll().size();

        // Create the Skladniki

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSkladnikiMockMvc.perform(put("/api/skladnikis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skladniki)))
            .andExpect(status().isBadRequest());

        // Validate the Skladniki in the database
        List<Skladniki> skladnikiList = skladnikiRepository.findAll();
        assertThat(skladnikiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSkladniki() throws Exception {
        // Initialize the database
        skladnikiService.save(skladniki);

        int databaseSizeBeforeDelete = skladnikiRepository.findAll().size();

        // Delete the skladniki
        restSkladnikiMockMvc.perform(delete("/api/skladnikis/{id}", skladniki.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Skladniki> skladnikiList = skladnikiRepository.findAll();
        assertThat(skladnikiList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
