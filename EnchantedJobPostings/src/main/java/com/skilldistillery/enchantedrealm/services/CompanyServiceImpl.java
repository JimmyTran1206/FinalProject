package com.skilldistillery.enchantedrealm.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.enchantedrealm.entities.Address;
import com.skilldistillery.enchantedrealm.entities.Application;
import com.skilldistillery.enchantedrealm.entities.Company;
import com.skilldistillery.enchantedrealm.entities.JobPosting;
import com.skilldistillery.enchantedrealm.entities.Status;
import com.skilldistillery.enchantedrealm.entities.User;
import com.skilldistillery.enchantedrealm.repositories.AddressRepository;
import com.skilldistillery.enchantedrealm.repositories.ApplicationRepository;
import com.skilldistillery.enchantedrealm.repositories.CompanyRepository;
import com.skilldistillery.enchantedrealm.repositories.JobPostingRepository;
import com.skilldistillery.enchantedrealm.repositories.UserRepository;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyRepository companyRepo;

	@Autowired
	private JobPostingRepository jobRepo;

	@Autowired
	private ApplicationRepository appRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private AddressRepository addRepo;

	@Override
	public List<Company> findAllCompanies() {
		return companyRepo.findAll();
	}

	@Override
	public List<JobPosting> findCompanyJobPostings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Application> findCompanyApplications() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Company addCompany(User user, Company company) {
		if (user.getRole() != "Company") {
			return null;
		} else {
			return companyRepo.save(company);
		}
	}

	@Override
	public Company editCompany(String username, int id, Company company) {
		Company companyToUpdate = companyRepo.findById(id);
		if (!companyToUpdate.getUser().getUsername().equals(username)) {
			return null;
		} else {
			Optional<Address> addressOpt = addRepo.findById(company.getAddress().getId());
			if(addressOpt.isPresent()) {
				Address address = addressOpt.get();
				address.setStreet(company.getAddress().getStreet());
				address.setCity(company.getAddress().getCity());
				address.setState(company.getAddress().getState());
				address.setZipCode(company.getAddress().getZipCode());
				addRepo.save(address);
			}
			companyToUpdate.setAddress(company.getAddress());
			companyToUpdate.setLogo(company.getLogo());
			companyToUpdate.setName(company.getName());
			companyToUpdate.setOverview(company.getOverview());
			return companyRepo.save(companyToUpdate);
		}

	}

	@Override
	public boolean deleteCompany(String username, int id) {
		boolean result = companyRepo.existsById(id);
		if (result) {
			Company company = companyRepo.findById(id);
			if (company.getUser().getUsername().equals(username)) {
				company.setEnabled(false);
				companyRepo.saveAndFlush(company);
				List<JobPosting> jobPostings = jobRepo.findByCompany_id(id);
				if (!jobPostings.isEmpty()) {
					for (JobPosting jobPosting : jobPostings) {
						jobPosting.setEnabled(false);
						jobRepo.saveAndFlush(jobPosting);
						List<Application> jobApps = appRepo.findByJobPosting_id(jobPosting.getId());
						if (!jobApps.isEmpty()) {
							for (Application app : jobApps) {
								Status status = new Status();
								status.setId(3);
								app.setStatus(status);
								appRepo.saveAndFlush(app);
							}
						}
					}
				}
			}
		}
		return result;
	}

	@Override
	public Company findById(int id) {
		return companyRepo.findById(id);
	}

	@Override
	public Company create(Company company, String name) {
		User user = userRepo.findByUsername(name);
		if(user != null) {
			Address address = new Address();
			addRepo.saveAndFlush(address);
			company.setUser(user);
			company.setAddress(address);
			return companyRepo.saveAndFlush(company);
		}
		
		return null;
	}

	@Override
	public Company findByUsername(String name) {
		// TODO Auto-generated method stub
		Company company = companyRepo.findByUser_username(name);
		return company;
	}

}
