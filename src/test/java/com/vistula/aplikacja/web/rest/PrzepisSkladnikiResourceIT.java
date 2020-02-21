package com.vistula.aplikacja.web.rest;

import com.vistula.aplikacja.AplikacjaDietetycznaApp;
import com.vistula.aplikacja.domain.PrzepisSkladniki;
import com.vistula.aplikacja.repository.PrzepisSkladnikiRepository;
import com.vistula.aplikacja.service.PrzepisSkladnikiService;
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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.vistula.aplikacja.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PrzepisSkladnikiResource} REST controller.
 */
@SpringBootTest(classes = AplikacjaDietetycznaApp.class)
public class PrzepisSkladnikiResourceIT {

    private static final Double DEFAULT_ILOSC = 1D;
    private static final Double UPDATED_ILOSC = 2D;

    private static final Double DEFAULT_KALORIE_ILOSC = 1D;
    private static final Double UPDATED_KALORIE_ILOSC = 2D;

    private static final Long DEFAULT_PRZEPIS_ID = 1L;
    private static final Long UPDATED_PRZEPIS_ID = 2L;

    @Autowired
    private PrzepisSkladnikiRepository przepisSkladnikiRepository;

    @Autowired
    private PrzepisSkladnikiService przepisSkladnikiService;

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

    private MockMvc restPrzepisSkladnikiMockMvc;

    private PrzepisSkladniki przepisSkladniki;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrzepisSkladnikiResource przepisSkladnikiResource = new PrzepisSkladnikiResource(przepisSkladnikiService);
        this.restPrzepisSkladnikiMockMvc = MockMvcBuilders.standaloneSetup(przepisSkladnikiResource)
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
    public static PrzepisSkladniki createEntity(EntityManager em) {
        PrzepisSkladniki przepisSkladniki = new PrzepisSkladniki()
            .ilosc(DEFAULT_ILOSC)
            .kalorieIlosc(DEFAULT_KALORIE_ILOSC)
            .przepisId(DEFAULT_PRZEPIS_ID);
        return przepisSkladniki;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrzepisSkladniki createUpdatedEntity(EntityManager em) {
        PrzepisSkladniki przepisSkladniki = new PrzepisSkladniki()
            .ilosc(UPDATED_ILOSC)
            .kalorieIlosc(UPDATED_KALORIE_ILOSC)
            .przepisId(UPDATED_PRZEPIS_ID);
        return przepisSkladniki;
    }

    @BeforeEach
    public void initTest() {
        przepisSkladniki = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrzepisSkladniki() throws Exception {
        int databaseSizeBeforeCreate = przepisSkladnikiRepository.findAll().size();

        // Create the PrzepisSkladniki
        restPrzepisSkladnikiMockMvc.perform(post("/api/przepis-skladnikis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(przepisSkladniki)))
            .andExpect(status().isCreated());

        // Validate the PrzepisSkladniki in the database
        List<PrzepisSkladniki> przepisSkladnikiList = przepisSkladnikiRepository.findAll();
        assertThat(przepisSkladnikiList).hasSize(databaseSizeBeforeCreate + 1);
        PrzepisSkladniki testPrzepisSkladniki = przepisSkladnikiList.get(przepisSkladnikiList.size() - 1);
        assertThat(testPrzepisSkladniki.getIlosc()).isEqualTo(DEFAULT_ILOSC);
        assertThat(testPrzepisSkladniki.getKalorieIlosc()).isEqualTo(DEFAULT_KALORIE_ILOSC);
        assertThat(testPrzepisSkladniki.getPrzepisId()).isEqualTo(DEFAULT_PRZEPIS_ID);
    }

    @Test
    @Transactional
    public void createPrzepisSkladnikiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = przepisSkladnikiRepository.findAll().size();

        // Create the PrzepisSkladniki with an existing ID
        przepisSkladniki.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrzepisSkladnikiMockMvc.perform(post("/api/przepis-skladnikis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(przepisSkladniki)))
            .andExpect(status().isBadRequest());

        // Validate the PrzepisSkladniki in the database
        List<PrzepisSkladniki> przepisSkladnikiList = przepisSkladnikiRepository.findAll();
        assertThat(przepisSkladnikiList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPrzepisSkladnikis() throws Exception {
        // Initialize the database
        przepisSkladnikiRepository.saveAndFlush(przepisSkladniki);

        // Get all the przepisSkladnikiList
        restPrzepisSkladnikiMockMvc.perform(get("/api/przepis-skladnikis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(przepisSkladniki.getId().intValue())))
            .andExpect(jsonPath("$.[*].ilosc").value(hasItem(DEFAULT_ILOSC.doubleValue())))
            .andExpect(jsonPath("$.[*].kalorieIlosc").value(hasItem(DEFAULT_KALORIE_ILOSC.doubleValue())))
            .andExpect(jsonPath("$.[*].przepisId").value(hasItem(DEFAULT_PRZEPIS_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getPrzepisSkladniki() throws Exception {
        // Initialize the database
        przepisSkladnikiRepository.saveAndFlush(przepisSkladniki);

        // Get the przepisSkladniki
        restPrzepisSkladnikiMockMvc.perform(get("/api/przepis-skladnikis/{id}", przepisSkladniki.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(przepisSkladniki.getId().intValue()))
            .andExpect(jsonPath("$.ilosc").value(DEFAULT_ILOSC.doubleValue()))
            .andExpect(jsonPath("$.kalorieIlosc").value(DEFAULT_KALORIE_ILOSC.doubleValue()))
            .andExpect(jsonPath("$.przepisId").value(DEFAULT_PRZEPIS_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPrzepisSkladniki() throws Exception {
        // Get the przepisSkladniki
        restPrzepisSkladnikiMockMvc.perform(get("/api/przepis-skladnikis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrzepisSkladniki() throws Exception {
        // Initialize the database
        przepisSkladnikiService.save(przepisSkladniki);

        int databaseSizeBeforeUpdate = przepisSkladnikiRepository.findAll().size();

        // Update the przepisSkladniki
        PrzepisSkladniki updatedPrzepisSkladniki = przepisSkladnikiRepository.findById(przepisSkladniki.getId()).get();
        // Disconnect from session so that the updates on updatedPrzepisSkladniki are not directly saved in db
        em.detach(updatedPrzepisSkladniki);
        updatedPrzepisSkladniki
            .ilosc(UPDATED_ILOSC)
            .kalorieIlosc(UPDATED_KALORIE_ILOSC)
            .przepisId(UPDATED_PRZEPIS_ID);

        restPrzepisSkladnikiMockMvc.perform(put("/api/przepis-skladnikis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPrzepisSkladniki)))
            .andExpect(status().isOk());

        // Validate the PrzepisSkladniki in the database
        List<PrzepisSkladniki> przepisSkladnikiList = przepisSkladnikiRepository.findAll();
        assertThat(przepisSkladnikiList).hasSize(databaseSizeBeforeUpdate);
        PrzepisSkladniki testPrzepisSkladniki = przepisSkladnikiList.get(przepisSkladnikiList.size() - 1);
        assertThat(testPrzepisSkladniki.getIlosc()).isEqualTo(UPDATED_ILOSC);
        assertThat(testPrzepisSkladniki.getKalorieIlosc()).isEqualTo(UPDATED_KALORIE_ILOSC);
        assertThat(testPrzepisSkladniki.getPrzepisId()).isEqualTo(UPDATED_PRZEPIS_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingPrzepisSkladniki() throws Exception {
        int databaseSizeBeforeUpdate = przepisSkladnikiRepository.findAll().size();

        // Create the PrzepisSkladniki

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrzepisSkladnikiMockMvc.perform(put("/api/przepis-skladnikis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(przepisSkladniki)))
            .andExpect(status().isBadRequest());

        // Validate the PrzepisSkladniki in the database
        List<PrzepisSkladniki> przepisSkladnikiList = przepisSkladnikiRepository.findAll();
        assertThat(przepisSkladnikiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePrzepisSkladniki() throws Exception {
        // Initialize the database
        przepisSkladnikiService.save(przepisSkladniki);

        int databaseSizeBeforeDelete = przepisSkladnikiRepository.findAll().size();

        // Delete the przepisSkladniki
        restPrzepisSkladnikiMockMvc.perform(delete("/api/przepis-skladnikis/{id}", przepisSkladniki.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PrzepisSkladniki> przepisSkladnikiList = przepisSkladnikiRepository.findAll();
        assertThat(przepisSkladnikiList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
