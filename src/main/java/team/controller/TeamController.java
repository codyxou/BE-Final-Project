package team.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import team.controller.model.TeamData;
import team.service.TeamService;

@RestController
@RequestMapping("/team")
@Slf4j
public class TeamController {
	
	//declare auto dependency injection
	@Autowired
	private TeamService teamService;
	
	//Post method to add a new team
	@PostMapping("/team")
	@ResponseStatus(code = HttpStatus.CREATED)
	public TeamData insertTeam(@RequestBody TeamData teamData) {
		log.info("Adding new team {}", teamData);
		return teamService.saveTeam(teamData);
	}
	
	//Get Method to get all teams
	@GetMapping("/team")
	//Create a list of teamData object for method called retrieveTeams
	public List<TeamData> retrieveAllTeams(){
		log.info("Retrieving all created teams.");
		return teamService.retrieveAllTeams();
	}

}
