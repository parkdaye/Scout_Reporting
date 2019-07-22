package com.aceproject.demo.dao.mybatis;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.aceproject.demo.dao.PlayerDao;
import com.aceproject.demo.model.Player;

public class PlayerDaoMybatisTest {

	@Autowired
	PlayerDao playerDao;
	
	@Test
	public void test() {
		List<Player> players = playerDao.getAll();
		
		System.out.println(players.get(0).getTeamCode());
		
		Player player = Player.builder()
				.personId(1)
				.teamCode("test1")
				.build();
		
		int playerId = playerDao.insert(player);
		System.out.println(playerDao.get(playerId).getTeamCode());
		
		player.setTeamCode("test22");
		playerDao.update(player);
		System.out.println(playerDao.get(playerId).getTeamCode());
	}
}
