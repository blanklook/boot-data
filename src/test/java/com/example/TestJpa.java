package com.example;

import static org.junit.Assert.*;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Customer;

@RunWith(org.springframework.test.context.junit4.SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=App.class)
@Transactional
public class TestJpa {
	@PersistenceContext
	EntityManager em;
	
	@Test
	public void testFindOne() {
		Customer customer = em.find(Customer.class, 1L);
		assertThat(customer.getId(), is(1L));
	}
	
	@Test
	public void testFindFirstName() {
		List<Customer> customers = em.createQuery("SELECT c FROM Customer c WHERE c.firstName=:firstName", Customer.class)
		.setParameter("firstName", "C")
		.getResultList();
		assertThat(customers.size(), is(3));
	}
	
	@Test
	public void testFindAll() {
		List<Customer> customers = em.createQuery("SELECT c FROM Customer c", Customer.class)
		.getResultList();
		assertThat(customers.size(), is(24));
	}
	
	@Test
	public void testPersist() {
		em.persist(new Customer("P","P1"));
		em.persist(new Customer("P","P1"));
		em.persist(new Customer("P","P1"));
		em.persist(new Customer("P","P1"));
		em.persist(new Customer("P","P1"));
		em.persist(new Customer("P","P1"));
		em.flush();
		
		List<Customer> customers = em.createQuery("SELECT c FROM Customer c WHERE c.firstName = :firstName", Customer.class)
		.setParameter("firstName", "P")
		.getResultList();
		
		assertThat(customers.size(), is(6));
		
	}
}
