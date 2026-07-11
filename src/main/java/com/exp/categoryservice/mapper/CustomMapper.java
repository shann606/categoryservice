package com.exp.categoryservice.mapper;

import org.mapstruct.Mapper;

import com.exp.categoryservice.dto.CategoryReponse;
import com.exp.categoryservice.dto.CategoryRequest;
import com.exp.categoryservice.dto.SubCategoryRequest;
import com.exp.categoryservice.dto.SubCategoryResponse;
import com.exp.categoryservice.entity.Category;
import com.exp.categoryservice.entity.SubCategory;

@Mapper(componentModel = "spring")
public interface CustomMapper {

	Category toCatEntity(CategoryRequest req);
	
	CategoryReponse toCatDTP(Category cat);
	
	SubCategory toSubCatEntity(SubCategoryRequest req);
	
	SubCategoryResponse toSubCatDTO(SubCategory cat);

}
