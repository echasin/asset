package com.innvo.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.innvo.domain.Assetassetmbrrecordtype;

import com.innvo.repository.AssetassetmbrrecordtypeRepository;
import com.innvo.repository.search.AssetassetmbrrecordtypeSearchRepository;
import com.innvo.web.rest.util.HeaderUtil;
import com.innvo.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Assetassetmbrrecordtype.
 */
@RestController
@RequestMapping("/api")
public class AssetassetmbrrecordtypeResource {

    private final Logger log = LoggerFactory.getLogger(AssetassetmbrrecordtypeResource.class);

    private static final String ENTITY_NAME = "assetassetmbrrecordtype";
        
    private final AssetassetmbrrecordtypeRepository assetassetmbrrecordtypeRepository;

    private final AssetassetmbrrecordtypeSearchRepository assetassetmbrrecordtypeSearchRepository;

    public AssetassetmbrrecordtypeResource(AssetassetmbrrecordtypeRepository assetassetmbrrecordtypeRepository, AssetassetmbrrecordtypeSearchRepository assetassetmbrrecordtypeSearchRepository) {
        this.assetassetmbrrecordtypeRepository = assetassetmbrrecordtypeRepository;
        this.assetassetmbrrecordtypeSearchRepository = assetassetmbrrecordtypeSearchRepository;
    }

    /**
     * POST  /assetassetmbrrecordtypes : Create a new assetassetmbrrecordtype.
     *
     * @param assetassetmbrrecordtype the assetassetmbrrecordtype to create
     * @return the ResponseEntity with status 201 (Created) and with body the new assetassetmbrrecordtype, or with status 400 (Bad Request) if the assetassetmbrrecordtype has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/assetassetmbrrecordtypes")
    @Timed
    public ResponseEntity<Assetassetmbrrecordtype> createAssetassetmbrrecordtype(@Valid @RequestBody Assetassetmbrrecordtype assetassetmbrrecordtype) throws URISyntaxException {
        log.debug("REST request to save Assetassetmbrrecordtype : {}", assetassetmbrrecordtype);
        if (assetassetmbrrecordtype.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new assetassetmbrrecordtype cannot already have an ID")).body(null);
        }
        Assetassetmbrrecordtype result = assetassetmbrrecordtypeRepository.save(assetassetmbrrecordtype);
        assetassetmbrrecordtypeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/assetassetmbrrecordtypes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /assetassetmbrrecordtypes : Updates an existing assetassetmbrrecordtype.
     *
     * @param assetassetmbrrecordtype the assetassetmbrrecordtype to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated assetassetmbrrecordtype,
     * or with status 400 (Bad Request) if the assetassetmbrrecordtype is not valid,
     * or with status 500 (Internal Server Error) if the assetassetmbrrecordtype couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/assetassetmbrrecordtypes")
    @Timed
    public ResponseEntity<Assetassetmbrrecordtype> updateAssetassetmbrrecordtype(@Valid @RequestBody Assetassetmbrrecordtype assetassetmbrrecordtype) throws URISyntaxException {
        log.debug("REST request to update Assetassetmbrrecordtype : {}", assetassetmbrrecordtype);
        if (assetassetmbrrecordtype.getId() == null) {
            return createAssetassetmbrrecordtype(assetassetmbrrecordtype);
        }
        Assetassetmbrrecordtype result = assetassetmbrrecordtypeRepository.save(assetassetmbrrecordtype);
        assetassetmbrrecordtypeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, assetassetmbrrecordtype.getId().toString()))
            .body(result);
    }

    /**
     * GET  /assetassetmbrrecordtypes : get all the assetassetmbrrecordtypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of assetassetmbrrecordtypes in body
     */
    @GetMapping("/assetassetmbrrecordtypes")
    @Timed
    public ResponseEntity<List<Assetassetmbrrecordtype>> getAllAssetassetmbrrecordtypes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Assetassetmbrrecordtypes");
        Page<Assetassetmbrrecordtype> page = assetassetmbrrecordtypeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/assetassetmbrrecordtypes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /assetassetmbrrecordtypes/:id : get the "id" assetassetmbrrecordtype.
     *
     * @param id the id of the assetassetmbrrecordtype to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the assetassetmbrrecordtype, or with status 404 (Not Found)
     */
    @GetMapping("/assetassetmbrrecordtypes/{id}")
    @Timed
    public ResponseEntity<Assetassetmbrrecordtype> getAssetassetmbrrecordtype(@PathVariable Long id) {
        log.debug("REST request to get Assetassetmbrrecordtype : {}", id);
        Assetassetmbrrecordtype assetassetmbrrecordtype = assetassetmbrrecordtypeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(assetassetmbrrecordtype));
    }

    /**
     * DELETE  /assetassetmbrrecordtypes/:id : delete the "id" assetassetmbrrecordtype.
     *
     * @param id the id of the assetassetmbrrecordtype to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/assetassetmbrrecordtypes/{id}")
    @Timed
    public ResponseEntity<Void> deleteAssetassetmbrrecordtype(@PathVariable Long id) {
        log.debug("REST request to delete Assetassetmbrrecordtype : {}", id);
        assetassetmbrrecordtypeRepository.delete(id);
        assetassetmbrrecordtypeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/assetassetmbrrecordtypes?query=:query : search for the assetassetmbrrecordtype corresponding
     * to the query.
     *
     * @param query the query of the assetassetmbrrecordtype search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/assetassetmbrrecordtypes")
    @Timed
    public ResponseEntity<List<Assetassetmbrrecordtype>> searchAssetassetmbrrecordtypes(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Assetassetmbrrecordtypes for query {}", query);
        Page<Assetassetmbrrecordtype> page = assetassetmbrrecordtypeSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/assetassetmbrrecordtypes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
