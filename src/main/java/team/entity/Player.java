package team.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

//Declares in JPA this is an entity and data creates our getters and setters
@Entity
@Data
public class Player {
	
	//ID and Generated value define the primary key in our table. The other columns are declared as variables without annotation.
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long playerId;
	private String playerFirstName;
	private String playerLastName;
	private String playerNumber;
	private String playerPosition;
	private int playerAge;
	private String playerCountry;
	
	//Many to One annotation for the relationship with Team
	//Also adding annotations to prevent recursions
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "team_id")
	
	//Declare Team object
	private Team team;

}
