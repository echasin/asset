package com.innvo.repository.search;

import com.innvo.domain.Assetrecordtype;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Assetrecordtype entity.
 */
public interface AssetrecordtypeSearchRepository extends ElasticsearchRepository<Assetrecordtype, Long> {
}
