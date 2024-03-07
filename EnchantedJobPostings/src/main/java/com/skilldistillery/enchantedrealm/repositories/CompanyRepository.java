package com.skilldistillery.enchantedrealm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.enchantedrealm.entities.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer>{

}