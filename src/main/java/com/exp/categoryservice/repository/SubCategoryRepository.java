package com.exp.categoryservice.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.exp.categoryservice.dto.ViewSubCategory;
import com.exp.categoryservice.entity.SubCategory;

public interface SubCategoryRepository extends JpaRepository<SubCategory, UUID> {
	@Query("""
		    SELECT new com.exp.categoryservice.dto.ViewSubCategory(
		        s.id,
		        s.catId,
		        c.name,
		        s.name,
		        s.description,
		        s.status,
		        s.info.createdOn
		    )
		    FROM Category c
		    INNER JOIN SubCategory s
		        ON c.id = s.catId
		    WHERE c.id = ?1
		    ORDER BY c.info.createdOn DESC
		    """)
		Page<ViewSubCategory> viewSubCategories(UUID catId, Pageable pageable);
	
	
	
		@Modifying
		@Query("""
				delete SubCategory where catId = ?1
				""")
		void deleteSubCategories(UUID categoryId);
}
