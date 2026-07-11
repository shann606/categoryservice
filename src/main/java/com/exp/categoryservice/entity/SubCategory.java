package com.exp.categoryservice.entity;

import java.util.UUID;

import com.exp.categoryservice.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "exp_sub_category")
public class SubCategory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	@Column(name = "exp_cat_id", nullable = false)
	private UUID catId;
	
	@Column(name = "exp_sub_category_name", nullable = false)
	private String name;
	@Column(name = "exp_description")
	private String description;
	@Enumerated(EnumType.STRING)
	@Column(name = "exp_status")
	private Status status;
	@Embedded
	private AddtionalInfo info;

}
