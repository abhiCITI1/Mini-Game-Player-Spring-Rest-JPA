/**
 * 
 */
package edu.sjsu.cmpe275.lab2.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


/**
 * @author Abhishek
 * @author Manasi
 */
@Entity(name="player")
public class Player {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="player_id")
	private int id;

	private String firstname;
    private String lastname;
	private String email;
    private String description;
    
    @Embedded
    private Address address;
    
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="sponsor_id")
    private Sponsor sponsor;
    
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "opponents", joinColumns = {
    		 @JoinColumn(name = "player_id", referencedColumnName = "player_id", nullable = false)}, inverseJoinColumns = {
    		 @JoinColumn(name = "opponent_id", referencedColumnName = "player_id", nullable = false)})
    @JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id" , scope=Player.class)
    private List<Player> opponents;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @param firstname the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * @param lastname the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * @return the sponsor
	 */
	public Sponsor getSponsor() {
		return sponsor;
	}

	/**
	 * @param sponsor the sponsor to set
	 */
	public void setSponsor(Sponsor sponsor) {
		this.sponsor = sponsor;
	}

	/**
	 * @return the opponents
	 */
	public List<Player> getOpponents() {
		return opponents;
	}

	/**
	 * @param opponents the opponents to set
	 */
	public void setOpponents(List<Player> opponents) {
		this.opponents = opponents;
	}
	

}
