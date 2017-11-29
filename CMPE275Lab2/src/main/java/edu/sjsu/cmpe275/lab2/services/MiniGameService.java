/**
 * 
 */
package edu.sjsu.cmpe275.lab2.services;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import edu.sjsu.cmpe275.lab2.models.Player;
import edu.sjsu.cmpe275.lab2.models.Sponsor;

/**
 * @author Abhishek
 * @author Manasi
 */
public interface MiniGameService {

	public ResponseEntity<?> createPlayer(HttpServletRequest request);
	
	public Player getPlayer(int id);
	
	public ResponseEntity<?> updatePlayer(int id, HttpServletRequest request);
	
	public ResponseEntity<?> deletePlayer(int id);
	
	public ResponseEntity<?> createSponsor(HttpServletRequest request);

	public Sponsor getSponsor(int id);
	
	public ResponseEntity<?> updateSponsor(int id, HttpServletRequest request);
	
	public ResponseEntity<?> deleteSponsor(int id);
	
	public String addOpponents(int id1, int id2);
	
	public String removeOpponents(int id1, int id2);
	
}
