/**
 * 
 */
package edu.sjsu.cmpe275.lab2.services;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import edu.sjsu.cmpe275.lab2.dao.PlayerRepository;
import edu.sjsu.cmpe275.lab2.dao.SponsorRepository;
import edu.sjsu.cmpe275.lab2.models.Address;
import edu.sjsu.cmpe275.lab2.models.Player;
import edu.sjsu.cmpe275.lab2.models.Sponsor;

/**
 * @author Abhishek
 * @author Manasi
 */
@Service
public class MiniGameServiceImpl implements MiniGameService {
	
	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private SponsorRepository sponsorRepository;
	
	public ResponseEntity<?> createPlayer(HttpServletRequest request)
	{
		
		Player player = new Player();
		Address address = new Address();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Group 16", "Abhishek Upadhyay, Manasi Milind Joshi");
		
		player.setFirstname(request.getParameter("firstname"));
		player.setLastname(request.getParameter("lastname"));
		player.setEmail(request.getParameter("email"));
		
		if(!request.getParameter("description").equals("") && request.getParameter("description")!=null)
		{
			player.setDescription(request.getParameter("description"));
		}
		if(request.getParameter("street")!=null)
			address.setStreet(request.getParameter("street"));
		if(request.getParameter("city")!=null)
			address.setCity(request.getParameter("city"));
		if(request.getParameter("state")!=null)
			address.setState(request.getParameter("state"));
		if(request.getParameter("zip")!=null)
			address.setZip(request.getParameter("zip"));
		
		player.setAddress(address);
		
		if(request.getParameter("sponsor_id")!=null)
		{
			Sponsor sponsor = sponsorRepository.findById(Integer.valueOf(request.getParameter("sponsor_id")));
			if(sponsor!=null)
				player.setSponsor(sponsor);
		}
		
		Player createdPlayer = playerRepository.save(player);
		return new ResponseEntity<>(createdPlayer, headers, HttpStatus.OK);
	}
	
	public Player getPlayer(int id)
	{
		Player player = playerRepository.findById(id);
		return player;
	}
	
	public ResponseEntity<?> updatePlayer(int id, HttpServletRequest request)
	{
		Player player = playerRepository.findById(id);
		Player updatedPlayer = null;
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Group 16", "Abhishek Upadhyay, Manasi Milind Joshi");
		
		if(player!=null)
		{
			if(request.getParameter("firstname")==null || 
					request.getParameter("lastname")==null ||
					request.getParameter("email")==null)
			{
				return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
			}

			player.setFirstname(request.getParameter("firstname"));
			player.setLastname(request.getParameter("lastname"));
			player.setEmail(request.getParameter("email"));

			if(request.getParameter("description")!=null)
			{
				player.setDescription(request.getParameter("description"));
			}
			Address address = player.getAddress();
			if(address==null)
				address = new Address();
			if(request.getParameter("street")!=null)
				address.setStreet(request.getParameter("street"));
			if(request.getParameter("city")!=null)
				address.setCity(request.getParameter("city"));
			if(request.getParameter("state")!=null)
				address.setState(request.getParameter("state"));
			if(request.getParameter("zip")!=null)
				address.setZip(request.getParameter("zip"));

			player.setAddress(address);

			if(request.getParameter("sponsor_id")!=null)
			{
				Sponsor sponsor = sponsorRepository.findById(Integer.valueOf(request.getParameter("sponsor_id")));
				if(sponsor!=null)
					player.setSponsor(sponsor);
			}
			updatedPlayer = playerRepository.save(player);
			
			
			return new ResponseEntity<>(updatedPlayer, headers, HttpStatus.OK);

		}
		else
			return new ResponseEntity<>(headers,HttpStatus.NOT_FOUND);
		
	}
	
	@Override
	public ResponseEntity<?> deletePlayer(int id) {
		
		Player player = playerRepository.findById(id);
		Player deletedPlayer = player;
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Group 16", "Abhishek Upadhyay, Manasi Milind Joshi");
		
		if(player!=null)
		{
			List<Player> opponents = player.getOpponents();
			
			CopyOnWriteArrayList<Player> opponents1 = new CopyOnWriteArrayList<>();
			
			for (Player player1 : opponents) {
				opponents1.add(player1);
			}
			
			Iterator<Player> itr = opponents1.iterator();
			
			while(itr.hasNext())
			{
				removeOpponents(player.getId(), itr.next().getId());
			}
			
			//player.setOpponents(opponents);
			//opponents.re
			//playerRepository.save(player);
			deletedPlayer.setOpponents(opponents1);
			playerRepository.delete(player); 
			return new ResponseEntity<>(deletedPlayer,headers, HttpStatus.OK);
		}
		else
			return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
	}
	
	@Override
	public ResponseEntity<?> createSponsor(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Sponsor sponsor = new Sponsor();
		Address address = new Address();
		
		sponsor.setName(request.getParameter("name"));
		
		if(request.getParameter("description")!=null)
			sponsor.setDescription(request.getParameter("description"));
			
		if(request.getParameter("street")!=null)
			address.setStreet(request.getParameter("street"));
		if(request.getParameter("city")!=null)
			address.setCity(request.getParameter("city"));
		if(request.getParameter("state")!=null)
			address.setState(request.getParameter("state"));
		if(request.getParameter("zip")!=null)
			address.setZip(request.getParameter("zip"));
		
		sponsor.setAddress(address);
		
		Sponsor createdSponsor = sponsorRepository.save(sponsor);
		
		return new ResponseEntity<>(createdSponsor, HttpStatus.OK);
	}
	
	
	public Sponsor getSponsor(int id)
	{
		return sponsorRepository.findById(id);
	}
	
	@Override
	public ResponseEntity<?> updateSponsor(int id, HttpServletRequest request) {

		Sponsor sponsor = sponsorRepository.findById(id);
		
		if(sponsor!=null)
		{
			if(request.getParameter("name")!=null)
				sponsor.setName(request.getParameter("name"));
			
			if(request.getParameter("description")!=null)
				sponsor.setDescription(request.getParameter("description"));
				
			
			Address address = sponsor.getAddress();
			if(address==null)
				address = new Address();
			if(request.getParameter("street")!=null)
				address.setStreet(request.getParameter("street"));
			if(request.getParameter("city")!=null)
				address.setCity(request.getParameter("city"));
			if(request.getParameter("state")!=null)
				address.setState(request.getParameter("state"));
			if(request.getParameter("zip")!=null)
				address.setZip(request.getParameter("zip"));
			
			sponsor.setAddress(address);
			
			Sponsor updatedSponsor = sponsorRepository.save(sponsor);
			
			return new ResponseEntity<>(updatedSponsor, HttpStatus.OK);
		}
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@Override
	public ResponseEntity<?> deleteSponsor(int id) {

		Sponsor sponsor = sponsorRepository.findById(id);
		
		if(sponsor!=null)
		{
			List<Player> players = playerRepository.findAll();
			for (Player player : players) {
				Sponsor playerSponsor = player.getSponsor();
				if(playerSponsor!=null)
				{
					if(playerSponsor.getId()==id)
						return new ResponseEntity<>("Can't delete this sponsor as one or more players still belong to this sponsor",HttpStatus.BAD_REQUEST);
				}
				
			}
			sponsorRepository.delete(sponsor);
			return new ResponseEntity<>(sponsor, HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public String addOpponents(int id1, int id2) {
		
		Player player1 = playerRepository.findById(id1);
		Player player2 = playerRepository.findById(id2);
		
		if(player1!=null && player2!=null)
		{
			
			List<Player> opponentsP1 = player1.getOpponents();
			
			
			List<Player> opponentsP2 =  player2.getOpponents();
		
			if(opponentsP1.contains(player2) && opponentsP2.contains(player1))
			{
				return "Success : Do nothing Players are already Opponents of each other";
			}
			else
			{
				//setting opponent of player 1
				opponentsP1.add(player2);
				player1.setOpponents(opponentsP1);
				
				//setting opponent of player 2
				opponentsP2.add(player1);
				player2.setOpponents(opponentsP2);
				
				//to manage the symmetric relationship update player table with both Player1 & Player2 opponents of each other
				playerRepository.save(player1);
				playerRepository.save(player2);
				return "Success";
			}
		}
		else
		{
			return "Players do not exist";
		}
	}

	@Override
	public String removeOpponents(int id1, int id2) {

		Player player1 = playerRepository.findById(id1);
		Player player2 = playerRepository.findById(id2); 
		String message = "";
		
		if(player1!=null || player2!=null)
		{
			List<Player> opponentsP1 = null;
			List<Player> opponentsP2 =null;
			//checking whether id1 or id2 are opponents of each other
			if(player1!=null)
			{
				opponentsP1 = player1.getOpponents();
			}
			if(player2!=null)
			{
				opponentsP2 = player2.getOpponents();
			}
			
			if(opponentsP1.contains(player2) && opponentsP2.contains(player1))
			{
				for(Player opponent : opponentsP1)
				{
					if(id2==opponent.getId())
					{
						opponentsP1.remove(opponent);
						break;
					}
				}
				//List<Player> opponentsP2 = player2.getOpponents();
				for(Player opponent : opponentsP2)
				{
					if(id1==opponent.getId())
					{
						opponentsP2.remove(opponent);
						break;
					}
				}
				if(player1!=null)
				{
					player1.setOpponents(opponentsP1);
				}
				if(player2!=null)
				{
					player2.setOpponents(opponentsP2);
				}
				playerRepository.save(player1);
				playerRepository.save(player2);
				message = "Success";
			}
			else
				message = "The two given players are not opponents of each other";
		}
		else
		{
			message = "Either player does not exist";
		}
		return message;
	}
}
