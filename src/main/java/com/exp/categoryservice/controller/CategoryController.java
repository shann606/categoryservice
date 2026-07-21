package com.exp.categoryservice.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exp.categoryservice.dto.CategoryReponse;
import com.exp.categoryservice.dto.CategoryRequest;
import com.exp.categoryservice.dto.SubCategoryRequest;
import com.exp.categoryservice.dto.SubCategoryResponse;
import com.exp.categoryservice.dto.UpdateSubCategory;
import com.exp.categoryservice.dto.ViewSubCategory;
import com.exp.categoryservice.service.CategoryService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/categories")
@Slf4j
public class CategoryController {

	private CategoryService catService;

	public CategoryController(CategoryService catService) {
		this.catService = catService;
	}

	@PostMapping
	public ResponseEntity<CategoryReponse> createCategory(@RequestBody CategoryRequest req,
			@RequestHeader(name = "X-Username") String createdBy) {
		log.info("logged in user id ::" + createdBy);

		return new ResponseEntity<CategoryReponse>(catService.createCategory(req, createdBy), HttpStatus.CREATED);

	}

	@PostMapping("/{id}/subcategory")
	public ResponseEntity<SubCategoryResponse> createSubCategory(@PathVariable UUID id,
			@RequestBody SubCategoryRequest req, @RequestHeader(name = "X-Username") String createdBy) {
		log.info("logged in user id ::" + createdBy);

		return new ResponseEntity<SubCategoryResponse>(catService.createSubCategory(id, req, createdBy), HttpStatus.OK);

	}

	@GetMapping("/{id}")
	public ResponseEntity<CategoryReponse> getCategory(@PathVariable UUID id) {

		return new ResponseEntity<CategoryReponse>(catService.getCategory(id), HttpStatus.ACCEPTED);

	}

	@PutMapping("/{id}")
	public ResponseEntity<CategoryReponse> updateCategory(@PathVariable UUID id, @RequestBody CategoryRequest req,
			@RequestHeader(name = "X-Username") String updatedBy) {

		return new ResponseEntity<CategoryReponse>(catService.updateCategory(id, req, updatedBy), HttpStatus.OK);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Page<CategoryReponse>> deleteCategory(@PathVariable UUID id) {

		return new ResponseEntity<Page<CategoryReponse>>(catService.deleteCategory(id), HttpStatus.OK);

	}

	@GetMapping("/search")
	public ResponseEntity<Page<CategoryReponse>> categorySearch(@RequestParam(required = false) String name,
			@RequestParam(required = false) String status, @RequestParam(required = false) String fromdate,
			@RequestParam(required = false) String todate, @RequestParam(required = false) int pageNo) {

		log.info("getting the request ::" + name + "--" + status + "----" + fromdate + "---" + todate);

		return new ResponseEntity<Page<CategoryReponse>>(
				catService.categorySearch(name, status, fromdate, todate, pageNo), HttpStatus.OK);

	}

	@GetMapping("{categoryId}/subcategories")
	public ResponseEntity<Page<ViewSubCategory>> subCategories(@PathVariable UUID categoryId, int pageNo) {

		log.info("getting category id to process associated sub cats" + categoryId);

		return new ResponseEntity<Page<ViewSubCategory>>(catService.viewSubCategories(categoryId, pageNo),
				HttpStatus.OK);

	}

	@PutMapping("/subcategories/{subcategoryId}")
	public ResponseEntity<UpdateSubCategory> updateSubCategory(@PathVariable UUID subcategoryId,
			@RequestBody UpdateSubCategory subCategory, @RequestHeader(name = "X-Username") String updatedBy) {
		log.info("updating subcategory");

		return new ResponseEntity<UpdateSubCategory>(
				catService.updateSubCategory(subcategoryId, subCategory, updatedBy), HttpStatus.OK);

	}

	@GetMapping("/subcategories/{subcategoryId}")
	public ResponseEntity<SubCategoryResponse> getSubCategory(@PathVariable UUID subcategoryId) {

		log.info("Getting indiviual sub-category data" + subcategoryId);

		return new ResponseEntity<SubCategoryResponse>(catService.getSubCategory(subcategoryId), HttpStatus.OK);

	}

	@DeleteMapping("/subcategories/{subCategoryId}")
	public ResponseEntity<Page<ViewSubCategory>> deleteSubCategory(@PathVariable UUID subCategoryId,
			@RequestParam UUID categoryId) {

		return new ResponseEntity<Page<ViewSubCategory>>(catService.deleteSubCategory(categoryId, subCategoryId),
				HttpStatus.OK);

	}

}
