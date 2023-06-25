package com.helpinghands.HelpingHands.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.helpinghands.HelpingHands.entities.BloodBank;

import java.util.List;

@Repository
public interface BloodBankDao extends JpaRepository<BloodBank,Long>{
	
	public BloodBank findByName(String name);
	@Query("select a from BloodBank a where a.postal =:postal")
	public List<BloodBank> allBloodBanksInPostal(@Param("postal") String postal);

}