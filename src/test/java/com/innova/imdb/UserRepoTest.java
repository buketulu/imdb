package com.innova.imdb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.innova.imdb.entities.Company;
import com.innova.imdb.entities.User;
import com.innova.imdb.entities.User.CustomerType;
import com.innova.imdb.repository.CompanyRepository;
import com.innova.imdb.repository.UserRepository;
import com.innova.imdb.services.UserService;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
public class UserRepoTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CompanyRepository companyRepository;
	
	@Test
	@Rollback(false)
	@Order(1)
	public void testCreateUser() {

		User user1 = new User();
		user1.setUserName("imdb");
		user1.setPassword("aaa");
		user1.setCustomerType(CustomerType.COMPANY);
		User savedUser = userRepository.save(user1);
		Company c=new Company();
		c.setName("imdb");
		user1.setCompany(c);
		c.setCompanyUser(savedUser);
		Company savedCompany=companyRepository.save(c);

		
		
		assertNotNull(savedCompany);
		assertNotNull(savedUser);
		
		assertEquals(savedUser.getId(), user1.getId(), "Bu id'ler eşit değil");
		assertEquals(savedUser.getCompany(), savedCompany, "Company error");
		
	}
	
	@Test
	@Rollback(true)
	@Order(2)
	public void testUpdateUser() {
		User newUser = new User();
		newUser.setUserName("old");
		newUser.setPassword("aaa");
		User dbUser = userRepository.save(newUser);
		
		assertEquals(dbUser.getUserName(), newUser.getUserName());

		dbUser.setUserName("new");
		User newDbUser = userRepository.save(dbUser);
		
		assertEquals(newDbUser.getUserName(), "new");
	}


	@Test
	@Rollback(true)
	@Order(3)
	public void testDeleteUser() {
		User user = new User();
		user.setUserName("dummy");
		user.setPassword("aaa");
		user.setCustomerType(CustomerType.ACTOR);
		User savedUser = userRepository.save(user);

		Optional<User> userdbUser = userRepository.findById(savedUser.getId());
		assertTrue(userdbUser.isPresent());
		
		userRepository.delete(userdbUser.get());
		
		userdbUser = userRepository.findById(savedUser.getId());
		
		assertTrue(userdbUser.isEmpty());


	}

}
