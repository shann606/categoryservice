package com.exp.categoryservice.service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.exp.categoryservice.dto.CategoriesAndSubCategories;
import com.exp.categoryservice.dto.CategoryReponse;
import com.exp.categoryservice.dto.CategoryRequest;
import com.exp.categoryservice.dto.SubCategoryRequest;
import com.exp.categoryservice.dto.SubCategoryResponse;
import com.exp.categoryservice.dto.UpdateSubCategory;
import com.exp.categoryservice.dto.ViewSubCategory;
import com.exp.categoryservice.entity.AddtionalInfo;
import com.exp.categoryservice.entity.Category;
import com.exp.categoryservice.entity.SubCategory;
import com.exp.categoryservice.exception.CategoryNotFoundException;
import com.exp.categoryservice.mapper.CustomMapper;
import com.exp.categoryservice.repository.CategoryRespository;
import com.exp.categoryservice.repository.SubCategoryRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryService {

	private CategoryRespository catRepo;
	private SubCategoryRepository subCatRepo;
	private CustomMapper customMapper;

	public CategoryService(CategoryRespository catRepo, SubCategoryRepository subCatRepo, CustomMapper customMapper) {
		this.catRepo = catRepo;
		this.subCatRepo = subCatRepo;
		this.customMapper = customMapper;
	}

	@Transactional
	public CategoryReponse createCategory(CategoryRequest req, String createdBy) {

		log.info("Getting the category creation request" + req.toString());

		Category cat = customMapper.toCatEntity(req);

		cat.setInfo(getAddtionalInfo(createdBy));

		Category cat1 = catRepo.saveAndFlush(cat);

		return customMapper.toCatDTP(cat1);

	}

	@Transactional
	public SubCategoryResponse createSubCategory(UUID id, SubCategoryRequest req, String createdBy) {
		log.info("Getting the sub category creation request" + req.toString());
		SubCategory sub = customMapper.toSubCatEntity(req);
		sub.setCatId(id);
		sub.setInfo(getAddtionalInfo(createdBy));
		SubCategory sub1 = subCatRepo.saveAndFlush(sub);

		return customMapper.toSubCatDTO(sub1);
	}

	private AddtionalInfo getAddtionalInfo(String createdBy) {

		return AddtionalInfo.builder().createdby(createdBy).createdOn(OffsetDateTime.now(Clock.systemUTC())).build();

	}

	private AddtionalInfo getAddtionalInfoUpdate(String updatedBy) {

		return AddtionalInfo.builder().updatedby(updatedBy).createdOn(OffsetDateTime.now(Clock.systemUTC())).build();

	}

	private OffsetDateTime dateConverter(String date) {

		LocalDate localDate = LocalDate.parse(date);

		ZoneId dbZone = ZoneId.of("Asia/Kolkata");
		ZoneOffset dbOffset = dbZone.getRules().getOffset(localDate.atStartOfDay());

		return localDate.atStartOfDay().atOffset(dbOffset);

	}

	@Transactional(readOnly = true)
	public Page<CategoryReponse> categorySearch(String name, String status, String fromDate, String toDate,
			int pageNo) {

		OffsetDateTime createdF = null, createdT = null;

		if (StringUtils.hasText(fromDate)) {
			createdF = dateConverter(fromDate);
		}

		if (StringUtils.hasText(toDate)) {
			createdT = dateConverter(toDate);
		}
		log.info("coming here to check");
		return catRepo.categorySearch(name, status, createdF, createdT, PageRequest.of(pageNo, 6));

	}

	public CategoryReponse getCategory(UUID id) {

		Category cat = catRepo.findById(id)
				.orElseThrow(() -> new CategoryNotFoundException("category not found in the system."));

		return customMapper.toCatDTP(cat);
	}

	public Page<ViewSubCategory> viewSubCategories(UUID catId, int pageNo) {

		return subCatRepo.viewSubCategories(catId, PageRequest.of(pageNo, 6));
	}

	@Transactional(timeout = 10)
	public UpdateSubCategory updateSubCategory(UUID subid, UpdateSubCategory sub, String updatedBy) {

		SubCategory subCat = subCatRepo.findById(subid)
				.orElseThrow(() -> new CategoryNotFoundException("Sub-Category not found in the system."));

		Optional.ofNullable(sub.name()).filter(StringUtils::hasText).ifPresent(subCat::setName);
		Optional.ofNullable(sub.status()).ifPresent(subCat::setStatus);
		Optional.ofNullable(sub.description()).filter(StringUtils::hasText).ifPresent(subCat::setDescription);

		subCat.getInfo().setUpdatedby(updatedBy);
		subCat.getInfo().setUpdatedOn(OffsetDateTime.now(Clock.systemUTC()));

		subCat = subCatRepo.save(subCat);

		return new UpdateSubCategory(subCat.getId(), subCat.getName(), subCat.getStatus(), subCat.getDescription());
	}

	public SubCategoryResponse getSubCategory(UUID subId) {

		SubCategory sub = subCatRepo.findById(subId)
				.orElseThrow(() -> new CategoryNotFoundException("Sub-Category not found in the system."));

		return new SubCategoryResponse(sub.getId(), sub.getCatId(), sub.getName(), sub.getDescription(),
				sub.getStatus());
	}

	@Transactional(timeout = 10)
	public CategoryReponse updateCategory(UUID id, CategoryRequest req, String updatedBy) {

		Category category = catRepo.findById(id)
				.orElseThrow(() -> new CategoryNotFoundException("Category not found in the system"));

		Optional.ofNullable(req.name()).filter(StringUtils::hasText).ifPresent(category::setName);
		Optional.ofNullable(req.status()).ifPresent(category::setStatus);
		Optional.ofNullable(req.description()).filter(StringUtils::hasText).ifPresent(category::setDescription);

		category.getInfo().setUpdatedby(updatedBy);
		category.getInfo().setUpdatedOn(OffsetDateTime.now(Clock.systemUTC()));

		category = catRepo.save(category);

		return new CategoryReponse(category.getId(), category.getName(), category.getDescription(),
				category.getStatus(), category.getInfo().getUpdatedOn());
	}

	@Transactional
	public Page<CategoryReponse> deleteCategory(UUID id) {

		subCatRepo.deleteSubCategories(id);
		catRepo.deleteById(id);

		return categorySearch(null, null, null, null, 0);
	}

	@Transactional
	public Page<ViewSubCategory> deleteSubCategory(UUID categoryId, UUID subCategoryId) {
		subCatRepo.deleteById(subCategoryId);
		return viewSubCategories(categoryId, 0);
	}

	public List<CategoriesAndSubCategories> getCategories() {

		return catRepo.findAll().stream().map(category -> {

			CategoriesAndSubCategories cat = new CategoriesAndSubCategories(category.getId(), category.getName());

			return cat;
		}).toList();
	}

	public List<CategoriesAndSubCategories> getSubCategories(UUID categoryId) {

		return subCatRepo.getSubCategories(categoryId);
	}

}
