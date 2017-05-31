package com.innvo.web.rest;

import com.innvo.AssetApp;

import com.innvo.domain.Assetassetmbr;
import com.innvo.domain.Asset;
import com.innvo.domain.Asset;
import com.innvo.domain.Model;
import com.innvo.repository.AssetassetmbrRepository;
import com.innvo.repository.search.AssetassetmbrSearchRepository;
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
 * Test class for the AssetassetmbrResource REST controller.
 *
 * @see AssetassetmbrResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AssetApp.class)
public class AssetassetmbrResourceIntTest {

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final Integer DEFAULT_XCOORDINATE = 1;
    private static final Integer UPDATED_XCOORDINATE = 2;

    private static final Integer DEFAULT_YCOORDINATE = 1;
    private static final Integer UPDATED_YCOORDINATE = 2;

    private static final String DEFAULT_PARENTINSTANCE = "AAAAAAAAAA";
    private static final String UPDATED_PARENTINSTANCE = "BBBBBBBBBB";

    private static final String DEFAULT_CHILDINSTANCE = "AAAAAAAAAA";
    private static final String UPDATED_CHILDINSTANCE = "BBBBBBBBBB";

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
    private AssetassetmbrRepository assetassetmbrRepository;

    @Autowired
    private AssetassetmbrSearchRepository assetassetmbrSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAssetassetmbrMockMvc;

    private Assetassetmbr assetassetmbr;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AssetassetmbrResource assetassetmbrResource = new AssetassetmbrResource(assetassetmbrRepository, assetassetmbrSearchRepository);
        this.restAssetassetmbrMockMvc = MockMvcBuilders.standaloneSetup(assetassetmbrResource)
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
    public static Assetassetmbr createEntity(EntityManager em) {
        Assetassetmbr assetassetmbr = new Assetassetmbr()
            .comment(DEFAULT_COMMENT)
            .xcoordinate(DEFAULT_XCOORDINATE)
            .ycoordinate(DEFAULT_YCOORDINATE)
            .parentinstance(DEFAULT_PARENTINSTANCE)
            .childinstance(DEFAULT_CHILDINSTANCE)
            .description(DEFAULT_DESCRIPTION)
            .status(DEFAULT_STATUS)
            .lastmodifiedby(DEFAULT_LASTMODIFIEDBY)
            .lastmodifieddatetime(DEFAULT_LASTMODIFIEDDATETIME)
            .domain(DEFAULT_DOMAIN);
        // Add required entity
        Asset parentasset = AssetResourceIntTest.createEntity(em);
        em.persist(parentasset);
        em.flush();
        assetassetmbr.setParentasset(parentasset);
        // Add required entity
        Asset childasset = AssetResourceIntTest.createEntity(em);
        em.persist(childasset);
        em.flush();
        assetassetmbr.setChildasset(childasset);
        // Add required entity
        Model model = ModelResourceIntTest.createEntity(em);
        em.persist(model);
        em.flush();
        assetassetmbr.setModel(model);
        return assetassetmbr;
    }

    @Before
    public void initTest() {
        assetassetmbrSearchRepository.deleteAll();
        assetassetmbr = createEntity(em);
    }

    @Test
    @Transactional
    public void createAssetassetmbr() throws Exception {
        int databaseSizeBeforeCreate = assetassetmbrRepository.findAll().size();

        // Create the Assetassetmbr
        restAssetassetmbrMockMvc.perform(post("/api/assetassetmbrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetassetmbr)))
            .andExpect(status().isCreated());

        // Validate the Assetassetmbr in the database
        List<Assetassetmbr> assetassetmbrList = assetassetmbrRepository.findAll();
        assertThat(assetassetmbrList).hasSize(databaseSizeBeforeCreate + 1);
        Assetassetmbr testAssetassetmbr = assetassetmbrList.get(assetassetmbrList.size() - 1);
        assertThat(testAssetassetmbr.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testAssetassetmbr.getXcoordinate()).isEqualTo(DEFAULT_XCOORDINATE);
        assertThat(testAssetassetmbr.getYcoordinate()).isEqualTo(DEFAULT_YCOORDINATE);
        assertThat(testAssetassetmbr.getParentinstance()).isEqualTo(DEFAULT_PARENTINSTANCE);
        assertThat(testAssetassetmbr.getChildinstance()).isEqualTo(DEFAULT_CHILDINSTANCE);
        assertThat(testAssetassetmbr.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAssetassetmbr.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAssetassetmbr.getLastmodifiedby()).isEqualTo(DEFAULT_LASTMODIFIEDBY);
        assertThat(testAssetassetmbr.getLastmodifieddatetime()).isEqualTo(DEFAULT_LASTMODIFIEDDATETIME);
        assertThat(testAssetassetmbr.getDomain()).isEqualTo(DEFAULT_DOMAIN);

        // Validate the Assetassetmbr in Elasticsearch
        Assetassetmbr assetassetmbrEs = assetassetmbrSearchRepository.findOne(testAssetassetmbr.getId());
        assertThat(assetassetmbrEs).isEqualToComparingFieldByField(testAssetassetmbr);
    }

    @Test
    @Transactional
    public void createAssetassetmbrWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = assetassetmbrRepository.findAll().size();

        // Create the Assetassetmbr with an existing ID
        assetassetmbr.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssetassetmbrMockMvc.perform(post("/api/assetassetmbrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetassetmbr)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Assetassetmbr> assetassetmbrList = assetassetmbrRepository.findAll();
        assertThat(assetassetmbrList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameshortIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetassetmbrRepository.findAll().size();
        // set the field null
        assetassetmbr.setNameshort(null);

        // Create the Assetassetmbr, which fails.

        restAssetassetmbrMockMvc.perform(post("/api/assetassetmbrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetassetmbr)))
            .andExpect(status().isBadRequest());

        List<Assetassetmbr> assetassetmbrList = assetassetmbrRepository.findAll();
        assertThat(assetassetmbrList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetassetmbrRepository.findAll().size();
        // set the field null
        assetassetmbr.setStatus(null);

        // Create the Assetassetmbr, which fails.

        restAssetassetmbrMockMvc.perform(post("/api/assetassetmbrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetassetmbr)))
            .andExpect(status().isBadRequest());

        List<Assetassetmbr> assetassetmbrList = assetassetmbrRepository.findAll();
        assertThat(assetassetmbrList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastmodifiedbyIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetassetmbrRepository.findAll().size();
        // set the field null
        assetassetmbr.setLastmodifiedby(null);

        // Create the Assetassetmbr, which fails.

        restAssetassetmbrMockMvc.perform(post("/api/assetassetmbrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetassetmbr)))
            .andExpect(status().isBadRequest());

        List<Assetassetmbr> assetassetmbrList = assetassetmbrRepository.findAll();
        assertThat(assetassetmbrList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastmodifieddatetimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetassetmbrRepository.findAll().size();
        // set the field null
        assetassetmbr.setLastmodifieddatetime(null);

        // Create the Assetassetmbr, which fails.

        restAssetassetmbrMockMvc.perform(post("/api/assetassetmbrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetassetmbr)))
            .andExpect(status().isBadRequest());

        List<Assetassetmbr> assetassetmbrList = assetassetmbrRepository.findAll();
        assertThat(assetassetmbrList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAssetassetmbrs() throws Exception {
        // Initialize the database
        assetassetmbrRepository.saveAndFlush(assetassetmbr);

        // Get all the assetassetmbrList
        restAssetassetmbrMockMvc.perform(get("/api/assetassetmbrs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assetassetmbr.getId().intValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].xcoordinate").value(hasItem(DEFAULT_XCOORDINATE)))
            .andExpect(jsonPath("$.[*].ycoordinate").value(hasItem(DEFAULT_YCOORDINATE)))
            .andExpect(jsonPath("$.[*].parentinstance").value(hasItem(DEFAULT_PARENTINSTANCE.toString())))
            .andExpect(jsonPath("$.[*].childinstance").value(hasItem(DEFAULT_CHILDINSTANCE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].lastmodifiedby").value(hasItem(DEFAULT_LASTMODIFIEDBY.toString())))
            .andExpect(jsonPath("$.[*].lastmodifieddatetime").value(hasItem(sameInstant(DEFAULT_LASTMODIFIEDDATETIME))))
            .andExpect(jsonPath("$.[*].domain").value(hasItem(DEFAULT_DOMAIN.toString())));
    }

    @Test
    @Transactional
    public void getAssetassetmbr() throws Exception {
        // Initialize the database
        assetassetmbrRepository.saveAndFlush(assetassetmbr);

        // Get the assetassetmbr
        restAssetassetmbrMockMvc.perform(get("/api/assetassetmbrs/{id}", assetassetmbr.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(assetassetmbr.getId().intValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.xcoordinate").value(DEFAULT_XCOORDINATE))
            .andExpect(jsonPath("$.ycoordinate").value(DEFAULT_YCOORDINATE))
            .andExpect(jsonPath("$.parentinstance").value(DEFAULT_PARENTINSTANCE.toString()))
            .andExpect(jsonPath("$.childinstance").value(DEFAULT_CHILDINSTANCE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.lastmodifiedby").value(DEFAULT_LASTMODIFIEDBY.toString()))
            .andExpect(jsonPath("$.lastmodifieddatetime").value(sameInstant(DEFAULT_LASTMODIFIEDDATETIME)))
            .andExpect(jsonPath("$.domain").value(DEFAULT_DOMAIN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAssetassetmbr() throws Exception {
        // Get the assetassetmbr
        restAssetassetmbrMockMvc.perform(get("/api/assetassetmbrs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssetassetmbr() throws Exception {
        // Initialize the database
        assetassetmbrRepository.saveAndFlush(assetassetmbr);
        assetassetmbrSearchRepository.save(assetassetmbr);
        int databaseSizeBeforeUpdate = assetassetmbrRepository.findAll().size();

        // Update the assetassetmbr
        Assetassetmbr updatedAssetassetmbr = assetassetmbrRepository.findOne(assetassetmbr.getId());
        updatedAssetassetmbr
            .comment(UPDATED_COMMENT)
            .xcoordinate(UPDATED_XCOORDINATE)
            .ycoordinate(UPDATED_YCOORDINATE)
            .parentinstance(UPDATED_PARENTINSTANCE)
            .childinstance(UPDATED_CHILDINSTANCE)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .lastmodifiedby(UPDATED_LASTMODIFIEDBY)
            .lastmodifieddatetime(UPDATED_LASTMODIFIEDDATETIME)
            .domain(UPDATED_DOMAIN);

        restAssetassetmbrMockMvc.perform(put("/api/assetassetmbrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAssetassetmbr)))
            .andExpect(status().isOk());

        // Validate the Assetassetmbr in the database
        List<Assetassetmbr> assetassetmbrList = assetassetmbrRepository.findAll();
        assertThat(assetassetmbrList).hasSize(databaseSizeBeforeUpdate);
        Assetassetmbr testAssetassetmbr = assetassetmbrList.get(assetassetmbrList.size() - 1);
        assertThat(testAssetassetmbr.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testAssetassetmbr.getXcoordinate()).isEqualTo(UPDATED_XCOORDINATE);
        assertThat(testAssetassetmbr.getYcoordinate()).isEqualTo(UPDATED_YCOORDINATE);
        assertThat(testAssetassetmbr.getParentinstance()).isEqualTo(UPDATED_PARENTINSTANCE);
        assertThat(testAssetassetmbr.getChildinstance()).isEqualTo(UPDATED_CHILDINSTANCE);
        assertThat(testAssetassetmbr.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAssetassetmbr.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAssetassetmbr.getLastmodifiedby()).isEqualTo(UPDATED_LASTMODIFIEDBY);
        assertThat(testAssetassetmbr.getLastmodifieddatetime()).isEqualTo(UPDATED_LASTMODIFIEDDATETIME);
        assertThat(testAssetassetmbr.getDomain()).isEqualTo(UPDATED_DOMAIN);

        // Validate the Assetassetmbr in Elasticsearch
        Assetassetmbr assetassetmbrEs = assetassetmbrSearchRepository.findOne(testAssetassetmbr.getId());
        assertThat(assetassetmbrEs).isEqualToComparingFieldByField(testAssetassetmbr);
    }

    @Test
    @Transactional
    public void updateNonExistingAssetassetmbr() throws Exception {
        int databaseSizeBeforeUpdate = assetassetmbrRepository.findAll().size();

        // Create the Assetassetmbr

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAssetassetmbrMockMvc.perform(put("/api/assetassetmbrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetassetmbr)))
            .andExpect(status().isCreated());

        // Validate the Assetassetmbr in the database
        List<Assetassetmbr> assetassetmbrList = assetassetmbrRepository.findAll();
        assertThat(assetassetmbrList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAssetassetmbr() throws Exception {
        // Initialize the database
        assetassetmbrRepository.saveAndFlush(assetassetmbr);
        assetassetmbrSearchRepository.save(assetassetmbr);
        int databaseSizeBeforeDelete = assetassetmbrRepository.findAll().size();

        // Get the assetassetmbr
        restAssetassetmbrMockMvc.perform(delete("/api/assetassetmbrs/{id}", assetassetmbr.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean assetassetmbrExistsInEs = assetassetmbrSearchRepository.exists(assetassetmbr.getId());
        assertThat(assetassetmbrExistsInEs).isFalse();

        // Validate the database is empty
        List<Assetassetmbr> assetassetmbrList = assetassetmbrRepository.findAll();
        assertThat(assetassetmbrList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAssetassetmbr() throws Exception {
        // Initialize the database
        assetassetmbrRepository.saveAndFlush(assetassetmbr);
        assetassetmbrSearchRepository.save(assetassetmbr);

        // Search the assetassetmbr
        restAssetassetmbrMockMvc.perform(get("/api/_search/assetassetmbrs?query=id:" + assetassetmbr.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assetassetmbr.getId().intValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].xcoordinate").value(hasItem(DEFAULT_XCOORDINATE)))
            .andExpect(jsonPath("$.[*].ycoordinate").value(hasItem(DEFAULT_YCOORDINATE)))
            .andExpect(jsonPath("$.[*].parentinstance").value(hasItem(DEFAULT_PARENTINSTANCE.toString())))
            .andExpect(jsonPath("$.[*].childinstance").value(hasItem(DEFAULT_CHILDINSTANCE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].lastmodifiedby").value(hasItem(DEFAULT_LASTMODIFIEDBY.toString())))
            .andExpect(jsonPath("$.[*].lastmodifieddatetime").value(hasItem(sameInstant(DEFAULT_LASTMODIFIEDDATETIME))))
            .andExpect(jsonPath("$.[*].domain").value(hasItem(DEFAULT_DOMAIN.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Assetassetmbr.class);
        Assetassetmbr assetassetmbr1 = new Assetassetmbr();
        assetassetmbr1.setId(1L);
        Assetassetmbr assetassetmbr2 = new Assetassetmbr();
        assetassetmbr2.setId(assetassetmbr1.getId());
        assertThat(assetassetmbr1).isEqualTo(assetassetmbr2);
        assetassetmbr2.setId(2L);
        assertThat(assetassetmbr1).isNotEqualTo(assetassetmbr2);
        assetassetmbr1.setId(null);
        assertThat(assetassetmbr1).isNotEqualTo(assetassetmbr2);
    }
}
