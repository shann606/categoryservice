package com.exp.categoryservice.exception;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5783346277204948113L;
	
	private String status;
	private String reason;
	

}
