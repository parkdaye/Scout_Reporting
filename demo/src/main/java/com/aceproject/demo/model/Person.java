package com.aceproject.demo.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Person extends DTO {
	private int personId;
	private String name;
}
