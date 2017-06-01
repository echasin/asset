package com.innvo.repository;

import com.innvo.domain.Assetjson;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Assetjson entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssetjsonRepository extends JpaRepository<Assetjson,Long> {

}
