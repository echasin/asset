package com.innvo.web.rest;

import com.innvo.AssetApp;

import com.innvo.domain.Modelrecordtype;
import com.innvo.repository.ModelrecordtypeRepository;
import com.innvo.repository.search.ModelrecordtypeSearchRepository;
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
 * Test class for the ModelrecordtypeResource REST controller.
 *
 * @see ModelrecordtypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AssetApp.class)
public class ModelrecordtypeResourceIntTest {

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
    private ModelrecordtypeRepository modelrecordtypeRepository;

    @Autowired
    private ModelrecordtypeSearchRepository modelrecordtypeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restModelrecordtypeMockMvc;

    private Modelrecordtype modelrecordtype;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ModelrecordtypeResource modelrecordtypeResource = new ModelrecordtypeResource(modelrecordtypeRepository, modelrecordtypeSearchRepository);
        this.restModelrecordtypeMockMvc = MockMvcBuilders.standaloneSetup(modelrecordtypeResource)
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
    public static Modelrecordtype createEntity(EntityManager em) {
        Modelrecordtype modelrecordtype = new Modelrecordtype()
            .name(DEFAULT_NAME)
            .nameshort(DEFAULT_NAMESHORT)
            .description(DEFAULT_DESCRIPTION)
            .status(DEFAULT_STATUS)
            .lastmodifiedby(DEFAULT_LASTMODIFIEDBY)
            .lastmodifieddatetime(DEFAULT_LASTMODIFIEDDATETIME)
            .domain(DEFAULT_DOMAIN);
        return modelrecordtype;
    }

    @Before
    public void initTest() {
        modelrecordtypeSearchRepository.deleteAll();
        modelrecordtype = createEntity(em);
    }

    @Test
    @Transactional
    public void createModelrecordtype() throws Exception {
        int databaseSizeBeforeCreate = modelrecordtypeRepository.findAll().size();

        // Create the Modelrecordtype
        restModelrecordtypeMockMvc.perform(post("/api/modelrecordtypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modelrecordtype)))
            .andExpect(status().isCreated());

        // Validate the Modelrecordtype in the database
        List<Modelrecordtype> modelrecordtypeList = modelrecordtypeRepository.findAll();
        assertThat(modelrecordtypeList).hasSize(databaseSizeBeforeCreate + 1);
        Modelrecordtype testModelrecordtype = modelrecordtypeList.get(modelrecordtypeList.size() - 1);
        assertThat(testModelrecordtype.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testModelrecordtype.getNameshort()).isEqualTo(DEFAULT_NAMESHORT);
        assertThat(testModelrecordtype.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testModelrecordtype.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testModelrecordtype.getLastmodifiedby()).isEqualTo(DEFAULT_LASTMODIFIEDBY);
        assertThat(testModelrecordtype.getLastmodifieddatetime()).isEqualTo(DEFAULT_LASTMODIFIEDDATETIME);
        assertThat(testModelrecordtype.getDomain()).isEqualTo(DEFAULT_DOMAIN);

        // Validate the Modelrecordtype in Elasticsearch
        Modelrecordtype modelrecordtypeEs = modelrecordtypeSearchRepository.findOne(testModelrecordtype.getId());
        assertThat(modelrecordtypeEs).isEqualToComparingFieldByField(testModelrecordtype);
    }

    @Test
    @Transactional
    public void createModelrecordtypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = modelrecordtypeRepository.findAll().size();

        // Create the Modelrecordtype with an existing ID
        modelrecordtype.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restModelrecordtypeMockMvc.perform(post("/api/modelrecordtypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modelrecordtype)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Modelrecordtype> modelrecordtypeList = modelrecordtypeRepository.findAll();
        assertThat(modelrecordtypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = modelrecordtypeRepository.findAll().size();
        // set the field null
        modelrecordtype.setName(null);

        // Create the Modelrecordtype, which fails.

        restModelrecordtypeMockMvc.perform(post("/api/modelrecordtypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modelrecordtype)))
            .andExpect(status().isBadRequest());

        List<Modelrecordtype> modelrecordtypeList = modelrecordtypeRepository.findAll();
        assertThat(modelrecordtypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameshortIsRequired() throws Exception {
        int databaseSizeBeforeTest = modelrecordtypeRepository.findAll().size();
        // set the field null
        modelrecordtype.setNameshort(null);

        // Create the Modelrecordtype, which fails.

        restModelrecordtypeMockMvc.perform(post("/api/modelrecordtypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modelrecordtype)))
            .andExpect(status().isBadRequest());

        List<Modelrecordtype> modelrecordtypeList = modelrecordtypeRepository.findAll();
        assertThat(modelrecordtypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = modelrecordtypeRepository.findAll().size();
        // set the field null
        modelrecordtype.setStatus(null);

        // Create the Modelrecordtype, which fails.

        restModelrecordtypeMockMvc.perform(post("/api/modelrecordtypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modelrecordtype)))
            .andExpect(status().isBadRequest());

        List<Modelrecordtype> modelrecordtypeList = modelrecordtypeRepository.findAll();
        assertThat(modelrecordtypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastmodifiedbyIsRequired() throws Exception {
        int databaseSizeBeforeTest = modelrecordtypeRepository.findAll().size();
        // set the field null
        modelrecordtype.setLastmodifiedby(null);

        // Create the Modelrecordtype, which fails.

        restModelrecordtypeMockMvc.perform(post("/api/modelrecordtypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modelrecordtype)))
            .andExpect(status().isBadRequest());

        List<Modelrecordtype> modelrecordtypeList = modelrecordtypeRepository.findAll();
        assertThat(modelrecordtypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastmodifieddatetimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = modelrecordtypeRepository.findAll().size();
        // set the field null
        modelrecordtype.setLastmodifieddatetime(null);

        // Create the Modelrecordtype, which fails.

        restModelrecordtypeMockMvc.perform(post("/api/modelrecordtypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modelrecordtype)))
            .andExpect(status().isBadRequest());

        List<Modelrecordtype> modelrecordtypeList = modelrecordtypeRepository.findAll();
        assertThat(modelrecordtypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllModelrecordtypes() throws Exception {
        // Initialize the database
        modelrecordtypeRepository.saveAndFlush(modelrecordtype);

        // Get all the modelrecordtypeList
        restModelrecordtypeMockMvc.perform(get("/api/modelrecordtypes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modelrecordtype.getId().intValue())))
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
    public void getModelrecordtype() throws Exception {
        // Initialize the database
        modelrecordtypeRepository.saveAndFlush(modelrecordtype);

        // Get the modelrecordtype
        restModelrecordtypeMockMvc.perform(get("/api/modelrecordtypes/{id}", modelrecordtype.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(modelrecordtype.getId().intValue()))
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
    public void getNonExistingModelrecordtype() throws Exception {
        // Get the modelrecordtype
        restModelrecordtypeMockMvc.perform(get("/api/modelrecordtypes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateModelrecordtype() throws Exception {
        // Initialize the database
        modelrecordtypeRepository.saveAndFlush(modelrecordtype);
        modelrecordtypeSearchRepository.save(modelrecordtype);
        int databaseSizeBeforeUpdate = modelrecordtypeRepository.findAll().size();

        // Update the modelrecordtype
        Modelrecordtype updatedModelrecordtype = modelrecordtypeRepository.findOne(modelrecordtype.getId());
        updatedModelrecordtype
            .name(UPDATED_NAME)
            .nameshort(UPDATED_NAMESHORT)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .lastmodifiedby(UPDATED_LASTMODIFIEDBY)
            .lastmodifieddatetime(UPDATED_LASTMODIFIEDDATETIME)
            .domain(UPDATED_DOMAIN);

        restModelrecordtypeMockMvc.perform(put("/api/modelrecordtypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedModelrecordtype)))
            .andExpect(status().isOk());

        // Validate the Modelrecordtype in the database
        List<Modelrecordtype> modelrecordtypeList = modelrecordtypeRepository.findAll();
        assertThat(modelrecordtypeList).hasSize(databaseSizeBeforeUpdate);
        Modelrecordtype testModelrecordtype = modelrecordtypeList.get(modelrecordtypeList.size() - 1);
        assertThat(testModelrecordtype.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testModelrecordtype.getNameshort()).isEqualTo(UPDATED_NAMESHORT);
        assertThat(testModelrecordtype.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testModelrecordtype.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testModelrecordtype.getLastmodifiedby()).isEqualTo(UPDATED_LASTMODIFIEDBY);
        assertThat(testModelrecordtype.getLastmodifieddatetime()).isEqualTo(UPDATED_LASTMODIFIEDDATETIME);
        assertThat(testModelrecordtype.getDomain()).isEqualTo(UPDATED_DOMAIN);

        // Validate the Modelrecordtype in Elasticsearch
        Modelrecordtype modelrecordtypeEs = modelrecordtypeSearchRepository.findOne(testModelrecordtype.getId());
        assertThat(modelrecordtypeEs).isEqualToComparingFieldByField(testModelrecordtype);
    }

    @Test
    @Transactional
    public void updateNonExistingModelrecordtype() throws Exception {
        int databaseSizeBeforeUpdate = modelrecordtypeRepository.findAll().size();

        // Create the Modelrecordtype

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restModelrecordtypeMockMvc.perform(put("/api/modelrecordtypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modelrecordtype)))
            .andExpect(status().isCreated());

        // Validate the Modelrecordtype in the database
        List<Modelrecordtype> modelrecordtypeList = modelrecordtypeRepository.findAll();
        assertThat(modelrecordtypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteModelrecordtype() throws Exception {
        // Initialize the database
        modelrecordtypeRepository.saveAndFlush(modelrecordtype);
        modelrecordtypeSearchRepository.save(modelrecordtype);
        int databaseSizeBeforeDelete = modelrecordtypeRepository.findAll().size();

        // Get the modelrecordtype
        restModelrecordtypeMockMvc.perform(delete("/api/modelrecordtypes/{id}", modelrecordtype.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean modelrecordtypeExistsInEs = modelrecordtypeSearchRepository.exists(modelrecordtype.getId());
        assertThat(modelrecordtypeExistsInEs).isFalse();

        // Validate the database is empty
        List<Modelrecordtype> modelrecordtypeList = modelrecordtypeRepository.findAll();
        assertThat(modelrecordtypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchModelrecordtype() throws Exception {
        // Initialize the database
        modelrecordtypeRepository.saveAndFlush(modelrecordtype);
        modelrecordtypeSearchRepository.save(modelrecordtype);

        // Search the modelrecordtype
        restModelrecordtypeMockMvc.perform(get("/api/_search/modelrecordtypes?query=id:" + modelrecordtype.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modelrecordtype.getId().intValue())))
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
        TestUtil.equalsVerifier(Modelrecordtype.class);
        Modelrecordtype modelrecordtype1 = new Modelrecordtype();
        modelrecordtype1.setId(1L);
        Modelrecordtype modelrecordtype2 = new Modelrecordtype();
        modelrecordtype2.setId(modelrecordtype1.getId());
        assertThat(modelrecordtype1).isEqualTo(modelrecordtype2);
        modelrecordtype2.setId(2L);
        assertThat(modelrecordtype1).isNotEqualTo(modelrecordtype2);
        modelrecordtype1.setId(null);
        assertThat(modelrecordtype1).isNotEqualTo(modelrecordtype2);
    }
}
