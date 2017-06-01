package com.innvo.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.innvo.domain.Model;

import com.innvo.repository.ModelRepository;
import com.innvo.repository.search.ModelSearchRepository;
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
 * REST controller for managing Model.
 */
@RestController
@RequestMapping("/api")
public class ModelResource {

    private final Logger log = LoggerFactory.getLogger(ModelResource.class);

    private static final String ENTITY_NAME = "model";
        
    private final ModelRepository modelRepository;

    private final ModelSearchRepository modelSearchRepository;

    @Autowired
    DomainService domainService;
    
    public ModelResource(ModelRepository modelRepository, ModelSearchRepository modelSearchRepository) {
        this.modelRepository = modelRepository;
        this.modelSearchRepository = modelSearchRepository;
    }

    /**
     * POST  /models : Create a new model.
     *
     * @param model the model to create
     * @return the ResponseEntity with status 201 (Created) and with body the new model, or with status 400 (Bad Request) if the model has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/models")
    @Timed
    public ResponseEntity<Model> createModel(@Valid @RequestBody Model model) throws URISyntaxException {
        log.debug("REST request to save Model : {}", model);
        if (model.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new model cannot already have an ID")).body(null);
        }
        ZonedDateTime lastmodifieddate = ZonedDateTime.now(ZoneId.systemDefault());
        model.setLastmodifieddatetime(lastmodifieddate);
        model.setDomain(domainService.getDomain());
        Model result = modelRepository.save(model);
        modelSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/models/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /models : Updates an existing model.
     *
     * @param model the model to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated model,
     * or with status 400 (Bad Request) if the model is not valid,
     * or with status 500 (Internal Server Error) if the model couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/models")
    @Timed
    public ResponseEntity<Model> updateModel(@Valid @RequestBody Model model) throws URISyntaxException {
        log.debug("REST request to update Model : {}", model);
        if (model.getId() == null) {
            return createModel(model);
        }
        ZonedDateTime lastmodifieddate = ZonedDateTime.now(ZoneId.systemDefault());
        model.setLastmodifieddatetime(lastmodifieddate);
        model.setDomain(domainService.getDomain());
        Model result = modelRepository.save(model);
        modelSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, model.getId().toString()))
            .body(result);
    }

    /**
     * GET  /models : get all the models.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of models in body
     */
    @GetMapping("/models")
    @Timed
    public ResponseEntity<List<Model>> getAllModels(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Models");
        Page<Model> page = modelRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/models");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /models/:id : get the "id" model.
     *
     * @param id the id of the model to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the model, or with status 404 (Not Found)
     */
    @GetMapping("/models/{id}")
    @Timed
    public ResponseEntity<Model> getModel(@PathVariable Long id) {
        log.debug("REST request to get Model : {}", id);
        Model model = modelRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(model));
    }

    /**
     * DELETE  /models/:id : delete the "id" model.
     *
     * @param id the id of the model to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/models/{id}")
    @Timed
    public ResponseEntity<Void> deleteModel(@PathVariable Long id) {
        log.debug("REST request to delete Model : {}", id);
        modelRepository.delete(id);
        modelSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/models?query=:query : search for the model corresponding
     * to the query.
     *
     * @param query the query of the model search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/models")
    @Timed
    public ResponseEntity<List<Model>> searchModels(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Models for query {}", query);
        Page<Model> page = modelSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/models");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
