package com.example;

import static org.junit.Assert.*;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Customer;

@RunWith(org.springframework.test.context.junit4.SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=App.class)
@Transactional
public class TestApp {

	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;
	
	@Test
	public void test() {
		String sql = "SELECT 1";
		SqlParameterSource param = new MapSqlParameterSource();
		Integer result = jdbcTemplate.queryForObject(sql, param, Integer.class);
		assertThat(result, is(1));
	}

	@Test
	public void testPlus() {
		String sql = "SELECT :a + :b";
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("a", 500)
				.addValue("b", 1000);
		Integer result = jdbcTemplate.queryForObject(sql, param, Integer.class);
		assertThat(result, is(1500));
	}
	
	@Test
	public void testCustomer() {
		String sql = "SELECT id, first_name, last_name FROM customers WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("id", 1L);
		
		Customer customer = jdbcTemplate.queryForObject(sql, param, (rs, num) -> {
			return new Customer(rs.getLong("id")
					, rs.getString("first_name")
					, rs.getString("last_name"));
		});
		
		assertThat(customer.getId(), is(1L));
	}
	
	@Test
	public void testFindFirstName() {
		String sql = "SELECT id, first_name, last_name FROM customers WHERE first_name = :firstName";
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("firstName", "A");
		List<Customer> customers =jdbcTemplate.query(sql, param, (rs, num) -> {
			Long id = rs.getLong("id");
			String firstName = rs.getString("first_name");
			String lastName = rs.getString("last_name");

			return new Customer(id, firstName, lastName);
		});
		assertThat(customers.size(), is(10));
	}
	
	@Test
	public void testFindAll() {
		String sql = "SELECT id, first_name, last_name FROM customers";
		SqlParameterSource param = new MapSqlParameterSource();
		List<Customer> customers =jdbcTemplate.query(sql, param, (rs, num) -> {
			Long id = rs.getLong("id");
			String firstName = rs.getString("first_name");
			String lastName = rs.getString("last_name");

			return new Customer(id, firstName, lastName);
		});
		
		assertThat(customers.size(), is(24));
		
	}
	
	@Test
	public void testSave() {
		String sql = "INSERT INTO customers (first_name, last_name) VALUES (:firstName, :lastName)";
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("firstName", "Hibernate")
				.addValue("lastName", "JPA");
		KeyHolder keyHolder = new GeneratedKeyHolder();
		int update = jdbcTemplate.update(sql, param, keyHolder);
		assertThat(update, is(1));
		assertThat(keyHolder.getKey(), is(25));
	}
}
