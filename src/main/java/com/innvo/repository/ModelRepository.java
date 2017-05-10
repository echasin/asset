package com.innvo.repository;

import com.innvo.domain.Model;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Model entity.
 */
@SuppressWarnings("unused")
public interface ModelRepository extends JpaRepository<Model,Long> {

}
