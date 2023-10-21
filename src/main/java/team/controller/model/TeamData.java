package team.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import team.entity.League;
import team.entity.Player;
import team.entity.Team;

@Data
@NoArgsConstructor
public class TeamData {
	//establishing variables for TeamData object
	private Long teamId;
	private String teamName;
	private String teamCountry;
	private Set<TeamLeague> leagues = new HashSet<>();
	private Set<TeamPlayer> players = new HashSet<>();
	
	//constructor for TeamData object, grabbing getters from Team object 
	public TeamData (Team team) {
		teamId = team.getTeamId();
		teamName = team.getTeamName();
		teamCountry = team.getTeamCountry();
		
		//loops to iterate through each set of leagues and players
		
		for (Player player : team.getPlayers()) {
			players.add(new TeamPlayer(player));
		} 
		
		for (League league : team.getLeagues()) {
			leagues.add(new TeamLeague(league));
		}
	}
	
@Data
@NoArgsConstructor
public static class TeamLeague {
	
	private Long leagueId;
	private String leagueName;
	private String leagueCountry;
	
	public TeamLeague(League league) {
		leagueId = league.getLeagueId();
		leagueName = league.getLeagueName();
		leagueCountry = league.getLeagueCountry();
	}
	
}

@Data
@NoArgsConstructor
public static class TeamPlayer {
	
	private Long playerId;
	private String playerFirstName;
	private String playerLastName;
	private String playerNumber;
	private String playerPosition;
	private int playerAge;
	private String playerCountry;
	
	public TeamPlayer (Player player) {
		
		playerId = player.getPlayerId();
		playerFirstName = player.getPlayerFirstName();
		playerLastName = player.getPlayerLastName();
		playerNumber = player.getPlayerNumber();
		playerPosition = player.getPlayerPosition();
		playerAge = player.getPlayerAge();
		playerCountry = player.getPlayerCountry();
		
	}
	
}

}
