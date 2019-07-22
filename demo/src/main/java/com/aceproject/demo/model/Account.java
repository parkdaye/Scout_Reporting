package com.aceproject.demo.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Account extends DTO {
	private int teamId;
	private int ap;
}
