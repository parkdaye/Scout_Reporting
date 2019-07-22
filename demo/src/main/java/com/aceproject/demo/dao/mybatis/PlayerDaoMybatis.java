package com.aceproject.demo.dao.mybatis;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.aceproject.demo.dao.PlayerDao;
import com.aceproject.demo.model.Person;
import com.aceproject.demo.model.Player;
import com.aceproject.demo.support.CommonDaoSupport;

@Repository
public class PlayerDaoMybatis extends CommonDaoSupport implements PlayerDao{
	
	@Override
	public List<Player> getAll() {
		return getSqlSession().selectList("com.aceproject.demo.dao.player.selectList");
	}

	@Override
	public Player get(int playerId) {
		return getSqlSession().selectOne("com.aceproject.demo.dao.player.select", playerId);
	}

	@Override
	public int insert(Player player) {
		return getSqlSession().insert("com.aceproject.demo.dao.player.insert", player);
	}

	@Override
	public void update(Player player) {
		getSqlSession().update("com.aceproject.demo.dao.player.update", player);
		
	}
}
