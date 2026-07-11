package com.exp.categoryservice.entity;

import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddtionalInfo {

	
	@Column(name = "exp_created_on")
	private OffsetDateTime createdOn;
	@Column(name = "exp_updated_on")
	private OffsetDateTime updatedOn;
	@Column(name = "exp_created_by")
	private String createdby;
	@Column(name = "exp_updated_by")
	private String updatedby;
	

}
