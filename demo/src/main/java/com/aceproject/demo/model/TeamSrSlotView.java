package com.aceproject.demo.model;

import lombok.ToString;

@ToString
public class TeamSrSlotView {

	private TeamSrSlot teamSrSlot;
	private Player player;
	private Person person;

	public TeamSrSlotView(TeamSrSlot teamSrSlot, Player player, Person person) {
		super();
		this.teamSrSlot = teamSrSlot;
		this.player = player;
		this.person = person;
	}

	public int getSlotNo() {
		return teamSrSlot.getSlotNo();
	}

	public YN getContractYN() {
		return teamSrSlot.getContractYN();
	}

	public String getTeamCode() {
		return player.getTeamCode();
	}

	public int getPlayerId() {
		return player.getPlayerId();
	}

	public int getCost() {
		return player.getCost();
	}

	public String getName() {
		return person.getName();
	}
}
