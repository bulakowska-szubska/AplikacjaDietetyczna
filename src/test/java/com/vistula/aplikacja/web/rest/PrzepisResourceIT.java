package com.vistula.aplikacja.web.rest;

import com.vistula.aplikacja.AplikacjaDietetycznaApp;
import com.vistula.aplikacja.domain.Przepis;
import com.vistula.aplikacja.repository.PrzepisRepository;
import com.vistula.aplikacja.service.PrzepisService;
import com.vistula.aplikacja.service.PrzepisSkladnikiService;
import com.vistula.aplikacja.service.UserService;
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

import com.vistula.aplikacja.domain.enumeration.TypPrzepisuEnum;
/**
 * Integration tests for the {@link PrzepisResource} REST controller.
 */
@SpringBootTest(classes = AplikacjaDietetycznaApp.class)
public class PrzepisResourceIT {

    private static final String DEFAULT_NAZWA = "AAAAAAAAAA";
    private static final String UPDATED_NAZWA = "BBBBBBBBBB";

    private static final TypPrzepisuEnum DEFAULT_TYP_PRZEPISU = TypPrzepisuEnum.WEGETARIANSKI;
    private static final TypPrzepisuEnum UPDATED_TYP_PRZEPISU = TypPrzepisuEnum.WEGANSKI;

    private static final byte[] DEFAULT_ZDJECIE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ZDJECIE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ZDJECIE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ZDJECIE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_OPIS = "AAAAAAAAAA";
    private static final String UPDATED_OPIS = "BBBBBBBBBB";

    private static final Double DEFAULT_KALORIE_SUMA = 1D;
    private static final Double UPDATED_KALORIE_SUMA = 2D;

    @Autowired
    private PrzepisRepository przepisRepository;

    @Autowired
    private PrzepisService przepisService;

    @Autowired
    private UserService userService;

    @Autowired
    private PrzepisSkladnikiService przepisSkladnikiService;

    @Autowired
    private PrzepisSkladnikiResource przepisSkladnikiResource;

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

    private MockMvc restPrzepisMockMvc;

    private Przepis przepis;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrzepisResource przepisResource = new PrzepisResource(userService, przepisService, przepisSkladnikiService, przepisSkladnikiResource);
        this.restPrzepisMockMvc = MockMvcBuilders.standaloneSetup(przepisResource)
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
    public static Przepis createEntity(EntityManager em) {
        Przepis przepis = new Przepis()
            .nazwa(DEFAULT_NAZWA)
            .typPrzepisu(DEFAULT_TYP_PRZEPISU)
            .zdjecie(DEFAULT_ZDJECIE)
            .zdjecieContentType(DEFAULT_ZDJECIE_CONTENT_TYPE)
            .opis(DEFAULT_OPIS)
            .kalorieSuma(DEFAULT_KALORIE_SUMA);
        return przepis;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Przepis createUpdatedEntity(EntityManager em) {
        Przepis przepis = new Przepis()
            .nazwa(UPDATED_NAZWA)
            .typPrzepisu(UPDATED_TYP_PRZEPISU)
            .zdjecie(UPDATED_ZDJECIE)
            .zdjecieContentType(UPDATED_ZDJECIE_CONTENT_TYPE)
            .opis(UPDATED_OPIS)
            .kalorieSuma(UPDATED_KALORIE_SUMA);
        return przepis;
    }

    @BeforeEach
    public void initTest() {
        przepis = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrzepis() throws Exception {
        int databaseSizeBeforeCreate = przepisRepository.findAll().size();

        // Create the Przepis
        restPrzepisMockMvc.perform(post("/api/przepis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(przepis)))
            .andExpect(status().isCreated());

        // Validate the Przepis in the database
        List<Przepis> przepisList = przepisRepository.findAll();
        assertThat(przepisList).hasSize(databaseSizeBeforeCreate + 1);
        Przepis testPrzepis = przepisList.get(przepisList.size() - 1);
        assertThat(testPrzepis.getNazwa()).isEqualTo(DEFAULT_NAZWA);
        assertThat(testPrzepis.getTypPrzepisu()).isEqualTo(DEFAULT_TYP_PRZEPISU);
        assertThat(testPrzepis.getZdjecie()).isEqualTo(DEFAULT_ZDJECIE);
        assertThat(testPrzepis.getZdjecieContentType()).isEqualTo(DEFAULT_ZDJECIE_CONTENT_TYPE);
        assertThat(testPrzepis.getOpis()).isEqualTo(DEFAULT_OPIS);
        assertThat(testPrzepis.getKalorieSuma()).isEqualTo(DEFAULT_KALORIE_SUMA);
    }

    @Test
    @Transactional
    public void createPrzepisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = przepisRepository.findAll().size();

        // Create the Przepis with an existing ID
        przepis.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrzepisMockMvc.perform(post("/api/przepis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(przepis)))
            .andExpect(status().isBadRequest());

        // Validate the Przepis in the database
        List<Przepis> przepisList = przepisRepository.findAll();
        assertThat(przepisList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPrzepis() throws Exception {
        // Initialize the database
        przepisRepository.saveAndFlush(przepis);

        // Get all the przepisList
        restPrzepisMockMvc.perform(get("/api/przepis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(przepis.getId().intValue())))
            .andExpect(jsonPath("$.[*].nazwa").value(hasItem(DEFAULT_NAZWA)))
            .andExpect(jsonPath("$.[*].typPrzepisu").value(hasItem(DEFAULT_TYP_PRZEPISU.toString())))
            .andExpect(jsonPath("$.[*].zdjecieContentType").value(hasItem(DEFAULT_ZDJECIE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].zdjecie").value(hasItem(Base64Utils.encodeToString(DEFAULT_ZDJECIE))))
            .andExpect(jsonPath("$.[*].opis").value(hasItem(DEFAULT_OPIS)))
            .andExpect(jsonPath("$.[*].kalorieSuma").value(hasItem(DEFAULT_KALORIE_SUMA.doubleValue())));
    }

    @Test
    @Transactional
    public void getPrzepis() throws Exception {
        // Initialize the database
        przepisRepository.saveAndFlush(przepis);

        // Get the przepis
        restPrzepisMockMvc.perform(get("/api/przepis/{id}", przepis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(przepis.getId().intValue()))
            .andExpect(jsonPath("$.nazwa").value(DEFAULT_NAZWA))
            .andExpect(jsonPath("$.typPrzepisu").value(DEFAULT_TYP_PRZEPISU.toString()))
            .andExpect(jsonPath("$.zdjecieContentType").value(DEFAULT_ZDJECIE_CONTENT_TYPE))
            .andExpect(jsonPath("$.zdjecie").value(Base64Utils.encodeToString(DEFAULT_ZDJECIE)))
            .andExpect(jsonPath("$.opis").value(DEFAULT_OPIS))
            .andExpect(jsonPath("$.kalorieSuma").value(DEFAULT_KALORIE_SUMA.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPrzepis() throws Exception {
        // Get the przepis
        restPrzepisMockMvc.perform(get("/api/przepis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrzepis() throws Exception {
        // Initialize the database
        przepisService.save(przepis);

        int databaseSizeBeforeUpdate = przepisRepository.findAll().size();

        // Update the przepis
        Przepis updatedPrzepis = przepisRepository.findById(przepis.getId()).get();
        // Disconnect from session so that the updates on updatedPrzepis are not directly saved in db
        em.detach(updatedPrzepis);
        updatedPrzepis
            .nazwa(UPDATED_NAZWA)
            .typPrzepisu(UPDATED_TYP_PRZEPISU)
            .zdjecie(UPDATED_ZDJECIE)
            .zdjecieContentType(UPDATED_ZDJECIE_CONTENT_TYPE)
            .opis(UPDATED_OPIS)
            .kalorieSuma(UPDATED_KALORIE_SUMA);

        restPrzepisMockMvc.perform(put("/api/przepis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPrzepis)))
            .andExpect(status().isOk());

        // Validate the Przepis in the database
        List<Przepis> przepisList = przepisRepository.findAll();
        assertThat(przepisList).hasSize(databaseSizeBeforeUpdate);
        Przepis testPrzepis = przepisList.get(przepisList.size() - 1);
        assertThat(testPrzepis.getNazwa()).isEqualTo(UPDATED_NAZWA);
        assertThat(testPrzepis.getTypPrzepisu()).isEqualTo(UPDATED_TYP_PRZEPISU);
        assertThat(testPrzepis.getZdjecie()).isEqualTo(UPDATED_ZDJECIE);
        assertThat(testPrzepis.getZdjecieContentType()).isEqualTo(UPDATED_ZDJECIE_CONTENT_TYPE);
        assertThat(testPrzepis.getOpis()).isEqualTo(UPDATED_OPIS);
        assertThat(testPrzepis.getKalorieSuma()).isEqualTo(UPDATED_KALORIE_SUMA);
    }

    @Test
    @Transactional
    public void updateNonExistingPrzepis() throws Exception {
        int databaseSizeBeforeUpdate = przepisRepository.findAll().size();

        // Create the Przepis

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrzepisMockMvc.perform(put("/api/przepis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(przepis)))
            .andExpect(status().isBadRequest());

        // Validate the Przepis in the database
        List<Przepis> przepisList = przepisRepository.findAll();
        assertThat(przepisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePrzepis() throws Exception {
        // Initialize the database
        przepisService.save(przepis);

        int databaseSizeBeforeDelete = przepisRepository.findAll().size();

        // Delete the przepis
        restPrzepisMockMvc.perform(delete("/api/przepis/{id}", przepis.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Przepis> przepisList = przepisRepository.findAll();
        assertThat(przepisList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
