package com.aceproject.demo.service.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.aceproject.demo.dao.AccountDao;
import com.aceproject.demo.dao.TeamDao;
import com.aceproject.demo.dao.TeamPlayerDao;
import com.aceproject.demo.dao.TeamSrSlotDao;
import com.aceproject.demo.model.Account;
import com.aceproject.demo.model.Team;
import com.aceproject.demo.model.TeamPlayer;
import com.aceproject.demo.model.TeamSrSlot;
import com.aceproject.demo.model.TeamSrSlotView;
import com.aceproject.demo.model.YN;
import com.aceproject.demo.service.ScoutReportingService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ScoutReportingServiceImplTest {

	@Autowired
	private ScoutReportingService scoutReportingService;

	@Autowired
	private TeamDao teamDao;

	@Autowired
	private AccountDao accountDao;

	@Autowired
	private TeamSrSlotDao teamSrSlotDao;

	@Autowired
	private TeamPlayerDao teamPlayerDao;

	private static final int COMMON_CONTRACT_COST = 50;
	private static final int COMMON_REFRESH_COST = 50;
	private static final int TEST_SLOT_NUMBER = 1;
	private static final int OPTION_TEAMCODE_COST = 50;
	private static final int OPTION_COSTUP_COST = 50;
	private static final int OPTION_COST_MIN = 4;
	private static final Logger logger = LoggerFactory.getLogger(ScoutReportingServiceImplTest.class);

	@Test
	public void getSlotTest() {
		int teamId = insertTeam();
		scoutReportingService.refreshFree(teamId);
		List<TeamSrSlotView> teamSrSlotView = scoutReportingService.getSrSlots(teamId);

		System.out.println(teamSrSlotView);

		assertTrue(1 == teamSrSlotView.get(0).getSlotNo());
	}

	@Test
	public void contractTest() {

		int teamId = insertTeam();
		int preAp = accountDao.get(teamId).getAp();

		scoutReportingService.refreshFree(teamId);
		scoutReportingService.contract(teamId, TEST_SLOT_NUMBER);

		// ap 차감 체크
		assertThat(accountDao.get(teamId).getAp(), is(preAp - COMMON_CONTRACT_COST));

		// 슬롯 영입 체크
		TeamSrSlot testSlot = teamSrSlotDao.get(teamId, TEST_SLOT_NUMBER);
		assertThat(testSlot.getContractYN(), is(YN.Y));

		// 팀플레이어에 영입 체크 (teamPlayer에 아무것도 없어야함!!!)
		int playerId = testSlot.getPlayerId();
		TeamPlayer teamPlayer = teamPlayerDao.get(teamId, playerId);
		assertThat(teamPlayer.getPlayerId(), is(playerId));
	}

	@Test
	public void refreshFreeTest() {
		int teamId = insertTeam();

		scoutReportingService.refreshFree(teamId);
		String refreshDate = teamDao.get(teamId).getRefreshDate().toString();

		logger.info(refreshDate);
	}

	@Test
	public void refreshByApTest() {
		int teamId = insertTeam();
		scoutReportingService.getSrSlots(teamId);
		String teamCode = "에이스";
		boolean isCostUp = true;

		int preAp = accountDao.get(teamId).getAp();
		
		scoutReportingService.refreshByAP(teamId, teamCode, isCostUp);
		List<TeamSrSlotView> srSlotViews = scoutReportingService.getSrSlots(teamId);
		assertThat(srSlotViews.get(0).getTeamCode(), is(teamCode));
		assertThat(srSlotViews.get(1).getTeamCode(), is(teamCode));
		assertThat(srSlotViews.get(2).getTeamCode(), is(teamCode));
		assertThat(srSlotViews.get(3).getTeamCode(), is(teamCode));
		
		assertThat(srSlotViews.get(0).getCost(), greaterThan(OPTION_COST_MIN));
		assertThat(srSlotViews.get(1).getCost(), greaterThan(OPTION_COST_MIN));
		assertThat(srSlotViews.get(2).getCost(), greaterThan(OPTION_COST_MIN));
		assertThat(srSlotViews.get(3).getCost(), greaterThan(OPTION_COST_MIN));

		int totalCost = COMMON_REFRESH_COST + OPTION_TEAMCODE_COST + OPTION_COSTUP_COST;
		assertThat(accountDao.get(teamId).getAp(), is(preAp - totalCost));
	}

	private int insertTeam() {
		Team team = Team.builder().teamName("test").build();

		int teamId = teamDao.insert(team);
		Account account = Account.builder().teamId(teamId).ap(1000).build();
		accountDao.insert(account);

		return teamId;
	}
}
