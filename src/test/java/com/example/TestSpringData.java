package com.example;

import static org.junit.Assert.*;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Customer;
import com.example.repository.CustomerRepository;

@RunWith(org.springframework.test.context.junit4.SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=App.class)
@Transactional
public class TestSpringData {

	@Autowired
	CustomerRepository repository;
	
	@Test
	public void test() {
		assertThat(repository, is(notNullValue()));
	}
	
	@Test
	public void testFindAll() {
		List<Customer> customers = repository.findAll();
		assertThat(customers.size(), is(24));
	}
	@Test
	public void testFindOne() {
		Customer customer = repository.findOne(1L);
		assertThat(customer.getId(), is(1L));
	}
	
	@Test
	public void testFindByFirstName() {
		List<Customer> customers = repository.findByFirstName("C");
		assertThat(customers.size(), is(3));
	}
	
	@Test
	public void testFindByLastName() {
		List<Customer> customers = repository.findByLastName("B1");
		assertThat(customers.size(), is(10));
	}
	
	@Test
	public void testFindByName() {
		Customer customer = repository.findByName("SHIN", "YOONSEOK");
		assertThat(customer.getFirstName(), is("SHIN"));
		assertThat(customer.getLastName(), is("YOONSEOK"));
	}
	@Test
	public void testFindByFullName() {
		Customer customer = repository.findByFullName("SHIN", "YOONSEOK");
		assertThat(customer.getFirstName(), is("SHIN"));
		assertThat(customer.getLastName(), is("YOONSEOK"));
	}
	
	@Test
	public void testFindAllOrderByFirstNameAsc() {
		List<Customer> customers = repository.findAll(orderByFirstNameAsc());
		assertThat(customers.get(0).getFirstName(), is("A") );
	}
	@Test
	public void testFindAllOrderByLastNameDesc() {
		Sort sort = new Sort(Sort.Direction.DESC, "firstName");
		List<Customer> customers = repository.findAll(sort);
		assertThat(customers.get(0).getFirstName(), is("SHIN") );
	}
	@Test
	public void testFindAllOrderByLastNameDesc2() {
		List<Customer> customers = repository.findAll(orderByLastNameDesc());
		assertThat(customers.get(0).getLastName(), is("YOONSEOK") );
	}
	
	private Sort orderByFirstNameAsc() {
		return new Sort(Sort.Direction.ASC, "firstName");
	}
	private Sort orderByLastNameDesc() {
		return new Sort(Sort.Direction.DESC, "firstName");
	}
	
	@Test
	public void testFindAllPageable() {
		Pageable pageable = new PageRequest(0, 5, orderByFirstNameAsc());
		Page<Customer> page = repository.findAll(pageable);
		assertThat(page.getContent().size(), is(5));
	}
	@Test
	public void testFindAllPageable2() {
		Pageable pageable = new PageRequest(0, 5, Sort.Direction.DESC, "lastName");
		Page<Customer> page = repository.findAll(pageable);
		assertThat(page.getContent().size(), is(5));
		pageable = new PageRequest(0, 10, Sort.Direction.DESC, "lastName");
		page = repository.findAll(pageable);
		assertThat(page.getContent().size(), is(10));
	}
}
