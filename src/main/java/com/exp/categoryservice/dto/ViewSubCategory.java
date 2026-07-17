package com.exp.categoryservice.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.exp.categoryservice.enums.Status;

public record ViewSubCategory(UUID subid, UUID catIi , String catname , String subname, String description
		,Status status, OffsetDateTime createon) {

}
