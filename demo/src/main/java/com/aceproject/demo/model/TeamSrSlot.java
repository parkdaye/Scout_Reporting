package com.aceproject.demo.model;

import lombok.Data;

//@Builder
@Data
public class TeamSrSlot extends DTO {
	private int teamId;
	private int slotNo;
	private int playerId;
	private YN contractYN;

	public TeamSrSlot(int teamId, int slotNo, int playerId) {
		super();
		this.teamId = teamId;
		this.slotNo = slotNo;
		this.playerId = playerId;
		this.contractYN = YN.N;
	}

}
