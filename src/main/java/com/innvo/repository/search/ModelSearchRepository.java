package com.innvo.repository.search;

import com.innvo.domain.Model;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Model entity.
 */
public interface ModelSearchRepository extends ElasticsearchRepository<Model, Long> {
}
