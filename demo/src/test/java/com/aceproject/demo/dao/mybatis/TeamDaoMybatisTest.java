package com.aceproject.demo.dao.mybatis;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.aceproject.demo.dao.TeamDao;
import com.aceproject.demo.model.Team;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class TeamDaoMybatisTest {
	
	@Autowired
	private TeamDao teamDao;

	@Test
	@Rollback(true)
	public void test() {
		Team team = Team.builder()
				.teamName("daye")
				.build();

		int teamId = teamDao.insert(team);

		assertThat(teamDao.get(teamId).getTeamName(), is(team.getTeamName()));
		
		//update
		team.setTeamName("jojojo");	
		
		teamDao.updateTeamName(team);
		assertThat(teamDao.get(teamId).getTeamName(), is(team.getTeamName()));
		
		//delete
		teamDao.delete(teamId);
		assertNull(teamDao.get(teamId));
		
	}



}
