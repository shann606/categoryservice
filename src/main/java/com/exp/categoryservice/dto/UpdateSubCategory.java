package com.exp.categoryservice.dto;

import java.util.UUID;

import com.exp.categoryservice.enums.Status;

public record UpdateSubCategory(UUID subId, String name, Status status, String description) {

}
