package com.skilldistillery.enchantedrealm.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.enchantedrealm.entities.Application;
import com.skilldistillery.enchantedrealm.entities.Company;
import com.skilldistillery.enchantedrealm.entities.JobPosting;
import com.skilldistillery.enchantedrealm.services.ApplicationService;
import com.skilldistillery.enchantedrealm.services.JobPostingService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api")
@CrossOrigin({ "*", "http://localhost/" })
public class JobPostingController {

	@Autowired
	JobPostingService jpService;
	
	@Autowired
	ApplicationService appService;
	
	@GetMapping("jobpostings")
	public List<JobPosting> index(HttpServletRequest req, HttpServletResponse res) {
		return jpService.index();
	}
	
	@GetMapping("jobpostings/search/title/{title}")
	public List<JobPosting> findByTitle(@PathVariable("title") String title, HttpServletRequest req, HttpServletResponse res) {
		return jpService.findByTitle(title);
	}
	
	@GetMapping("jobpostings/search/company/{name}")
	public List<JobPosting> findByCompanyName(@PathVariable("name") String name, HttpServletRequest req, HttpServletResponse res) {
		return jpService.findByCompany(name);
	}
	
	@GetMapping("jobpostings/search/{low}/{high}")
	public List<JobPosting> findBySalaryRange(@PathVariable("low") int low, @PathVariable("high") int high, HttpServletRequest req, HttpServletResponse res) {
		return jpService.findBySalary(low, high);
	}
	
	@GetMapping("jobpostings/search/industry/{name}")
	public List<JobPosting> findByIndustry(@PathVariable("name") String name, HttpServletRequest req, HttpServletResponse res) {
		return jpService.findByIndustry(name);
	}
	
	@GetMapping("jobpostings/search/location/city/{city}")
	public List<JobPosting> findByCity(@PathVariable("city") String city, HttpServletRequest req, HttpServletResponse res) {
		return jpService.findByCity(city);
	}
	
	@GetMapping("jobpostings/search/location/state/{state}")
	public List<JobPosting> findByState(@PathVariable("state") String state, HttpServletRequest req, HttpServletResponse res) {
		return jpService.findByState(state);
	}
	
	@GetMapping("jobpostings/search/location/zipcode/{zip}")
	public List<JobPosting> findByZipcode(@PathVariable("zip") String zip, HttpServletRequest req, HttpServletResponse res) {
		return jpService.findByZipCode(zip);
	}
	
	@GetMapping("jobpostings/search/location/{city}/{state}")
	public List<JobPosting> findBycity(@PathVariable("city") String city, @PathVariable("state") String state, HttpServletRequest req, HttpServletResponse res) {
		return jpService.findByCityAndState(city, state);
	}
	
	@GetMapping("jobpostings/{id}")
	public JobPosting findById(@PathVariable("id") int id, HttpServletRequest req, HttpServletResponse res) {
		JobPosting jp = jpService.findById(id);
		if(jp == null) {
			res.setStatus(404);
		}
		return jp;
	}
	
	@GetMapping("jobpostings/{id}/applications")
	public List<Application> findAppsByJobId(@PathVariable("id") int id, HttpServletRequest req, HttpServletResponse res) {
		return appService.findAppsByJobId(id);
	}
	
	@PutMapping("jobpostings/{id}")
	public JobPosting updateJobPosting(@PathVariable("id") int id, @RequestBody JobPosting jobPost, Principal principal, HttpServletResponse res, HttpServletRequest req) {
		JobPosting updatedJobPosting;
		try {
			updatedJobPosting = jpService.updateJobPosting(principal.getName(), id, jobPost);
			if(updatedJobPosting == null) {
				res.setStatus(404);
			} else {
				res.setStatus(200);
			}
		} catch (Exception e) {
			res.setStatus(400);
			e.printStackTrace();
			updatedJobPosting = null;
		}
		return updatedJobPosting;
	}
	
}
