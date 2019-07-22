package com.aceproject.demo.dao;

import com.aceproject.demo.model.Team;

public interface TeamDao {

	Team get(int teamId);

	int insert(Team team);

	void updateTeamName(Team team);
	
	void updateRefreshDate(Team team);

	void delete(int teamId);
}
