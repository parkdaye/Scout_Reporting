package com.aceproject.demo.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TeamPlayer extends DTO {
	private int teamId;
	private int playerId;
	private int level;
	private int exp;
}
