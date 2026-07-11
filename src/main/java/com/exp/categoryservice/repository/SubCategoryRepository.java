package com.exp.categoryservice.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exp.categoryservice.entity.SubCategory;


public interface SubCategoryRepository extends JpaRepository<SubCategory, UUID> {

}
