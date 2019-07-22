package com.aceproject.demo.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Player extends DTO {
	private int playerId;	
	private int personId;	
	private String teamCode;	
	private int cost;	
}
