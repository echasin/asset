package com.innvo.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.innvo.domain.Assetjson;

import com.innvo.repository.AssetjsonRepository;
import com.innvo.repository.search.AssetjsonSearchRepository;
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
 * REST controller for managing Assetjson.
 */
@RestController
@RequestMapping("/api")
public class AssetjsonResource {

    private final Logger log = LoggerFactory.getLogger(AssetjsonResource.class);

    private static final String ENTITY_NAME = "assetjson";
        
    private final AssetjsonRepository assetjsonRepository;

    private final AssetjsonSearchRepository assetjsonSearchRepository;

    public AssetjsonResource(AssetjsonRepository assetjsonRepository, AssetjsonSearchRepository assetjsonSearchRepository) {
        this.assetjsonRepository = assetjsonRepository;
        this.assetjsonSearchRepository = assetjsonSearchRepository;
    }

    /**
     * POST  /assetjsons : Create a new assetjson.
     *
     * @param assetjson the assetjson to create
     * @return the ResponseEntity with status 201 (Created) and with body the new assetjson, or with status 400 (Bad Request) if the assetjson has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/assetjsons")
    @Timed
    public ResponseEntity<Assetjson> createAssetjson(@Valid @RequestBody Assetjson assetjson) throws URISyntaxException {
        log.debug("REST request to save Assetjson : {}", assetjson);
        if (assetjson.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new assetjson cannot already have an ID")).body(null);
        }
        Assetjson result = assetjsonRepository.save(assetjson);
        assetjsonSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/assetjsons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /assetjsons : Updates an existing assetjson.
     *
     * @param assetjson the assetjson to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated assetjson,
     * or with status 400 (Bad Request) if the assetjson is not valid,
     * or with status 500 (Internal Server Error) if the assetjson couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/assetjsons")
    @Timed
    public ResponseEntity<Assetjson> updateAssetjson(@Valid @RequestBody Assetjson assetjson) throws URISyntaxException {
        log.debug("REST request to update Assetjson : {}", assetjson);
        if (assetjson.getId() == null) {
            return createAssetjson(assetjson);
        }
        Assetjson result = assetjsonRepository.save(assetjson);
        assetjsonSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, assetjson.getId().toString()))
            .body(result);
    }

    /**
     * GET  /assetjsons : get all the assetjsons.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of assetjsons in body
     */
    @GetMapping("/assetjsons")
    @Timed
    public ResponseEntity<List<Assetjson>> getAllAssetjsons(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Assetjsons");
        Page<Assetjson> page = assetjsonRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/assetjsons");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /assetjsons/:id : get the "id" assetjson.
     *
     * @param id the id of the assetjson to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the assetjson, or with status 404 (Not Found)
     */
    @GetMapping("/assetjsons/{id}")
    @Timed
    public ResponseEntity<Assetjson> getAssetjson(@PathVariable Long id) {
        log.debug("REST request to get Assetjson : {}", id);
        Assetjson assetjson = assetjsonRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(assetjson));
    }

    /**
     * DELETE  /assetjsons/:id : delete the "id" assetjson.
     *
     * @param id the id of the assetjson to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/assetjsons/{id}")
    @Timed
    public ResponseEntity<Void> deleteAssetjson(@PathVariable Long id) {
        log.debug("REST request to delete Assetjson : {}", id);
        assetjsonRepository.delete(id);
        assetjsonSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/assetjsons?query=:query : search for the assetjson corresponding
     * to the query.
     *
     * @param query the query of the assetjson search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/assetjsons")
    @Timed
    public ResponseEntity<List<Assetjson>> searchAssetjsons(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Assetjsons for query {}", query);
        Page<Assetjson> page = assetjsonSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/assetjsons");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
