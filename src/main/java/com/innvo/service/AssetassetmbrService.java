package com.innvo.service;

import com.innvo.domain.Assetassetmbr;
import com.innvo.repository.AssetassetmbrRepository;
import com.innvo.repository.search.AssetassetmbrSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Assetassetmbr.
 */
@Service
@Transactional
public class AssetassetmbrService {

    private final Logger log = LoggerFactory.getLogger(AssetassetmbrService.class);
    
    private final AssetassetmbrRepository assetassetmbrRepository;

    private final AssetassetmbrSearchRepository assetassetmbrSearchRepository;

    public AssetassetmbrService(AssetassetmbrRepository assetassetmbrRepository, AssetassetmbrSearchRepository assetassetmbrSearchRepository) {
        this.assetassetmbrRepository = assetassetmbrRepository;
        this.assetassetmbrSearchRepository = assetassetmbrSearchRepository;
    }

    /**
     * Save a assetassetmbr.
     *
     * @param assetassetmbr the entity to save
     * @return the persisted entity
     */
    public Assetassetmbr save(Assetassetmbr assetassetmbr) {
        log.debug("Request to save Assetassetmbr : {}", assetassetmbr);
        Assetassetmbr result = assetassetmbrRepository.save(assetassetmbr);
        assetassetmbrSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the assetassetmbrs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Assetassetmbr> findAll(Pageable pageable) {
        log.debug("Request to get all Assetassetmbrs");
        Page<Assetassetmbr> result = assetassetmbrRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one assetassetmbr by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Assetassetmbr findOne(Long id) {
        log.debug("Request to get Assetassetmbr : {}", id);
        Assetassetmbr assetassetmbr = assetassetmbrRepository.findOne(id);
        return assetassetmbr;
    }

    /**
     *  Delete the  assetassetmbr by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Assetassetmbr : {}", id);
        assetassetmbrRepository.delete(id);
        assetassetmbrSearchRepository.delete(id);
    }

    /**
     * Search for the assetassetmbr corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Assetassetmbr> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Assetassetmbrs for query {}", query);
        Page<Assetassetmbr> result = assetassetmbrSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
