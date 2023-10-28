package team.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class League {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long leagueId;
	private String leagueName;
	private String leagueCountry;
	
	//many to many annotation and annotations to prevent recursion
	@EqualsAndHashCode.Exclude
	@JsonIgnore
	@ToString.Exclude
	@ManyToMany(mappedBy = "leagues", cascade = CascadeType.PERSIST)
	private Set<Team> teams = new HashSet<>();
}
