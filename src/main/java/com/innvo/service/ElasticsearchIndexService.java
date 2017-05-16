package com.innvo.service;

import com.codahale.metrics.annotation.Timed;
import com.innvo.domain.*;
import com.innvo.repository.*;
import com.innvo.repository.search.*;
import org.elasticsearch.indices.IndexAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;

@Service
public class ElasticsearchIndexService {

    private final Logger log = LoggerFactory.getLogger(ElasticsearchIndexService.class);

    private final AssetRepository assetRepository;

    private final AssetSearchRepository assetSearchRepository;

    private final AssetassetmbrRepository assetassetmbrRepository;

    private final AssetassetmbrSearchRepository assetassetmbrSearchRepository;

    private final AssetassetmbrrecordtypeRepository assetassetmbrrecordtypeRepository;

    private final AssetassetmbrrecordtypeSearchRepository assetassetmbrrecordtypeSearchRepository;

    private final AssetrecordtypeRepository assetrecordtypeRepository;

    private final AssetrecordtypeSearchRepository assetrecordtypeSearchRepository;

    private final ModelRepository modelRepository;

    private final ModelSearchRepository modelSearchRepository;

    private final ModelrecordtypeRepository modelrecordtypeRepository;

    private final ModelrecordtypeSearchRepository modelrecordtypeSearchRepository;

    private final ElasticsearchTemplate elasticsearchTemplate;

    public ElasticsearchIndexService(
        AssetRepository assetRepository,
        AssetSearchRepository assetSearchRepository,
        AssetassetmbrRepository assetassetmbrRepository,
        AssetassetmbrSearchRepository assetassetmbrSearchRepository,
        AssetassetmbrrecordtypeRepository assetassetmbrrecordtypeRepository,
        AssetassetmbrrecordtypeSearchRepository assetassetmbrrecordtypeSearchRepository,
        AssetrecordtypeRepository assetrecordtypeRepository,
        AssetrecordtypeSearchRepository assetrecordtypeSearchRepository,
        ModelRepository modelRepository,
        ModelSearchRepository modelSearchRepository,
        ModelrecordtypeRepository modelrecordtypeRepository,
        ModelrecordtypeSearchRepository modelrecordtypeSearchRepository,
        ElasticsearchTemplate elasticsearchTemplate) {
        this.assetRepository = assetRepository;
        this.assetSearchRepository = assetSearchRepository;
        this.assetassetmbrRepository = assetassetmbrRepository;
        this.assetassetmbrSearchRepository = assetassetmbrSearchRepository;
        this.assetassetmbrrecordtypeRepository = assetassetmbrrecordtypeRepository;
        this.assetassetmbrrecordtypeSearchRepository = assetassetmbrrecordtypeSearchRepository;
        this.assetrecordtypeRepository = assetrecordtypeRepository;
        this.assetrecordtypeSearchRepository = assetrecordtypeSearchRepository;
        this.modelRepository = modelRepository;
        this.modelSearchRepository = modelSearchRepository;
        this.modelrecordtypeRepository = modelrecordtypeRepository;
        this.modelrecordtypeSearchRepository = modelrecordtypeSearchRepository;
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Async
    @Timed
    public void reindexAll() {
        reindexForClass(Asset.class, assetRepository, assetSearchRepository);
        reindexForClass(Assetassetmbr.class, assetassetmbrRepository, assetassetmbrSearchRepository);
        reindexForClass(Assetassetmbrrecordtype.class, assetassetmbrrecordtypeRepository, assetassetmbrrecordtypeSearchRepository);
        reindexForClass(Assetrecordtype.class, assetrecordtypeRepository, assetrecordtypeSearchRepository);
        reindexForClass(Model.class, modelRepository, modelSearchRepository);
        reindexForClass(Modelrecordtype.class, modelrecordtypeRepository, modelrecordtypeSearchRepository);

        log.info("Elasticsearch: Successfully performed reindexing");
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    private <T, ID extends Serializable> void reindexForClass(Class<T> entityClass, JpaRepository<T, ID> jpaRepository,
                                                              ElasticsearchRepository<T, ID> elasticsearchRepository) {
        elasticsearchTemplate.deleteIndex(entityClass);
        try {
            elasticsearchTemplate.createIndex(entityClass);
        } catch (IndexAlreadyExistsException e) {
            // Do nothing. Index was already concurrently recreated by some other service.
        }
        elasticsearchTemplate.putMapping(entityClass);
        if (jpaRepository.count() > 0) {
            try {
                Method m = jpaRepository.getClass().getMethod("findAllWithEagerRelationships");
                elasticsearchRepository.save((List<T>) m.invoke(jpaRepository));
            } catch (Exception e) {
                elasticsearchRepository.save(jpaRepository.findAll());
            }
        }
        log.info("Elasticsearch: Indexed all rows for " + entityClass.getSimpleName());
    }
}
