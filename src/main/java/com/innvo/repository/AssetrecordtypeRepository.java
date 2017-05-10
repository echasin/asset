package com.innvo.repository;

import com.innvo.domain.Assetrecordtype;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Assetrecordtype entity.
 */
@SuppressWarnings("unused")
public interface AssetrecordtypeRepository extends JpaRepository<Assetrecordtype,Long> {

}
