package com.innvo.repository;

import com.innvo.domain.Assetrecordtype;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Assetrecordtype entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssetrecordtypeRepository extends JpaRepository<Assetrecordtype,Long> {

}
