package com.aceproject.demo.dao;

import java.util.List;

import com.aceproject.demo.model.TeamPlayer;

public interface TeamPlayerDao {
	
	List<TeamPlayer> list(int teamId);
	
	TeamPlayer get(int teamId, int playerId);
	
	void insert(TeamPlayer teamPlayer);
	
	void update(TeamPlayer teamPlayer);

}
