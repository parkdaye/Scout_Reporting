package com.aceproject.demo.model;

import org.joda.time.DateTime;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Team extends DTO {
	private int teamId;
	private String teamName;
	private DateTime refreshDate;
}
