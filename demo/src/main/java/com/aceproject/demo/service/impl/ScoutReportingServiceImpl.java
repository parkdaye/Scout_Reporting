package com.aceproject.demo.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aceproject.demo.dao.AccountDao;
import com.aceproject.demo.dao.PersonDao;
import com.aceproject.demo.dao.PlayerDao;
import com.aceproject.demo.dao.TeamDao;
import com.aceproject.demo.dao.TeamPlayerDao;
import com.aceproject.demo.dao.TeamSrSlotDao;
import com.aceproject.demo.exception.AlreadyContractedSlotException;
import com.aceproject.demo.exception.NotEnoughApException;
import com.aceproject.demo.exception.NotEnoughRandomPlayerException;
import com.aceproject.demo.model.Account;
import com.aceproject.demo.model.Person;
import com.aceproject.demo.model.Player;
import com.aceproject.demo.model.Team;
import com.aceproject.demo.model.TeamPlayer;
import com.aceproject.demo.model.TeamSrSlot;
import com.aceproject.demo.model.TeamSrSlotView;
import com.aceproject.demo.model.YN;
import com.aceproject.demo.service.ScoutReportingService;

@Service
public class ScoutReportingServiceImpl implements ScoutReportingService {

	@Autowired
	private TeamSrSlotDao teamSrSlotDao;

	@Autowired
	private TeamDao teamDao;

	@Autowired
	private AccountDao accountDao;

	@Autowired
	private TeamPlayerDao teamPlayerDao;

	@Autowired
	private PlayerDao playerDao;

	@Autowired
	private PersonDao personDao;

	private static final int COMMON_CONTRACT_AP = 50;
	private static final int COMMON_REFRESH_AP = 50;
	private static final int OPTION_TEAMCODE_AP = 50;
	private static final int OPTION_COSTUP_AP = 50;
	private static final int OPTION_COST_MIN = 4;
	private static final int MAX_SLOT_NO = 4;

	@Transactional
	@Override
	public List<TeamSrSlotView> getSrSlots(int teamId) {

		// 최초 진입시 빈정보로 초기화
		List<TeamSrSlot> teamSrSlotList = teamSrSlotDao.list(teamId);
		
		if (teamSrSlotList.isEmpty()) {
			initSlot(teamId);
		}

		// 마지막무료갱신으로부터 1시간 지났으면 자동갱신
		Team team = teamDao.get(teamId);
		DateTime refreshDate = team.getRefreshDate();
		if (isOverHour(refreshDate)) {
			refreshFree(teamId);
		}

		// teamslotview에 저장된 list를 가져와 view객체로 반환
		Map<Integer, Player> players = playerDao.getAll().stream()
				.collect(Collectors.toMap(p -> p.getPlayerId(), p -> p));
		Map<Integer, Person> persons = personDao.getAll().stream()
				.collect(Collectors.toMap(p -> p.getPersonId(), p -> p));
		
		List<TeamSrSlotView> srSlotViewList = teamSrSlotList.stream()
				.map(s -> {
					Player player = players.get(s.getPlayerId()); 
					return new TeamSrSlotView(s, player, persons.get(player.getPersonId()));
				}).collect(Collectors.toList());

		return srSlotViewList;
	}

	@Transactional
	@Override
	public void contract(int teamId, int slotNo) throws NotEnoughApException {

		withdrawAP(teamId, COMMON_CONTRACT_AP);

		// 슬롯 상태를 업데이트(이미 영입한 슬롯이면 예외를 던짐)
		TeamSrSlot teamSrSlot = teamSrSlotDao.get(teamId, slotNo);
		if (teamSrSlot.getContractYN() == YN.Y) {
			throw new AlreadyContractedSlotException("이미 영입한 슬롯입니다.");
		}
		teamSrSlot.setContractYN(YN.Y);
		teamSrSlotDao.update(teamSrSlot);

		// 팀플레이어에 추가.
		int playerId = teamSrSlot.getPlayerId();
		addTeamPlayer(teamId, playerId);
	}

	@Transactional
	@Override
	public void refreshByAP(int teamId, String teamCode, boolean isCostUp) throws NotEnoughApException {

		List<Player> playerList = playerDao.getAll();
		int totalOptionCost = COMMON_REFRESH_AP;
		
		Predicate<Player> teamCodeOption = s ->  teamCode != null && s.getTeamCode().equals(teamCode);
		Predicate<Player> isCostUpOption = s -> isCostUp && s.getCost() >= OPTION_COST_MIN;
		Predicate<Player> totalFilter = teamCodeOption.and(isCostUpOption);
		
		playerList = playerList.stream().filter(totalFilter).collect(Collectors.toList());
		
		if(teamCode != null)
			totalOptionCost += OPTION_TEAMCODE_AP;

		if(isCostUp) {
			totalOptionCost += OPTION_COSTUP_AP;
		}
				
		
		//롤백되는 로그 갯수 순서에 따라 효율성이 나뉨
		withdrawAP(teamId, totalOptionCost);
		refresh(teamId, playerList);
	}

	@Transactional
	@Override
	public void refreshFree(int teamId) {
		updateRefreshDate(teamId);
		refresh(teamId);
	}

	public void initSlot(int teamId) {
		updateRefreshDate(teamId);
		List<TeamSrSlot> teamSrSlots = selectRandomSlotPlayers(teamId, playerDao.getAll());
		teamSrSlotDao.insert(teamSrSlots);
	}

	private void refresh(int teamId) {
		List<TeamSrSlot> teamSrSlots = selectRandomSlotPlayers(teamId, playerDao.getAll());
		teamSrSlotDao.update(teamSrSlots);
	}

	private void refresh(int teamId, List<Player> playerList) {
		List<TeamSrSlot> teamSrSlots = selectRandomSlotPlayers(teamId, playerList);
		teamSrSlotDao.update(teamSrSlots);
	}
	
	private void updateRefreshDate(int teamId) {
		Team team = teamDao.get(teamId);
		teamDao.updateRefreshDate(team);
	}

	private List<TeamSrSlot> selectRandomSlotPlayers(int teamId, List<Player> playerList) {
		
		if(playerList.size() < MAX_SLOT_NO)
			throw new NotEnoughRandomPlayerException("옵션에 맞는 선수가 충분하지 않습니다.");

		// 반환객체 생성
		List<TeamSrSlot> result = new ArrayList<>();

		// 최대 슬롯 개수만큼 랜덤하게 선수 선정
		Random random = new Random();
		Set<Integer> selectedPersonIds = new HashSet<>();
		List<Integer> selectedPlayerIds = new ArrayList<Integer>();
		while (selectedPlayerIds.size() < MAX_SLOT_NO) {
			int randomIdx = random.nextInt(playerList.size());

			// 중복 person 확인
			Player player = playerList.get(randomIdx);
			if (selectedPersonIds.contains(player.getPersonId())) {
				continue;
			} else {
				selectedPersonIds.add(player.getPersonId());
				selectedPlayerIds.add(player.getPlayerId());
			}
		}

		// teamslot 객체 생성해서 반환객체에 add
		for (int slotNo = 1; slotNo <= MAX_SLOT_NO; slotNo++) {
			int playerId = selectedPlayerIds.get(slotNo - 1);

			TeamSrSlot teamSrSlot = new TeamSrSlot(teamId, slotNo, playerId);
			result.add(teamSrSlot);
		}

		return result;

	}

	private void withdrawAP(int teamId, int apCost) {
		Account account = accountDao.get(teamId);
		int currentAP = account.getAp();

		if (currentAP < apCost)
			throw new NotEnoughApException("AP가 부족합니다.");

		account.setAp(currentAP - apCost);
		accountDao.update(account);
	}

	private void addTeamPlayer(int teamId, int playerId) {
		// teamplayer객체를 갖고와서 존재하지 않는 객체이면 새로 만들고 존재하면 레벨을 올림
		TeamPlayer existedTeamPlayer = teamPlayerDao.get(teamId, playerId);

		if (existedTeamPlayer == null) {
			TeamPlayer teamPlayer = TeamPlayer.builder().teamId(teamId).playerId(playerId).build();
			teamPlayerDao.insert(teamPlayer);
		} else {
			int currentLevel = existedTeamPlayer.getLevel();
			existedTeamPlayer.setLevel(currentLevel + 1);
			teamPlayerDao.update(existedTeamPlayer);
		}
	}

	private boolean isOverHour(DateTime refreshDate) {
		DateTime now = DateTime.now();
		return now.isAfter(refreshDate.plusHours(1));
	}

}
