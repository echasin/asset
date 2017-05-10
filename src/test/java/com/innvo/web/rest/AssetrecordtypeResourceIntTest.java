package com.innvo.web.rest;

import com.innvo.AssetApp;

import com.innvo.domain.Assetrecordtype;
import com.innvo.repository.AssetrecordtypeRepository;
import com.innvo.repository.search.AssetrecordtypeSearchRepository;
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
 * Test class for the AssetrecordtypeResource REST controller.
 *
 * @see AssetrecordtypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AssetApp.class)
public class AssetrecordtypeResourceIntTest {

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
    private AssetrecordtypeRepository assetrecordtypeRepository;

    @Autowired
    private AssetrecordtypeSearchRepository assetrecordtypeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAssetrecordtypeMockMvc;

    private Assetrecordtype assetrecordtype;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AssetrecordtypeResource assetrecordtypeResource = new AssetrecordtypeResource(assetrecordtypeRepository, assetrecordtypeSearchRepository);
        this.restAssetrecordtypeMockMvc = MockMvcBuilders.standaloneSetup(assetrecordtypeResource)
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
    public static Assetrecordtype createEntity(EntityManager em) {
        Assetrecordtype assetrecordtype = new Assetrecordtype()
            .name(DEFAULT_NAME)
            .nameshort(DEFAULT_NAMESHORT)
            .description(DEFAULT_DESCRIPTION)
            .status(DEFAULT_STATUS)
            .lastmodifiedby(DEFAULT_LASTMODIFIEDBY)
            .lastmodifieddatetime(DEFAULT_LASTMODIFIEDDATETIME)
            .domain(DEFAULT_DOMAIN);
        return assetrecordtype;
    }

    @Before
    public void initTest() {
        assetrecordtypeSearchRepository.deleteAll();
        assetrecordtype = createEntity(em);
    }

    @Test
    @Transactional
    public void createAssetrecordtype() throws Exception {
        int databaseSizeBeforeCreate = assetrecordtypeRepository.findAll().size();

        // Create the Assetrecordtype
        restAssetrecordtypeMockMvc.perform(post("/api/assetrecordtypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetrecordtype)))
            .andExpect(status().isCreated());

        // Validate the Assetrecordtype in the database
        List<Assetrecordtype> assetrecordtypeList = assetrecordtypeRepository.findAll();
        assertThat(assetrecordtypeList).hasSize(databaseSizeBeforeCreate + 1);
        Assetrecordtype testAssetrecordtype = assetrecordtypeList.get(assetrecordtypeList.size() - 1);
        assertThat(testAssetrecordtype.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAssetrecordtype.getNameshort()).isEqualTo(DEFAULT_NAMESHORT);
        assertThat(testAssetrecordtype.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAssetrecordtype.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAssetrecordtype.getLastmodifiedby()).isEqualTo(DEFAULT_LASTMODIFIEDBY);
        assertThat(testAssetrecordtype.getLastmodifieddatetime()).isEqualTo(DEFAULT_LASTMODIFIEDDATETIME);
        assertThat(testAssetrecordtype.getDomain()).isEqualTo(DEFAULT_DOMAIN);

        // Validate the Assetrecordtype in Elasticsearch
        Assetrecordtype assetrecordtypeEs = assetrecordtypeSearchRepository.findOne(testAssetrecordtype.getId());
        assertThat(assetrecordtypeEs).isEqualToComparingFieldByField(testAssetrecordtype);
    }

    @Test
    @Transactional
    public void createAssetrecordtypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = assetrecordtypeRepository.findAll().size();

        // Create the Assetrecordtype with an existing ID
        assetrecordtype.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssetrecordtypeMockMvc.perform(post("/api/assetrecordtypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetrecordtype)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Assetrecordtype> assetrecordtypeList = assetrecordtypeRepository.findAll();
        assertThat(assetrecordtypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetrecordtypeRepository.findAll().size();
        // set the field null
        assetrecordtype.setName(null);

        // Create the Assetrecordtype, which fails.

        restAssetrecordtypeMockMvc.perform(post("/api/assetrecordtypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetrecordtype)))
            .andExpect(status().isBadRequest());

        List<Assetrecordtype> assetrecordtypeList = assetrecordtypeRepository.findAll();
        assertThat(assetrecordtypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameshortIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetrecordtypeRepository.findAll().size();
        // set the field null
        assetrecordtype.setNameshort(null);

        // Create the Assetrecordtype, which fails.

        restAssetrecordtypeMockMvc.perform(post("/api/assetrecordtypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetrecordtype)))
            .andExpect(status().isBadRequest());

        List<Assetrecordtype> assetrecordtypeList = assetrecordtypeRepository.findAll();
        assertThat(assetrecordtypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetrecordtypeRepository.findAll().size();
        // set the field null
        assetrecordtype.setStatus(null);

        // Create the Assetrecordtype, which fails.

        restAssetrecordtypeMockMvc.perform(post("/api/assetrecordtypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetrecordtype)))
            .andExpect(status().isBadRequest());

        List<Assetrecordtype> assetrecordtypeList = assetrecordtypeRepository.findAll();
        assertThat(assetrecordtypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastmodifiedbyIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetrecordtypeRepository.findAll().size();
        // set the field null
        assetrecordtype.setLastmodifiedby(null);

        // Create the Assetrecordtype, which fails.

        restAssetrecordtypeMockMvc.perform(post("/api/assetrecordtypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetrecordtype)))
            .andExpect(status().isBadRequest());

        List<Assetrecordtype> assetrecordtypeList = assetrecordtypeRepository.findAll();
        assertThat(assetrecordtypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastmodifieddatetimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetrecordtypeRepository.findAll().size();
        // set the field null
        assetrecordtype.setLastmodifieddatetime(null);

        // Create the Assetrecordtype, which fails.

        restAssetrecordtypeMockMvc.perform(post("/api/assetrecordtypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetrecordtype)))
            .andExpect(status().isBadRequest());

        List<Assetrecordtype> assetrecordtypeList = assetrecordtypeRepository.findAll();
        assertThat(assetrecordtypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAssetrecordtypes() throws Exception {
        // Initialize the database
        assetrecordtypeRepository.saveAndFlush(assetrecordtype);

        // Get all the assetrecordtypeList
        restAssetrecordtypeMockMvc.perform(get("/api/assetrecordtypes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assetrecordtype.getId().intValue())))
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
    public void getAssetrecordtype() throws Exception {
        // Initialize the database
        assetrecordtypeRepository.saveAndFlush(assetrecordtype);

        // Get the assetrecordtype
        restAssetrecordtypeMockMvc.perform(get("/api/assetrecordtypes/{id}", assetrecordtype.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(assetrecordtype.getId().intValue()))
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
    public void getNonExistingAssetrecordtype() throws Exception {
        // Get the assetrecordtype
        restAssetrecordtypeMockMvc.perform(get("/api/assetrecordtypes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssetrecordtype() throws Exception {
        // Initialize the database
        assetrecordtypeRepository.saveAndFlush(assetrecordtype);
        assetrecordtypeSearchRepository.save(assetrecordtype);
        int databaseSizeBeforeUpdate = assetrecordtypeRepository.findAll().size();

        // Update the assetrecordtype
        Assetrecordtype updatedAssetrecordtype = assetrecordtypeRepository.findOne(assetrecordtype.getId());
        updatedAssetrecordtype
            .name(UPDATED_NAME)
            .nameshort(UPDATED_NAMESHORT)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .lastmodifiedby(UPDATED_LASTMODIFIEDBY)
            .lastmodifieddatetime(UPDATED_LASTMODIFIEDDATETIME)
            .domain(UPDATED_DOMAIN);

        restAssetrecordtypeMockMvc.perform(put("/api/assetrecordtypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAssetrecordtype)))
            .andExpect(status().isOk());

        // Validate the Assetrecordtype in the database
        List<Assetrecordtype> assetrecordtypeList = assetrecordtypeRepository.findAll();
        assertThat(assetrecordtypeList).hasSize(databaseSizeBeforeUpdate);
        Assetrecordtype testAssetrecordtype = assetrecordtypeList.get(assetrecordtypeList.size() - 1);
        assertThat(testAssetrecordtype.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAssetrecordtype.getNameshort()).isEqualTo(UPDATED_NAMESHORT);
        assertThat(testAssetrecordtype.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAssetrecordtype.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAssetrecordtype.getLastmodifiedby()).isEqualTo(UPDATED_LASTMODIFIEDBY);
        assertThat(testAssetrecordtype.getLastmodifieddatetime()).isEqualTo(UPDATED_LASTMODIFIEDDATETIME);
        assertThat(testAssetrecordtype.getDomain()).isEqualTo(UPDATED_DOMAIN);

        // Validate the Assetrecordtype in Elasticsearch
        Assetrecordtype assetrecordtypeEs = assetrecordtypeSearchRepository.findOne(testAssetrecordtype.getId());
        assertThat(assetrecordtypeEs).isEqualToComparingFieldByField(testAssetrecordtype);
    }

    @Test
    @Transactional
    public void updateNonExistingAssetrecordtype() throws Exception {
        int databaseSizeBeforeUpdate = assetrecordtypeRepository.findAll().size();

        // Create the Assetrecordtype

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAssetrecordtypeMockMvc.perform(put("/api/assetrecordtypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetrecordtype)))
            .andExpect(status().isCreated());

        // Validate the Assetrecordtype in the database
        List<Assetrecordtype> assetrecordtypeList = assetrecordtypeRepository.findAll();
        assertThat(assetrecordtypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAssetrecordtype() throws Exception {
        // Initialize the database
        assetrecordtypeRepository.saveAndFlush(assetrecordtype);
        assetrecordtypeSearchRepository.save(assetrecordtype);
        int databaseSizeBeforeDelete = assetrecordtypeRepository.findAll().size();

        // Get the assetrecordtype
        restAssetrecordtypeMockMvc.perform(delete("/api/assetrecordtypes/{id}", assetrecordtype.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean assetrecordtypeExistsInEs = assetrecordtypeSearchRepository.exists(assetrecordtype.getId());
        assertThat(assetrecordtypeExistsInEs).isFalse();

        // Validate the database is empty
        List<Assetrecordtype> assetrecordtypeList = assetrecordtypeRepository.findAll();
        assertThat(assetrecordtypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAssetrecordtype() throws Exception {
        // Initialize the database
        assetrecordtypeRepository.saveAndFlush(assetrecordtype);
        assetrecordtypeSearchRepository.save(assetrecordtype);

        // Search the assetrecordtype
        restAssetrecordtypeMockMvc.perform(get("/api/_search/assetrecordtypes?query=id:" + assetrecordtype.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assetrecordtype.getId().intValue())))
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
        TestUtil.equalsVerifier(Assetrecordtype.class);
        Assetrecordtype assetrecordtype1 = new Assetrecordtype();
        assetrecordtype1.setId(1L);
        Assetrecordtype assetrecordtype2 = new Assetrecordtype();
        assetrecordtype2.setId(assetrecordtype1.getId());
        assertThat(assetrecordtype1).isEqualTo(assetrecordtype2);
        assetrecordtype2.setId(2L);
        assertThat(assetrecordtype1).isNotEqualTo(assetrecordtype2);
        assetrecordtype1.setId(null);
        assertThat(assetrecordtype1).isNotEqualTo(assetrecordtype2);
    }
}
