package team.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import team.controller.model.TeamData;
import team.dao.TeamDao;
import team.entity.Team;

@Service
public class TeamService {
	
	//Declaring Auto dependencies
	@Autowired
	private TeamDao teamDao;
	
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
}
