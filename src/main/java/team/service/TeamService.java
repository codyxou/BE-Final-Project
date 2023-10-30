package team.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import team.controller.model.TeamData;
import team.controller.model.TeamData.TeamLeague;
import team.controller.model.TeamData.TeamPlayer;
import team.controller.model.TeamData.TeamWithLeagues;
import team.dao.LeagueDao;
import team.dao.PlayerDao;
import team.dao.TeamDao;
import team.entity.League;
import team.entity.Player;
import team.entity.Team;

@Service
public class TeamService {
	
	//Declaring Auto dependencies
	@Autowired
	private TeamDao teamDao;
	@Autowired 
	private PlayerDao playerDao;
	@Autowired
	private LeagueDao leagueDao;
	
	//---------------START Team Service Methods----------------//
	
	//transactional annotation to create a mySQL transaction for when this method is called to post to the database 
	@Transactional(readOnly = false)
	//save team Method that calls a method and subsequent methods to either find an existing team or create a new one 
	public TeamData saveTeam(TeamData teamData) {
		Long teamId = teamData.getTeamId();
		Team team = findOrCreateTeam(teamId);
		
		copyTeamFields(team, teamData);
		return new TeamData (teamDao.save(team));
	}
//method that takes the Team and TeamData objects as parameters and sets new ID, Name, and Country to new object 
	private void copyTeamFields(Team team, TeamData teamData) {
		team.setTeamId(teamData.getTeamId());
		team.setTeamName(teamData.getTeamName());
		team.setTeamCountry(teamData.getTeamCountry());
		
	}

	private Team findOrCreateTeam(Long teamId) {
		//set team object
		Team team;
		
		//if/else to check if teamId is null. If it is, creates a new Team object. if not, it calls the findId method for TeamId 
		if(Objects.isNull(teamId)) {
			team = new Team();
		}
		else {
			team = findTeamById(teamId);
		}
		return team;	
	}
	//searches for existing TeamId or throws the NSEE 
	private Team findTeamById(Long teamId) {
		return teamDao.findById(teamId).orElseThrow(
				() -> new NoSuchElementException("Team with ID=" + teamId + " was not found."));
	}
	//create a new transaction with read only as true since we're not changing/adding any data
	@Transactional(readOnly = true)
	public List<TeamData> retrieveAllTeams() {
		// making a list of Team with name all teams to find all within the teamDao
		List<Team> allTeams = teamDao.findAll();
		//Converting list of team into list of TeamData
		List<TeamData> result = new LinkedList<>();
		//Loop to clear leagues and players from the data so the get only shows purely teams
		for (Team team : allTeams) {
			TeamData allTeamData = new TeamData(team);
					
			allTeamData.getLeagues().clear();
			allTeamData.getPlayers().clear();
			result.add(allTeamData);	
		
		}
		return result;
	}
	
	//---------------END Team Service Methods------------------//
	
	//---------------START Player Service Methods----------------//
	
	@Transactional(readOnly = false)
	public TeamPlayer savePlayer(Long teamId, TeamPlayer teamPlayer) {
		//Grab Team Object and set equal to Find Team ID method
		Team team = findTeamById(teamId);
		//set variable playerId to playerId from TeamPlayer object
		Long playerId = teamPlayer.getPlayerId();
		//set Player Object to run findOrCreatePlayer Method
		Player player = findOrCreatePlayer(teamId, playerId);
		
		copyPlayerFields(player, teamPlayer);
		
		player.setTeam(team);
		team.getPlayers().add(player);
		Player dbPlayer = playerDao.save(player);
		return new TeamPlayer(dbPlayer);
		
	}
	private void copyPlayerFields(Player player, TeamPlayer teamPlayer) {
		player.setPlayerId(teamPlayer.getPlayerId());
		player.setPlayerFirstName(teamPlayer.getPlayerFirstName());
		player.setPlayerLastName(teamPlayer.getPlayerLastName());
		player.setPlayerNumber(teamPlayer.getPlayerNumber());
		player.setPlayerPosition(teamPlayer.getPlayerPosition());
		player.setPlayerAge(teamPlayer.getPlayerAge());
		player.setPlayerCountry(teamPlayer.getPlayerCountry());
		
	}
	private Player findOrCreatePlayer(Long teamId, Long playerId) {
		//nearly identical setup to findOrCreateTeam with the difference being in the else statement to have 2 parameters for find Player by ID method
		Player player;
		if(Objects.isNull(playerId)) {
			player = new Player();
		}
		else {
			player = findPlayerById(teamId, playerId);
		}
		return player;
	}
	private Player findPlayerById(Long teamId, Long playerId) {
		//same setup as findTeambyId Method, but also adding Illegal argument exception to check the TeamID assigned to the player. 
		//if it doesn't match, then it throws the exception. If it does, it returns player.
		Player player = playerDao.findById(playerId)
				.orElseThrow(() -> new NoSuchElementException("Player with ID=" + playerId + " was not found."));
		
		if(player.getTeam().getTeamId() != teamId ) {
			throw new IllegalArgumentException("The player with ID=" + playerId + " does not play for team with ID=" + teamId);
		}
		return player;
	}
	@Transactional(readOnly = true)
	public List<TeamPlayer> retrieveAllPlayers() {
		
		//Setting List of Players to the findAll Method within the PlayerDao interface
		List<Player> listOfPlayers = playerDao.findAll();
		List<TeamPlayer> allPlayers = new LinkedList<>();
		
		//iterate through the object to find all players and add to our new variable tData
		for (Player player: listOfPlayers) {
			TeamPlayer tData = new TeamPlayer(player);
			
			allPlayers.add(tData);
		}
		return allPlayers;
	}
	
	public List<TeamPlayer> retrievePlayersByTeamId(Long teamId) {
			//Get Team object using teamId
			Team team = findTeamById(teamId);
			
			//Get the list of players for selected teamId using the team.getplayers
			List<Player> listOfPlayers = new ArrayList<>(team.getPlayers());
			List<TeamPlayer> teamPlayers = new LinkedList<>();
			
			for(Player player : listOfPlayers) {
				TeamPlayer tpData = new TeamPlayer(player);
				
				teamPlayers.add(tpData);
			}
			return teamPlayers;
			
	}
	
	@Transactional(readOnly = false)
	public void deletePlayerById(Long teamId, Long playerId) {
		Player player = findPlayerById(teamId,playerId);
		playerDao.delete(player);
	}
	
	//---------------END Player Service Methods------------------//
	
	//---------------START League Service Methods------------------//
	
	//similar setup to retrieveAllPlayers above
	@Transactional(readOnly = true)
	public List<TeamLeague> retrieveAllLeagues() {
		List<League> listOfLeagues = leagueDao.findAll();
		List<TeamLeague> allLeagues = new LinkedList<>();
		
		for (League league: listOfLeagues) {
			TeamLeague lData = new TeamLeague(league);
			
			allLeagues.add(lData);
		}
		return allLeagues;
	}
	//This method should satisfy the get requirement between my many-to-many relationship. indicating what teams belong to what leagues
	@Transactional(readOnly = true)
	public List<TeamWithLeagues> retrieveTeamsAndLeagues() {
		List <Team> allTeams = teamDao.findAll();
		List <TeamWithLeagues> result = new ArrayList<>();
		
		for (Team team : allTeams) {
			Set<League> leagues = team.getLeagues();
			TeamWithLeagues teamWithLeagues = new TeamWithLeagues(team,leagues);
			result.add(teamWithLeagues);
		}
		return result;
	//---------------END League Service Methods------------------//

	}

}
