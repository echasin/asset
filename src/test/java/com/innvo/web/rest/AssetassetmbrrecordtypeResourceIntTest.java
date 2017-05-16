package com.innvo.web.rest;

import com.innvo.AssetApp;

import com.innvo.domain.Assetassetmbrrecordtype;
import com.innvo.repository.AssetassetmbrrecordtypeRepository;
import com.innvo.repository.search.AssetassetmbrrecordtypeSearchRepository;
import com.innvo.web.rest.errors.ExceptionTranslator;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.innvo.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AssetassetmbrrecordtypeResource REST controller.
 *
 * @see AssetassetmbrrecordtypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AssetApp.class)
public class AssetassetmbrrecordtypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NAMESHORT = "AAAAAAAAAA";
    private static final String UPDATED_NAMESHORT = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_LASTMODIFIEDBY = "AAAAAAAAAA";
    private static final String UPDATED_LASTMODIFIEDBY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LASTMODIFIEDDATETIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LASTMODIFIEDDATETIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_DOMAIN = "AAAAAAAAAA";
    private static final String UPDATED_DOMAIN = "BBBBBBBBBB";

    @Autowired
    private AssetassetmbrrecordtypeRepository assetassetmbrrecordtypeRepository;

    @Autowired
    private AssetassetmbrrecordtypeSearchRepository assetassetmbrrecordtypeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAssetassetmbrrecordtypeMockMvc;

    private Assetassetmbrrecordtype assetassetmbrrecordtype;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AssetassetmbrrecordtypeResource assetassetmbrrecordtypeResource = new AssetassetmbrrecordtypeResource(assetassetmbrrecordtypeRepository, assetassetmbrrecordtypeSearchRepository);
        this.restAssetassetmbrrecordtypeMockMvc = MockMvcBuilders.standaloneSetup(assetassetmbrrecordtypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Assetassetmbrrecordtype createEntity(EntityManager em) {
        Assetassetmbrrecordtype assetassetmbrrecordtype = new Assetassetmbrrecordtype()
            .name(DEFAULT_NAME)
            .nameshort(DEFAULT_NAMESHORT)
            .description(DEFAULT_DESCRIPTION)
            .status(DEFAULT_STATUS)
            .lastmodifiedby(DEFAULT_LASTMODIFIEDBY)
            .lastmodifieddatetime(DEFAULT_LASTMODIFIEDDATETIME)
            .domain(DEFAULT_DOMAIN);
        return assetassetmbrrecordtype;
    }

    @Before
    public void initTest() {
        assetassetmbrrecordtypeSearchRepository.deleteAll();
        assetassetmbrrecordtype = createEntity(em);
    }

    @Test
    @Transactional
    public void createAssetassetmbrrecordtype() throws Exception {
        int databaseSizeBeforeCreate = assetassetmbrrecordtypeRepository.findAll().size();

        // Create the Assetassetmbrrecordtype
        restAssetassetmbrrecordtypeMockMvc.perform(post("/api/assetassetmbrrecordtypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetassetmbrrecordtype)))
            .andExpect(status().isCreated());

        // Validate the Assetassetmbrrecordtype in the database
        List<Assetassetmbrrecordtype> assetassetmbrrecordtypeList = assetassetmbrrecordtypeRepository.findAll();
        assertThat(assetassetmbrrecordtypeList).hasSize(databaseSizeBeforeCreate + 1);
        Assetassetmbrrecordtype testAssetassetmbrrecordtype = assetassetmbrrecordtypeList.get(assetassetmbrrecordtypeList.size() - 1);
        assertThat(testAssetassetmbrrecordtype.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAssetassetmbrrecordtype.getNameshort()).isEqualTo(DEFAULT_NAMESHORT);
        assertThat(testAssetassetmbrrecordtype.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAssetassetmbrrecordtype.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAssetassetmbrrecordtype.getLastmodifiedby()).isEqualTo(DEFAULT_LASTMODIFIEDBY);
        assertThat(testAssetassetmbrrecordtype.getLastmodifieddatetime()).isEqualTo(DEFAULT_LASTMODIFIEDDATETIME);
        assertThat(testAssetassetmbrrecordtype.getDomain()).isEqualTo(DEFAULT_DOMAIN);

        // Validate the Assetassetmbrrecordtype in Elasticsearch
        Assetassetmbrrecordtype assetassetmbrrecordtypeEs = assetassetmbrrecordtypeSearchRepository.findOne(testAssetassetmbrrecordtype.getId());
        assertThat(assetassetmbrrecordtypeEs).isEqualToComparingFieldByField(testAssetassetmbrrecordtype);
    }

    @Test
    @Transactional
    public void createAssetassetmbrrecordtypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = assetassetmbrrecordtypeRepository.findAll().size();

        // Create the Assetassetmbrrecordtype with an existing ID
        assetassetmbrrecordtype.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssetassetmbrrecordtypeMockMvc.perform(post("/api/assetassetmbrrecordtypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetassetmbrrecordtype)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Assetassetmbrrecordtype> assetassetmbrrecordtypeList = assetassetmbrrecordtypeRepository.findAll();
        assertThat(assetassetmbrrecordtypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetassetmbrrecordtypeRepository.findAll().size();
        // set the field null
        assetassetmbrrecordtype.setName(null);

        // Create the Assetassetmbrrecordtype, which fails.

        restAssetassetmbrrecordtypeMockMvc.perform(post("/api/assetassetmbrrecordtypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetassetmbrrecordtype)))
            .andExpect(status().isBadRequest());

        List<Assetassetmbrrecordtype> assetassetmbrrecordtypeList = assetassetmbrrecordtypeRepository.findAll();
        assertThat(assetassetmbrrecordtypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameshortIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetassetmbrrecordtypeRepository.findAll().size();
        // set the field null
        assetassetmbrrecordtype.setNameshort(null);

        // Create the Assetassetmbrrecordtype, which fails.

        restAssetassetmbrrecordtypeMockMvc.perform(post("/api/assetassetmbrrecordtypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetassetmbrrecordtype)))
            .andExpect(status().isBadRequest());

        List<Assetassetmbrrecordtype> assetassetmbrrecordtypeList = assetassetmbrrecordtypeRepository.findAll();
        assertThat(assetassetmbrrecordtypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetassetmbrrecordtypeRepository.findAll().size();
        // set the field null
        assetassetmbrrecordtype.setStatus(null);

        // Create the Assetassetmbrrecordtype, which fails.

        restAssetassetmbrrecordtypeMockMvc.perform(post("/api/assetassetmbrrecordtypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetassetmbrrecordtype)))
            .andExpect(status().isBadRequest());

        List<Assetassetmbrrecordtype> assetassetmbrrecordtypeList = assetassetmbrrecordtypeRepository.findAll();
        assertThat(assetassetmbrrecordtypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastmodifiedbyIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetassetmbrrecordtypeRepository.findAll().size();
        // set the field null
        assetassetmbrrecordtype.setLastmodifiedby(null);

        // Create the Assetassetmbrrecordtype, which fails.

        restAssetassetmbrrecordtypeMockMvc.perform(post("/api/assetassetmbrrecordtypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetassetmbrrecordtype)))
            .andExpect(status().isBadRequest());

        List<Assetassetmbrrecordtype> assetassetmbrrecordtypeList = assetassetmbrrecordtypeRepository.findAll();
        assertThat(assetassetmbrrecordtypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastmodifieddatetimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetassetmbrrecordtypeRepository.findAll().size();
        // set the field null
        assetassetmbrrecordtype.setLastmodifieddatetime(null);

        // Create the Assetassetmbrrecordtype, which fails.

        restAssetassetmbrrecordtypeMockMvc.perform(post("/api/assetassetmbrrecordtypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetassetmbrrecordtype)))
            .andExpect(status().isBadRequest());

        List<Assetassetmbrrecordtype> assetassetmbrrecordtypeList = assetassetmbrrecordtypeRepository.findAll();
        assertThat(assetassetmbrrecordtypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAssetassetmbrrecordtypes() throws Exception {
        // Initialize the database
        assetassetmbrrecordtypeRepository.saveAndFlush(assetassetmbrrecordtype);

        // Get all the assetassetmbrrecordtypeList
        restAssetassetmbrrecordtypeMockMvc.perform(get("/api/assetassetmbrrecordtypes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assetassetmbrrecordtype.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].nameshort").value(hasItem(DEFAULT_NAMESHORT.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].lastmodifiedby").value(hasItem(DEFAULT_LASTMODIFIEDBY.toString())))
            .andExpect(jsonPath("$.[*].lastmodifieddatetime").value(hasItem(sameInstant(DEFAULT_LASTMODIFIEDDATETIME))))
            .andExpect(jsonPath("$.[*].domain").value(hasItem(DEFAULT_DOMAIN.toString())));
    }

    @Test
    @Transactional
    public void getAssetassetmbrrecordtype() throws Exception {
        // Initialize the database
        assetassetmbrrecordtypeRepository.saveAndFlush(assetassetmbrrecordtype);

        // Get the assetassetmbrrecordtype
        restAssetassetmbrrecordtypeMockMvc.perform(get("/api/assetassetmbrrecordtypes/{id}", assetassetmbrrecordtype.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(assetassetmbrrecordtype.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.nameshort").value(DEFAULT_NAMESHORT.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.lastmodifiedby").value(DEFAULT_LASTMODIFIEDBY.toString()))
            .andExpect(jsonPath("$.lastmodifieddatetime").value(sameInstant(DEFAULT_LASTMODIFIEDDATETIME)))
            .andExpect(jsonPath("$.domain").value(DEFAULT_DOMAIN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAssetassetmbrrecordtype() throws Exception {
        // Get the assetassetmbrrecordtype
        restAssetassetmbrrecordtypeMockMvc.perform(get("/api/assetassetmbrrecordtypes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssetassetmbrrecordtype() throws Exception {
        // Initialize the database
        assetassetmbrrecordtypeRepository.saveAndFlush(assetassetmbrrecordtype);
        assetassetmbrrecordtypeSearchRepository.save(assetassetmbrrecordtype);
        int databaseSizeBeforeUpdate = assetassetmbrrecordtypeRepository.findAll().size();

        // Update the assetassetmbrrecordtype
        Assetassetmbrrecordtype updatedAssetassetmbrrecordtype = assetassetmbrrecordtypeRepository.findOne(assetassetmbrrecordtype.getId());
        updatedAssetassetmbrrecordtype
            .name(UPDATED_NAME)
            .nameshort(UPDATED_NAMESHORT)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .lastmodifiedby(UPDATED_LASTMODIFIEDBY)
            .lastmodifieddatetime(UPDATED_LASTMODIFIEDDATETIME)
            .domain(UPDATED_DOMAIN);

        restAssetassetmbrrecordtypeMockMvc.perform(put("/api/assetassetmbrrecordtypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAssetassetmbrrecordtype)))
            .andExpect(status().isOk());

        // Validate the Assetassetmbrrecordtype in the database
        List<Assetassetmbrrecordtype> assetassetmbrrecordtypeList = assetassetmbrrecordtypeRepository.findAll();
        assertThat(assetassetmbrrecordtypeList).hasSize(databaseSizeBeforeUpdate);
        Assetassetmbrrecordtype testAssetassetmbrrecordtype = assetassetmbrrecordtypeList.get(assetassetmbrrecordtypeList.size() - 1);
        assertThat(testAssetassetmbrrecordtype.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAssetassetmbrrecordtype.getNameshort()).isEqualTo(UPDATED_NAMESHORT);
        assertThat(testAssetassetmbrrecordtype.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAssetassetmbrrecordtype.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAssetassetmbrrecordtype.getLastmodifiedby()).isEqualTo(UPDATED_LASTMODIFIEDBY);
        assertThat(testAssetassetmbrrecordtype.getLastmodifieddatetime()).isEqualTo(UPDATED_LASTMODIFIEDDATETIME);
        assertThat(testAssetassetmbrrecordtype.getDomain()).isEqualTo(UPDATED_DOMAIN);

        // Validate the Assetassetmbrrecordtype in Elasticsearch
        Assetassetmbrrecordtype assetassetmbrrecordtypeEs = assetassetmbrrecordtypeSearchRepository.findOne(testAssetassetmbrrecordtype.getId());
        assertThat(assetassetmbrrecordtypeEs).isEqualToComparingFieldByField(testAssetassetmbrrecordtype);
    }

    @Test
    @Transactional
    public void updateNonExistingAssetassetmbrrecordtype() throws Exception {
        int databaseSizeBeforeUpdate = assetassetmbrrecordtypeRepository.findAll().size();

        // Create the Assetassetmbrrecordtype

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAssetassetmbrrecordtypeMockMvc.perform(put("/api/assetassetmbrrecordtypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetassetmbrrecordtype)))
            .andExpect(status().isCreated());

        // Validate the Assetassetmbrrecordtype in the database
        List<Assetassetmbrrecordtype> assetassetmbrrecordtypeList = assetassetmbrrecordtypeRepository.findAll();
        assertThat(assetassetmbrrecordtypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAssetassetmbrrecordtype() throws Exception {
        // Initialize the database
        assetassetmbrrecordtypeRepository.saveAndFlush(assetassetmbrrecordtype);
        assetassetmbrrecordtypeSearchRepository.save(assetassetmbrrecordtype);
        int databaseSizeBeforeDelete = assetassetmbrrecordtypeRepository.findAll().size();

        // Get the assetassetmbrrecordtype
        restAssetassetmbrrecordtypeMockMvc.perform(delete("/api/assetassetmbrrecordtypes/{id}", assetassetmbrrecordtype.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean assetassetmbrrecordtypeExistsInEs = assetassetmbrrecordtypeSearchRepository.exists(assetassetmbrrecordtype.getId());
        assertThat(assetassetmbrrecordtypeExistsInEs).isFalse();

        // Validate the database is empty
        List<Assetassetmbrrecordtype> assetassetmbrrecordtypeList = assetassetmbrrecordtypeRepository.findAll();
        assertThat(assetassetmbrrecordtypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAssetassetmbrrecordtype() throws Exception {
        // Initialize the database
        assetassetmbrrecordtypeRepository.saveAndFlush(assetassetmbrrecordtype);
        assetassetmbrrecordtypeSearchRepository.save(assetassetmbrrecordtype);

        // Search the assetassetmbrrecordtype
        restAssetassetmbrrecordtypeMockMvc.perform(get("/api/_search/assetassetmbrrecordtypes?query=id:" + assetassetmbrrecordtype.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assetassetmbrrecordtype.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].nameshort").value(hasItem(DEFAULT_NAMESHORT.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].lastmodifiedby").value(hasItem(DEFAULT_LASTMODIFIEDBY.toString())))
            .andExpect(jsonPath("$.[*].lastmodifieddatetime").value(hasItem(sameInstant(DEFAULT_LASTMODIFIEDDATETIME))))
            .andExpect(jsonPath("$.[*].domain").value(hasItem(DEFAULT_DOMAIN.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Assetassetmbrrecordtype.class);
        Assetassetmbrrecordtype assetassetmbrrecordtype1 = new Assetassetmbrrecordtype();
        assetassetmbrrecordtype1.setId(1L);
        Assetassetmbrrecordtype assetassetmbrrecordtype2 = new Assetassetmbrrecordtype();
        assetassetmbrrecordtype2.setId(assetassetmbrrecordtype1.getId());
        assertThat(assetassetmbrrecordtype1).isEqualTo(assetassetmbrrecordtype2);
        assetassetmbrrecordtype2.setId(2L);
        assertThat(assetassetmbrrecordtype1).isNotEqualTo(assetassetmbrrecordtype2);
        assetassetmbrrecordtype1.setId(null);
        assertThat(assetassetmbrrecordtype1).isNotEqualTo(assetassetmbrrecordtype2);
    }
}
