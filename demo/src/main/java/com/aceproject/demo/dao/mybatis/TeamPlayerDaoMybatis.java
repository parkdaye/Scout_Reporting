package com.aceproject.demo.dao.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.aceproject.demo.dao.TeamPlayerDao;
import com.aceproject.demo.model.TeamPlayer;
import com.aceproject.demo.support.CommonDaoSupport;

@Repository
public class TeamPlayerDaoMybatis extends CommonDaoSupport implements TeamPlayerDao {

	@Override
	public List<TeamPlayer> list(int teamId) {
		return getSqlSession().selectList("com.aceproject.demo.dao.teamPlayer.selectList", teamId);
	}

	@Override
	public TeamPlayer get(int teamId, int playerId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("teamId", teamId);
		params.put("playerId", playerId);
		
		return getSqlSession().selectOne("com.aceproject.demo.dao.teamPlayer.select", params);
	}

	@Override
	public void insert(TeamPlayer teamPlayer) {
		getSqlSession().insert("com.aceproject.demo.dao.teamPlayer.insert", teamPlayer);
		
	}

	@Override
	public void update(TeamPlayer teamPlayer) {
		getSqlSession().update("com.aceproject.demo.dao.teamPlayer.update", teamPlayer);
		
	}


}
