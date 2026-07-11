package com.exp.categoryservice.dto;

import com.exp.categoryservice.enums.Status;

public record CategoryRequest(String name, String description, Status status) {

}
