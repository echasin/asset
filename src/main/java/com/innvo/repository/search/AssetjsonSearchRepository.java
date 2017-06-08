package com.innvo.repository.search;

import com.innvo.domain.Assetjson;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Assetjson entity.
 */
public interface AssetjsonSearchRepository extends ElasticsearchRepository<Assetjson, Long> {
}
