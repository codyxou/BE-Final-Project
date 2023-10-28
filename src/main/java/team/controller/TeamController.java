package team.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import team.controller.model.TeamData;
import team.controller.model.TeamData.TeamLeague;
import team.controller.model.TeamData.TeamPlayer;
import team.controller.model.TeamData.TeamWithLeagues;
import team.service.TeamService;

//Declare annotations to let JPA know this file is a controller for URL /team. SLF4J from lombok allows us to log what is happening in each method 
@RestController
@RequestMapping("/team")
@Slf4j
public class TeamController {
	
	//declare auto dependency injection
	@Autowired
	private TeamService teamService;
	
	//---------------START Team CRUD methods---------------//
	
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
	//-----------------END Team CRUD Methods------------------//
	
	//---------------START Player CRUD Methods----------------//
	
	//POST Method to add a player to a team
	@PostMapping("/team/{teamId}/player")
	@ResponseStatus(code = HttpStatus.CREATED)
	public TeamPlayer addPlayer(@PathVariable Long teamId,
			@RequestBody TeamPlayer teamPlayer) {
		log.info("Player {} added to Team {}", teamPlayer, teamId);
		return teamService.savePlayer(teamId, teamPlayer);
		
	}
	
	//GET method to list all players
	@GetMapping("/team/player")
	//create a list of TeamData object as a constructor that returns the retrieveAllPlayers method created in the TeamService file
	public List<TeamPlayer> retrieveAllPlayers() {
		log.info("All players called");
		return teamService.retrieveAllPlayers();
	}
	
	//GET method to retrieve all players based on teamId
	@GetMapping("/team/{teamId}/player")
	public List<TeamPlayer> retrievePlayersByTeamId(@PathVariable Long teamId) {
		log.info("Retrieving all players from team with ID=", teamId);
		return teamService.retrievePlayersByTeamId(teamId);
	}
	
	//PUT method to update a Player
	@PutMapping("team/{teamId}/player/{playerId}")
	public TeamPlayer updatePlayer(@PathVariable Long teamId, @PathVariable Long playerId, @RequestBody TeamPlayer teamPlayer) {
		teamPlayer.setPlayerId(playerId);
		log.info("Updating Player {}", teamPlayer);
		return teamService.savePlayer(teamId, teamPlayer);
	}
	
	//DELETE method to remove a player by ID
	@DeleteMapping("team/{teamId}/player/{playerId}")	
	public Map<String, String> deletePlayerById(@PathVariable Long teamId, @PathVariable Long playerId) {
		log.info("Deleting player with ID {} from team with ID {}", playerId, teamId);
		teamService.deletePlayerById(teamId, playerId);
		
		return Map.of("Confirmed: ", "Player with ID=" + playerId + " has been deleted from team with ID=" + teamId);
	}
	
	//method to prevent anyone from deleting all players
	@DeleteMapping("/team")
	public void deleteAllPlayers() {
		log.info("Deleting all players...");
		throw new UnsupportedOperationException("Not allowed to delete all players!");
	}
	
	//---------------END Player CRUD Methods------------------//
	
	//---------------START League CRUD Methods----------------//
	@GetMapping("/team/league")
	public List<TeamLeague> retrieveAllLeagues() {
		log.info("Retrieve all leagues called");
		return teamService.retrieveAllLeagues();
	}
	
	@GetMapping("team/teamleagues")
	public List<TeamWithLeagues> retrieveTeamsandLeagues(){
		return teamService.retrieveTeamsAndLeagues();
	}
	
	
	
	//---------------END League CRUD Methods------------------//

}
