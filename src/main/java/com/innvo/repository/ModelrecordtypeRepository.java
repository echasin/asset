package com.innvo.repository;

import com.innvo.domain.Modelrecordtype;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Modelrecordtype entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModelrecordtypeRepository extends JpaRepository<Modelrecordtype,Long> {

}
