package com.innvo.repository.search;

import com.innvo.domain.Assetassetmbr;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Assetassetmbr entity.
 */
public interface AssetassetmbrSearchRepository extends ElasticsearchRepository<Assetassetmbr, Long> {
}
