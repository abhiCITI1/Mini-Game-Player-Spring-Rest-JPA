/**
 * 
 */
package edu.sjsu.cmpe275.lab2.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.sjsu.cmpe275.lab2.models.Player;
import edu.sjsu.cmpe275.lab2.models.Sponsor;
import edu.sjsu.cmpe275.lab2.services.MiniGameService;

/**
 * @author Abhishek
 * @author Manasi
 */
@RestController("/")
public class MiniGameController {

	@Autowired
	private MiniGameService miniGameService;
	
	@GetMapping(value="/")
	public String sayHello()
	{
		return "Hello!";
	}
	
	@RequestMapping(value="/createPlayer", method=RequestMethod.POST)
	public ResponseEntity<?> createPlayer( HttpServletRequest request)
	{
		
		if(request.getParameter("firstname")==null || 
				request.getParameter("lastname")==null ||
				request.getParameter("email")==null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		else
			return miniGameService.createPlayer(request);
		
	}
	
	@RequestMapping(value="/player/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> getPlayer(@PathVariable Integer id)
	{
		HttpHeaders headers = new HttpHeaders();
		headers.add("Group 16", "Abhishek Upadhyay, Manasi Milind Joshi");
		Player player = miniGameService.getPlayer(id);
		if(player!=null)
			return new ResponseEntity<>(player,headers, HttpStatus.OK);
		else
			return new ResponseEntity<>("No player present with this id",headers, HttpStatus.NOT_FOUND);
			
	}
	
	@RequestMapping(value="/player/{id}", method=RequestMethod.POST)
	public ResponseEntity<?> updatePlayer(HttpServletRequest request, @PathVariable Integer id)
	{
		ResponseEntity<?> response = miniGameService.updatePlayer(id, request);
		return response; 
	}
	
	@RequestMapping(value="/player/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<?> deletePlayer(@PathVariable Integer id)
	{
		ResponseEntity<?> response = miniGameService.deletePlayer(id);
		return response;
	}
	
	@RequestMapping(value="/sponsor", method=RequestMethod.POST)
	public ResponseEntity<?> createSponsor(HttpServletRequest request)
	{
		if(request.getParameter("name")==null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		else
			return miniGameService.createSponsor(request);
	}
	
	@RequestMapping(value="/sponsor/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> getSponsor(@PathVariable Integer id)
	{
		Sponsor sponsor = miniGameService.getSponsor(id);
		if(sponsor!=null)
			return new ResponseEntity<>(sponsor, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	
	@RequestMapping(value="/sponsor/{id}", method=RequestMethod.POST)
	public ResponseEntity<?> updateSponsor(HttpServletRequest request, @PathVariable Integer id)
	{
		if(request.getParameter("name")==null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		else
			return miniGameService.updateSponsor(id, request);
	}
	
	@RequestMapping(value="/sponsor/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteSponsor(@PathVariable Integer id)
	{
		ResponseEntity<?> response = miniGameService.deleteSponsor(id);
		return response;
	}
	
	
	
	@RequestMapping(value="/opponents/{id1}/{id2}", method=RequestMethod.POST)
	public ResponseEntity<?> addOpponents(@PathVariable Integer id1, @PathVariable Integer id2)
	{
		String message = miniGameService.addOpponents(id1, id2);
		if(message.contains("Success"))
			return new ResponseEntity<>(message, HttpStatus.OK);
		else
			return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/opponents//{id1}/{id2}", method=RequestMethod.DELETE)
	public ResponseEntity<?> removeOpponents(@PathVariable Integer id1, @PathVariable Integer id2)
	{
		String message = miniGameService.removeOpponents(id1, id2);
		if(message.equals("Success"))
			return new ResponseEntity<>(message, HttpStatus.OK);
		else
			return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	}
	
	
	
	
}
