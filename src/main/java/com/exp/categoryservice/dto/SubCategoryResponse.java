package com.exp.categoryservice.dto;

import java.util.UUID;

import com.exp.categoryservice.enums.Status;

public record SubCategoryResponse(UUID id, UUID catId, String name, String description, Status status) {

}
