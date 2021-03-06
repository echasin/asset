package com.innvo.repository;

import com.innvo.domain.Assetassetmbr;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Assetassetmbr entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssetassetmbrRepository extends JpaRepository<Assetassetmbr,Long> {
	
	List<Assetassetmbr> findByModelId(long id);

}
