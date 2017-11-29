/**
 * 
 */
package edu.sjsu.cmpe275.lab2.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.sjsu.cmpe275.lab2.models.Player;


/**
 * @author Abhishek
 * @author Manasi
 */
@Repository
public interface PlayerRepository extends JpaRepository<Player, Serializable>{
	public List<Player> findByFirstname(String firstname);
	public Player findById(int id);
	public List<Player> findAll();
	public void delete(Player player);
	
}
