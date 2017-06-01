package com.innvo.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.innvo.domain.Modelrecordtype;

import com.innvo.repository.ModelrecordtypeRepository;
import com.innvo.repository.search.ModelrecordtypeSearchRepository;
import com.innvo.service.DomainService;
import com.innvo.web.rest.util.HeaderUtil;
import com.innvo.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Modelrecordtype.
 */
@RestController
@RequestMapping("/api")
public class ModelrecordtypeResource {

    private final Logger log = LoggerFactory.getLogger(ModelrecordtypeResource.class);

    private static final String ENTITY_NAME = "modelrecordtype";
        
    private final ModelrecordtypeRepository modelrecordtypeRepository;

    private final ModelrecordtypeSearchRepository modelrecordtypeSearchRepository;

    @Autowired
    DomainService domainService;
    
    public ModelrecordtypeResource(ModelrecordtypeRepository modelrecordtypeRepository, ModelrecordtypeSearchRepository modelrecordtypeSearchRepository) {
        this.modelrecordtypeRepository = modelrecordtypeRepository;
        this.modelrecordtypeSearchRepository = modelrecordtypeSearchRepository;
    }

    /**
     * POST  /modelrecordtypes : Create a new modelrecordtype.
     *
     * @param modelrecordtype the modelrecordtype to create
     * @return the ResponseEntity with status 201 (Created) and with body the new modelrecordtype, or with status 400 (Bad Request) if the modelrecordtype has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/modelrecordtypes")
    @Timed
    public ResponseEntity<Modelrecordtype> createModelrecordtype(@Valid @RequestBody Modelrecordtype modelrecordtype) throws URISyntaxException {
        log.debug("REST request to save Modelrecordtype : {}", modelrecordtype);
        if (modelrecordtype.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new modelrecordtype cannot already have an ID")).body(null);
        }
        ZonedDateTime lastmodifieddate = ZonedDateTime.now(ZoneId.systemDefault());
        modelrecordtype.setLastmodifieddatetime(lastmodifieddate);
        modelrecordtype.setDomain(domainService.getDomain());
        Modelrecordtype result = modelrecordtypeRepository.save(modelrecordtype);
        modelrecordtypeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/modelrecordtypes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /modelrecordtypes : Updates an existing modelrecordtype.
     *
     * @param modelrecordtype the modelrecordtype to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated modelrecordtype,
     * or with status 400 (Bad Request) if the modelrecordtype is not valid,
     * or with status 500 (Internal Server Error) if the modelrecordtype couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/modelrecordtypes")
    @Timed
    public ResponseEntity<Modelrecordtype> updateModelrecordtype(@Valid @RequestBody Modelrecordtype modelrecordtype) throws URISyntaxException {
        log.debug("REST request to update Modelrecordtype : {}", modelrecordtype);
        if (modelrecordtype.getId() == null) {
            return createModelrecordtype(modelrecordtype);
        }
        ZonedDateTime lastmodifieddate = ZonedDateTime.now(ZoneId.systemDefault());
        modelrecordtype.setLastmodifieddatetime(lastmodifieddate);
        modelrecordtype.setDomain(domainService.getDomain());
        Modelrecordtype result = modelrecordtypeRepository.save(modelrecordtype);
        modelrecordtypeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, modelrecordtype.getId().toString()))
            .body(result);
    }

    /**
     * GET  /modelrecordtypes : get all the modelrecordtypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of modelrecordtypes in body
     */
    @GetMapping("/modelrecordtypes")
    @Timed
    public ResponseEntity<List<Modelrecordtype>> getAllModelrecordtypes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Modelrecordtypes");
        Page<Modelrecordtype> page = modelrecordtypeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/modelrecordtypes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /modelrecordtypes/:id : get the "id" modelrecordtype.
     *
     * @param id the id of the modelrecordtype to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the modelrecordtype, or with status 404 (Not Found)
     */
    @GetMapping("/modelrecordtypes/{id}")
    @Timed
    public ResponseEntity<Modelrecordtype> getModelrecordtype(@PathVariable Long id) {
        log.debug("REST request to get Modelrecordtype : {}", id);
        Modelrecordtype modelrecordtype = modelrecordtypeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(modelrecordtype));
    }

    /**
     * DELETE  /modelrecordtypes/:id : delete the "id" modelrecordtype.
     *
     * @param id the id of the modelrecordtype to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/modelrecordtypes/{id}")
    @Timed
    public ResponseEntity<Void> deleteModelrecordtype(@PathVariable Long id) {
        log.debug("REST request to delete Modelrecordtype : {}", id);
        modelrecordtypeRepository.delete(id);
        modelrecordtypeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/modelrecordtypes?query=:query : search for the modelrecordtype corresponding
     * to the query.
     *
     * @param query the query of the modelrecordtype search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/modelrecordtypes")
    @Timed
    public ResponseEntity<List<Modelrecordtype>> searchModelrecordtypes(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Modelrecordtypes for query {}", query);
        Page<Modelrecordtype> page = modelrecordtypeSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/modelrecordtypes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
