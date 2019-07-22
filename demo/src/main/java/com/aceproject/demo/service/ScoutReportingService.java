package com.aceproject.demo.service;

import java.util.List;

import com.aceproject.demo.exception.NotEnoughApException;
import com.aceproject.demo.model.TeamSrSlotView;

public interface ScoutReportingService {

	/**
	 * 클라이언트에게 반환할 슬롯뷰 객체를 만든다 
	 * @param teamId
	 * @return List<TeamSrSlotView>
	 */
	List<TeamSrSlotView> getSrSlots(int teamId);
	
	/**
	 * ap를 차감하고 선수를 영입한다
	 * @param teamId
	 * @param slotNo
	 * @throws NotEnoughApException
	 */
	void contract(int teamId, int slotNo) throws NotEnoughApException;

	/**
	 * ap를 차감해서 슬롯 정보를 새로고침한다.
	 * @param teamId
	 * @throws NotEnoughApException
	 */
	void refreshByAP(int teamId, String teamCode, boolean isCostUp) throws NotEnoughApException;

	/**
	 * 무료로 슬롯 정보를 새로고침한다.
	 * @param teamId
	 */
	void refreshFree(int teamId);
}
