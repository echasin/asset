package com.innvo.repository.search;

import com.innvo.domain.Modelrecordtype;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Modelrecordtype entity.
 */
public interface ModelrecordtypeSearchRepository extends ElasticsearchRepository<Modelrecordtype, Long> {
}
