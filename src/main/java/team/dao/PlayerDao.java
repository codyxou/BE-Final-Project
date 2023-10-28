package team.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import team.entity.Player;

public interface PlayerDao extends JpaRepository<Player, Long> {

}
