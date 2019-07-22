package com.aceproject.demo.dao.mybatis;

import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.aceproject.demo.dao.TeamPlayerDao;
import com.aceproject.demo.model.TeamPlayer;
import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class TeamPlayerDaoMybatisTest {

	@Autowired
	private TeamPlayerDao teamPlayerDao;
	
	@Test
	@Rollback(true)
	public void test() {
		TeamPlayer teamPlayer = TeamPlayer.builder()
				.teamId(1)
				.playerId(1)
				.exp(0)
				.build();
		
		teamPlayerDao.insert(teamPlayer);
		assertThat(teamPlayer.getLevel(), is(teamPlayerDao.get(1, 1).getLevel()));
		
		
		teamPlayer.setLevel(2);
		teamPlayerDao.update(teamPlayer);
		assertThat(teamPlayer.getLevel(), is(teamPlayerDao.get(1, 1).getLevel()));
	}

}
