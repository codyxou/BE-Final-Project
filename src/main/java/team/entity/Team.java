package team.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Team {
	
	//setting annotations for primary key for Team entity - teamId
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long teamId;
	
	private String teamName;
	private String teamCountry;
	
	//many to many relationship annotation for many leagues to many teams 
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "team_league",
				joinColumns = @JoinColumn(name = "team_id"),
				inverseJoinColumns = @JoinColumn(name = "league_id"))
	private Set<League> leagues = new HashSet<>();
	
	//One to many relationship annotation for One team for many players 
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Player> players = new HashSet<>();

}
