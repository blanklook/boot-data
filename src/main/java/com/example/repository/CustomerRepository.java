package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	public List<Customer> findByFirstName(String firstName); 
	public List<Customer> findByLastName(String lastName);
	
	@Query("SELECT c FROM Customer c WHERE c.firstName=?1 AND c.lastName=?2")
	public Customer findByName(String firstName, String lastName);

	@Query("SELECT c FROM Customer c WHERE c.firstName=:firstName AND c.lastName=:lastName")
	public Customer findByFullName(
			@Param("firstName") String firstName, 
			@Param("lastName") String lastName);
}
