package org.biodifull.web.rest;

import org.biodifull.BiodifullPocApp;

import org.biodifull.domain.Campagne;
import org.biodifull.repository.CampagneRepository;
import org.biodifull.service.CampagneService;
import org.biodifull.service.dto.CampagneDTO;
import org.biodifull.service.mapper.CampagneMapper;
import org.biodifull.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static org.biodifull.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CampagneResource REST controller.
 *
 * @see CampagneResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BiodifullPocApp.class)
public class CampagneResourceIntTest {

    private static final String DEFAULT_CAMPAGNE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CAMPAGNE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CAMPAGNE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_CAMPAGNE_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_FORM_URL = "AAAAAAAAAA";
    private static final String UPDATED_FORM_URL = "BBBBBBBBBB";

    private static final String DEFAULT_CHALLENGERS_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_CHALLENGERS_LOCATION = "BBBBBBBBBB";

    @Autowired
    private CampagneRepository campagneRepository;

    @Autowired
    private CampagneMapper campagneMapper;
    
    @Autowired
    private CampagneService campagneService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCampagneMockMvc;

    private Campagne campagne;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CampagneResource campagneResource = new CampagneResource(campagneService);
        this.restCampagneMockMvc = MockMvcBuilders.standaloneSetup(campagneResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Campagne createEntity(EntityManager em) {
        Campagne campagne = new Campagne()
            .campagneName(DEFAULT_CAMPAGNE_NAME)
            .campagneDescription(DEFAULT_CAMPAGNE_DESCRIPTION)
            .formURL(DEFAULT_FORM_URL)
            .challengersLocation(DEFAULT_CHALLENGERS_LOCATION);
        return campagne;
    }

    @Before
    public void initTest() {
        campagne = createEntity(em);
    }

    @Test
    @Transactional
    public void createCampagne() throws Exception {
        int databaseSizeBeforeCreate = campagneRepository.findAll().size();

        // Create the Campagne
        CampagneDTO campagneDTO = campagneMapper.toDto(campagne);
        restCampagneMockMvc.perform(post("/api/campagnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campagneDTO)))
            .andExpect(status().isCreated());

        // Validate the Campagne in the database
        List<Campagne> campagneList = campagneRepository.findAll();
        assertThat(campagneList).hasSize(databaseSizeBeforeCreate + 1);
        Campagne testCampagne = campagneList.get(campagneList.size() - 1);
        assertThat(testCampagne.getCampagneName()).isEqualTo(DEFAULT_CAMPAGNE_NAME);
        assertThat(testCampagne.getCampagneDescription()).isEqualTo(DEFAULT_CAMPAGNE_DESCRIPTION);
        assertThat(testCampagne.getFormURL()).isEqualTo(DEFAULT_FORM_URL);
        assertThat(testCampagne.getChallengersLocation()).isEqualTo(DEFAULT_CHALLENGERS_LOCATION);
    }

    @Test
    @Transactional
    public void createCampagneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = campagneRepository.findAll().size();

        // Create the Campagne with an existing ID
        campagne.setId(1L);
        CampagneDTO campagneDTO = campagneMapper.toDto(campagne);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCampagneMockMvc.perform(post("/api/campagnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campagneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Campagne in the database
        List<Campagne> campagneList = campagneRepository.findAll();
        assertThat(campagneList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCampagnes() throws Exception {
        // Initialize the database
        campagneRepository.saveAndFlush(campagne);

        // Get all the campagneList
        restCampagneMockMvc.perform(get("/api/campagnes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(campagne.getId().intValue())))
            .andExpect(jsonPath("$.[*].campagneName").value(hasItem(DEFAULT_CAMPAGNE_NAME.toString())))
            .andExpect(jsonPath("$.[*].campagneDescription").value(hasItem(DEFAULT_CAMPAGNE_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].formURL").value(hasItem(DEFAULT_FORM_URL.toString())))
            .andExpect(jsonPath("$.[*].challengersLocation").value(hasItem(DEFAULT_CHALLENGERS_LOCATION.toString())));
    }
    
    @Test
    @Transactional
    public void getCampagne() throws Exception {
        // Initialize the database
        campagneRepository.saveAndFlush(campagne);

        // Get the campagne
        restCampagneMockMvc.perform(get("/api/campagnes/{id}", campagne.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(campagne.getId().intValue()))
            .andExpect(jsonPath("$.campagneName").value(DEFAULT_CAMPAGNE_NAME.toString()))
            .andExpect(jsonPath("$.campagneDescription").value(DEFAULT_CAMPAGNE_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.formURL").value(DEFAULT_FORM_URL.toString()))
            .andExpect(jsonPath("$.challengersLocation").value(DEFAULT_CHALLENGERS_LOCATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCampagne() throws Exception {
        // Get the campagne
        restCampagneMockMvc.perform(get("/api/campagnes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCampagne() throws Exception {
        // Initialize the database
        campagneRepository.saveAndFlush(campagne);

        int databaseSizeBeforeUpdate = campagneRepository.findAll().size();

        // Update the campagne
        Campagne updatedCampagne = campagneRepository.findById(campagne.getId()).get();
        // Disconnect from session so that the updates on updatedCampagne are not directly saved in db
        em.detach(updatedCampagne);
        updatedCampagne
            .campagneName(UPDATED_CAMPAGNE_NAME)
            .campagneDescription(UPDATED_CAMPAGNE_DESCRIPTION)
            .formURL(UPDATED_FORM_URL)
            .challengersLocation(UPDATED_CHALLENGERS_LOCATION);
        CampagneDTO campagneDTO = campagneMapper.toDto(updatedCampagne);

        restCampagneMockMvc.perform(put("/api/campagnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campagneDTO)))
            .andExpect(status().isOk());

        // Validate the Campagne in the database
        List<Campagne> campagneList = campagneRepository.findAll();
        assertThat(campagneList).hasSize(databaseSizeBeforeUpdate);
        Campagne testCampagne = campagneList.get(campagneList.size() - 1);
        assertThat(testCampagne.getCampagneName()).isEqualTo(UPDATED_CAMPAGNE_NAME);
        assertThat(testCampagne.getCampagneDescription()).isEqualTo(UPDATED_CAMPAGNE_DESCRIPTION);
        assertThat(testCampagne.getFormURL()).isEqualTo(UPDATED_FORM_URL);
        assertThat(testCampagne.getChallengersLocation()).isEqualTo(UPDATED_CHALLENGERS_LOCATION);
    }

    @Test
    @Transactional
    public void updateNonExistingCampagne() throws Exception {
        int databaseSizeBeforeUpdate = campagneRepository.findAll().size();

        // Create the Campagne
        CampagneDTO campagneDTO = campagneMapper.toDto(campagne);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCampagneMockMvc.perform(put("/api/campagnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campagneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Campagne in the database
        List<Campagne> campagneList = campagneRepository.findAll();
        assertThat(campagneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCampagne() throws Exception {
        // Initialize the database
        campagneRepository.saveAndFlush(campagne);

        int databaseSizeBeforeDelete = campagneRepository.findAll().size();

        // Get the campagne
        restCampagneMockMvc.perform(delete("/api/campagnes/{id}", campagne.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Campagne> campagneList = campagneRepository.findAll();
        assertThat(campagneList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Campagne.class);
        Campagne campagne1 = new Campagne();
        campagne1.setId(1L);
        Campagne campagne2 = new Campagne();
        campagne2.setId(campagne1.getId());
        assertThat(campagne1).isEqualTo(campagne2);
        campagne2.setId(2L);
        assertThat(campagne1).isNotEqualTo(campagne2);
        campagne1.setId(null);
        assertThat(campagne1).isNotEqualTo(campagne2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CampagneDTO.class);
        CampagneDTO campagneDTO1 = new CampagneDTO();
        campagneDTO1.setId(1L);
        CampagneDTO campagneDTO2 = new CampagneDTO();
        assertThat(campagneDTO1).isNotEqualTo(campagneDTO2);
        campagneDTO2.setId(campagneDTO1.getId());
        assertThat(campagneDTO1).isEqualTo(campagneDTO2);
        campagneDTO2.setId(2L);
        assertThat(campagneDTO1).isNotEqualTo(campagneDTO2);
        campagneDTO1.setId(null);
        assertThat(campagneDTO1).isNotEqualTo(campagneDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(campagneMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(campagneMapper.fromId(null)).isNull();
    }
}
