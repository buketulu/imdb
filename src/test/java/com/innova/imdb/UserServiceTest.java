package com.innova.imdb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.innova.imdb.entities.User;
import com.innova.imdb.entities.User.CustomerType;
import com.innova.imdb.repository.UserRepository;
import com.innova.imdb.services.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	User user;

	@BeforeEach
	void setUp() throws Exception {
		/*
		 * This is needed for Mockito to be able to instantiate the Mock Objects and
		 * Inject into the userServiceImpl object
		 */
		MockitoAnnotations.initMocks(this);

		user = new User();
		user.setId(1L);
		user.setUserName("turkan");
		user.setPassword("aaa");
		user.setCustomerType(CustomerType.NORMAL);
	}

	@Test
	public void testCreateUser() throws ParseException {

		System.out.println(userRepository);

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

		when(userRepository.getById(anyLong())).thenReturn(user);

		User newUser = userService.getUserById(1L);

		assertNotNull(newUser);
		assertEquals("turkan", newUser.getUserName());

	}

}
