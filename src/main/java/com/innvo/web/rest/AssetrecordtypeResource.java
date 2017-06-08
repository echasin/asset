package com.innvo.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.innvo.domain.Assetrecordtype;

import com.innvo.repository.AssetrecordtypeRepository;
import com.innvo.repository.search.AssetrecordtypeSearchRepository;
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
 * REST controller for managing Assetrecordtype.
 */
@RestController
@RequestMapping("/api")
public class AssetrecordtypeResource {

    private final Logger log = LoggerFactory.getLogger(AssetrecordtypeResource.class);

    private static final String ENTITY_NAME = "assetrecordtype";
        
    private final AssetrecordtypeRepository assetrecordtypeRepository;

    private final AssetrecordtypeSearchRepository assetrecordtypeSearchRepository;

    @Autowired
    DomainService domainService;
    
    public AssetrecordtypeResource(AssetrecordtypeRepository assetrecordtypeRepository, AssetrecordtypeSearchRepository assetrecordtypeSearchRepository) {
        this.assetrecordtypeRepository = assetrecordtypeRepository;
        this.assetrecordtypeSearchRepository = assetrecordtypeSearchRepository;
    }

    /**
     * POST  /assetrecordtypes : Create a new assetrecordtype.
     *
     * @param assetrecordtype the assetrecordtype to create
     * @return the ResponseEntity with status 201 (Created) and with body the new assetrecordtype, or with status 400 (Bad Request) if the assetrecordtype has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/assetrecordtypes")
    @Timed
    public ResponseEntity<Assetrecordtype> createAssetrecordtype(@Valid @RequestBody Assetrecordtype assetrecordtype) throws URISyntaxException {
        log.debug("REST request to save Assetrecordtype : {}", assetrecordtype);
        if (assetrecordtype.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new assetrecordtype cannot already have an ID")).body(null);
        }
        ZonedDateTime lastmodifieddate = ZonedDateTime.now(ZoneId.systemDefault());
        assetrecordtype.setLastmodifieddatetime(lastmodifieddate);
        assetrecordtype.setDomain(domainService.getDomain());
        Assetrecordtype result = assetrecordtypeRepository.save(assetrecordtype);
        assetrecordtypeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/assetrecordtypes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /assetrecordtypes : Updates an existing assetrecordtype.
     *
     * @param assetrecordtype the assetrecordtype to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated assetrecordtype,
     * or with status 400 (Bad Request) if the assetrecordtype is not valid,
     * or with status 500 (Internal Server Error) if the assetrecordtype couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/assetrecordtypes")
    @Timed
    public ResponseEntity<Assetrecordtype> updateAssetrecordtype(@Valid @RequestBody Assetrecordtype assetrecordtype) throws URISyntaxException {
        log.debug("REST request to update Assetrecordtype : {}", assetrecordtype);
        if (assetrecordtype.getId() == null) {
            return createAssetrecordtype(assetrecordtype);
        }
        ZonedDateTime lastmodifieddate = ZonedDateTime.now(ZoneId.systemDefault());
        assetrecordtype.setLastmodifieddatetime(lastmodifieddate);
        assetrecordtype.setDomain(domainService.getDomain());
        Assetrecordtype result = assetrecordtypeRepository.save(assetrecordtype);
        assetrecordtypeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, assetrecordtype.getId().toString()))
            .body(result);
    }

    /**
     * GET  /assetrecordtypes : get all the assetrecordtypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of assetrecordtypes in body
     */
    @GetMapping("/assetrecordtypes")
    @Timed
    public ResponseEntity<List<Assetrecordtype>> getAllAssetrecordtypes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Assetrecordtypes");
        Page<Assetrecordtype> page = assetrecordtypeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/assetrecordtypes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /assetrecordtypes/:id : get the "id" assetrecordtype.
     *
     * @param id the id of the assetrecordtype to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the assetrecordtype, or with status 404 (Not Found)
     */
    @GetMapping("/assetrecordtypes/{id}")
    @Timed
    public ResponseEntity<Assetrecordtype> getAssetrecordtype(@PathVariable Long id) {
        log.debug("REST request to get Assetrecordtype : {}", id);
        Assetrecordtype assetrecordtype = assetrecordtypeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(assetrecordtype));
    }

    /**
     * DELETE  /assetrecordtypes/:id : delete the "id" assetrecordtype.
     *
     * @param id the id of the assetrecordtype to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/assetrecordtypes/{id}")
    @Timed
    public ResponseEntity<Void> deleteAssetrecordtype(@PathVariable Long id) {
        log.debug("REST request to delete Assetrecordtype : {}", id);
        assetrecordtypeRepository.delete(id);
        assetrecordtypeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/assetrecordtypes?query=:query : search for the assetrecordtype corresponding
     * to the query.
     *
     * @param query the query of the assetrecordtype search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/assetrecordtypes")
    @Timed
    public ResponseEntity<List<Assetrecordtype>> searchAssetrecordtypes(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Assetrecordtypes for query {}", query);
        Page<Assetrecordtype> page = assetrecordtypeSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/assetrecordtypes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
