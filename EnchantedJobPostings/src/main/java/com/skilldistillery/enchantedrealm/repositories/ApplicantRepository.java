package com.skilldistillery.enchantedrealm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.enchantedrealm.entities.Applicant;

public interface ApplicantRepository extends JpaRepository<Applicant, Integer>{
	
	Applicant findByIdAndUser_username(int id, String username);

	Applicant findByUser_username(String username);
}
