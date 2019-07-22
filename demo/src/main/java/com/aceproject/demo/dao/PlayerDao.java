package com.aceproject.demo.dao;

import java.util.List;

import com.aceproject.demo.model.Player;

public interface PlayerDao {

	Player get(int playerId);
		
	int insert(Player player);
		
	void update(Player player);
	
	List<Player> getAll();
	
}
