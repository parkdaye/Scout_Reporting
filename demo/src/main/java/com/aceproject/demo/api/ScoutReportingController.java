package com.aceproject.demo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aceproject.demo.exception.NotEnoughApException;
import com.aceproject.demo.model.TeamSrSlotView;
import com.aceproject.demo.service.ScoutReportingService;

@RestController("/teams")
public class ScoutReportingController {

	@Autowired
	private ScoutReportingService scoutReportingService;
	
	@GetMapping("/{teamId}/srslots")
	public List<TeamSrSlotView> getTeamSrSlotViews(@PathVariable int teamId) {
		return scoutReportingService.getSrSlots(teamId);
	}
	
	@PostMapping("/{teamId}/srslots/{slotNo}")
	public void contract(@PathVariable int teamId, @PathVariable int slotNo) {
		scoutReportingService.contract(teamId, slotNo);
	}
	
	@PutMapping("/{teamId}/srslots")
	void refreshByAP(@PathVariable int teamId, 
			@RequestParam(value = "teamCode", required = false) String teamCode, 
			@RequestParam(value = "isCostUp") boolean isCostUp) {
		scoutReportingService.refreshByAP(teamId, teamCode, isCostUp);
	}


}
