package com.aceproject.demo.dao.mybatis;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.aceproject.demo.dao.TeamSrSlotDao;
import com.aceproject.demo.model.TeamSrSlot;
import com.aceproject.demo.model.YN;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class TeamSrSlotDaoMybatisTest {

	@Autowired
	private TeamSrSlotDao teamSrSlotDao;

	@Test
	@Rollback(true)
	public void test() {

		List<TeamSrSlot> slotList = new ArrayList<TeamSrSlot>();

		for (int slotNo = 1; slotNo <= 4; slotNo++) {
			TeamSrSlot slot = new TeamSrSlot(1, slotNo, 1);
			slotList.add(slot);
		}

		teamSrSlotDao.insert(slotList);

		// select
		List<TeamSrSlot> selectedSlot = teamSrSlotDao.list(1);
		assertThat(selectedSlot.get(1).getPlayerId(), is(1));

		slotList.get(0).setContractYN(YN.Y);
		// update
		teamSrSlotDao.update(slotList);
		assertThat(YN.Y, is(selectedSlot.get(0).getContractYN()));

	}

	@Test
	public void test_list() {
		int teamId = 10000000;

		List<TeamSrSlot> srSlots = teamSrSlotDao.list(teamId);
//		Assert.assertNull(srSlots);
		Assert.assertTrue(srSlots.isEmpty());
	}

}
