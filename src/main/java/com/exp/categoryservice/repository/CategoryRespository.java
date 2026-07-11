package com.exp.categoryservice.repository;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.exp.categoryservice.dto.CategoryReponse;
import com.exp.categoryservice.entity.Category;

public interface CategoryRespository extends JpaRepository<Category, UUID> {

	@Query("""
			SELECT new com.exp.categoryservice.dto.CategoryReponse(c.id,c.name,c.description,c.status ,
			c.info.createdOn
			)
			FROM Category c
			WHERE
			    (?1 IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', ?1, '%')))
			AND (?2 IS NULL OR LOWER(c.status) LIKE LOWER(CONCAT('%', ?2, '%')))
			AND (?3 IS NULL OR c.info.createdOn >= ?3)
			AND (?4 IS NULL OR c.info.createdOn <= ?4)
			""")
	Page<CategoryReponse> categorySearch(String name, String status,
			OffsetDateTime createdFrom, OffsetDateTime createdto, Pageable pagable);

	

}
