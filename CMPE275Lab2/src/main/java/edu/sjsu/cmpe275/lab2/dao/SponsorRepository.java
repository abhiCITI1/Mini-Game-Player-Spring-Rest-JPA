/**
 * 
 */
package edu.sjsu.cmpe275.lab2.dao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.sjsu.cmpe275.lab2.models.Sponsor;

/**
 * @author Abhishek
 * @author Manasi
 */
public interface SponsorRepository extends JpaRepository<Sponsor, Serializable>{

	public Sponsor findById(int id);
}
