package com.aceproject.demo.dao.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.aceproject.demo.dao.TeamSrSlotDao;
import com.aceproject.demo.model.TeamSrSlot;
import com.aceproject.demo.model.YN;
import com.aceproject.demo.support.CommonDaoSupport;

@Repository
public class TeamSrSlotDaoMybatis extends CommonDaoSupport implements TeamSrSlotDao {

	@Override
	public List<TeamSrSlot> list(int teamId) {
		return getSqlSession().selectList("com.aceproject.demo.teamSrSlot.selectList", teamId);
	}

	@Override
	public TeamSrSlot get(int teamId, int slotNo) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("teamId", teamId);
		params.put("slotNo", slotNo);

		return getSqlSession().selectOne("com.aceproject.demo.teamSrSlot.select", params);
	}

	@Override
	public void insert(List<TeamSrSlot> teamSrSlots) {
		if (teamSrSlots.isEmpty())
			return;

		teamSrSlots.forEach(s -> getSqlSession().insert("com.aceproject.demo.teamSrSlot.insert", s));
	}

	@Override
	public void update(TeamSrSlot teamSrSlot) {
		getSqlSession().update("com.aceproject.demo.teamSrSlot.update", teamSrSlot);
	}

	@Override
	public void update(List<TeamSrSlot> teamSrSlots) {
		if (teamSrSlots.isEmpty())
			return;
		teamSrSlots.forEach(s -> getSqlSession().update("com.aceproject.demo.teamSrSlot.update", s));
		
	}

}
