package team.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import team.entity.Team;

public interface TeamDao extends JpaRepository<Team, Long> {

}
