package com.exp.categoryservice.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.exp.categoryservice.enums.Status;

public record CategoryReponse(UUID id , String name, String description, Status status, OffsetDateTime createdon) {

}
