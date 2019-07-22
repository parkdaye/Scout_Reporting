package com.aceproject.demo.dao.mybatis;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.aceproject.demo.dao.TeamDao;
import com.aceproject.demo.model.Team;
import com.aceproject.demo.support.CommonDaoSupport;

@Repository
public class TeamDaoMybatis extends CommonDaoSupport implements TeamDao {

	@Override
	public Team get(int teamId) {
		return getSqlSession().selectOne("com.aceproject.demo.dao.team.select", teamId);
	}

	@Override
	public int insert(Team team) {
		return getSqlSession().insert("com.aceproject.demo.dao.team.insert", team);
	}

	@Override
	public void updateTeamName(Team team) {
		getSqlSession().update("com.aceproject.demo.dao.team.updateTeamName", team);
	}
	
	@Override
	public void updateRefreshDate(Team team) {
		getSqlSession().update("com.aceproject.demo.dao.team.updateRefreshDate", team);
	}

	@Override
	public void delete(int teamId) {
		getSqlSession().delete("com.aceproject.demo.dao.team.delete", teamId);
	}
	

}
