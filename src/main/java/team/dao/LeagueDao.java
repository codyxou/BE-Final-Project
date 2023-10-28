package team.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import team.entity.League;

public interface LeagueDao extends JpaRepository<League, Long> {

}
