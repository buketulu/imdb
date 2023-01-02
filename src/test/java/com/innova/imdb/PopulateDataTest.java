package com.innova.imdb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.innova.imdb.entities.Actor;
import com.innova.imdb.entities.Company;
import com.innova.imdb.entities.Crew;
import com.innova.imdb.entities.Crew.Position;
import com.innova.imdb.entities.Movie;
import com.innova.imdb.entities.Multimedia;
import com.innova.imdb.entities.Multimedia.Type;
import com.innova.imdb.entities.Role;
import com.innova.imdb.entities.User;
import com.innova.imdb.entities.User.CustomerType;
import com.innova.imdb.repository.ActorRepository;
import com.innova.imdb.repository.CompanyRepository;
import com.innova.imdb.repository.MovieRepository;
import com.innova.imdb.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
public class PopulateDataTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ActorRepository actorRepository;

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private CompanyRepository companyRepository;
	
	@Test
	@Rollback(false)
	@Order(1)
	public void testCreateData() throws ParseException {

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

		movieRepository.deleteAll();
		actorRepository.deleteAll();
		userRepository.deleteAll();
		
		User user1 = new User();
		user1.setUserName("binnur");
		user1.setPassword("aaa");
		user1.setCustomerType(CustomerType.ACTOR);
		userRepository.save(user1);

		
		Actor actor1 = new Actor();
		actor1.setName("Binnur Kaya");
		actor1.setBirthdate(formatter.parse("19-04-1972"));
		actor1.setHeight(165);
		actor1.setPersonalInfo("1995-Bilkent Üniversitesi");
		actor1.setActorUser(user1);
		Multimedia m1=new Multimedia(Type.IMAGE, "/link1/image.jpeg", null,actor1);
		Multimedia m2=new Multimedia(Type.INTERVIEW, "/link1/tvshow.mpeg", null,actor1);
		actor1.addMultimedia(m1);
		actor1.addMultimedia(m2);
		actorRepository.save(actor1);

		User user2 = new User();
		user2.setUserName("ecem");
		user2.setPassword("aaa");
		user2.setCustomerType(CustomerType.ACTOR);
		userRepository.save(user2);

		Actor actor2 = new Actor();
		actor2.setName("Ecem Erkek");
		actor2.setBirthdate(formatter.parse("24-06-1989"));
		actor2.setHeight(169);
		actor2.setPersonalInfo("oyunculuk kariyerine ilk adımını tiyatro oyunları ile attı.");
		actor2.setActorUser(user2);
		Multimedia m3=new Multimedia(Type.IMAGE, "/link2/image.jpeg", null,actor2);
		Multimedia m4=new Multimedia(Type.INTERVIEW, "/link2/tvshow.mpeg", null,actor2);
		actor2.addMultimedia(m3);
		actor2.addMultimedia(m4);
		
		actorRepository.save(actor2);

		User user3 = new User();
		user3.setUserName("burak");
		user3.setPassword("aaa");
		user3.setCustomerType(CustomerType.ACTOR);
		userRepository.save(user3);

		Actor actor3 = new Actor();
		actor3.setName("Burak Dadak");
		actor3.setBirthdate(formatter.parse("08-06-1998"));
		actor3.setHeight(169);
		actor3.setPersonalInfo("bahçeşehir üniversitesi");
		actor3.setActorUser(user3);
		Multimedia m5=new Multimedia(Type.IMAGE, "/link3/image.jpeg", null,actor3);
		Multimedia m6=new Multimedia(Type.INTERVIEW, "/link3/image.mpeg", null,actor3);
		actor3.addMultimedia(m5);
		actor3.addMultimedia(m6);
		
		actorRepository.save(actor3);

		//MOvie 1
		User user4 = new User();
		user4.setUserName("ogmPictures");
		user4.setPassword("aaa");
		user4.setCustomerType(CustomerType.COMPANY);
		userRepository.save(user4);
		
		Company company1=new Company();
		company1.setName("OGM Pictures");
		company1.setCompanyUser(user4);
		companyRepository.save(company1);

		Movie movie1 = new Movie();
		movie1.setTitle("Kırmızı Oda");
		movie1.setCompany(company1);
		movie1.setProductionDate(formatter.parse("04-09-2020"));

		Set crewSet1 = new HashSet();
		Crew crew1 = new Crew();
		crew1.setName("Cem Karcı");
		crew1.setPosition(Position.DIRECTOR);
		crew1.setMovie(movie1);
		crewSet1.add(crew1);
		Crew crew2 = new Crew();
		crew2.setName("Banu Kiremitçi");
		crew2.setPosition(Position.WRITER);
		crew2.setMovie(movie1);
		crewSet1.add(crew2);
		movie1.setCrew(crewSet1);

		Set actorSet1 = new HashSet<>();
		Role role1 = new Role();
		role1.setActor(actor1);
		role1.setRoleName("Doktor Hanım");
		role1.setMovie(movie1);
		role1.setDescription("Manolya kliniği");
		actorSet1.add(role1);
		movie1.setActors(actorSet1);

		Multimedia m7=new Multimedia(Type.IMAGE, "/movies/movie1/image.jpeg", movie1,null);
		Multimedia m8=new Multimedia(Type.INTERVIEW, "/movies/movie1/image.mpeg", movie1,null);
		movie1.addMultimedia(m7);
		movie1.addMultimedia(m8);
		
		
		movieRepository.save(movie1);

		// Movie2

		User user5 = new User();
		user5.setUserName("bkm");
		user5.setPassword("aaa");
		user5.setCustomerType(CustomerType.COMPANY);
		userRepository.save(user5);

		Company company2=new Company();
		company2.setName("BKM");
		company2.setCompanyUser(user5);
		companyRepository.save(company2);


		Movie movie2 = new Movie();
		movie2.setTitle("Sen Hiç Ateşböceği Gördün Mü?");
		movie2.setCompany(company2);
		movie2.setProductionDate(formatter.parse("22-03-2021"));

		Set crewSet2 = new HashSet();
		Crew crew3 = new Crew();
		crew3.setName("Andaç Haznedaroğlu");
		crew3.setPosition(Position.DIRECTOR);
		crew3.setMovie(movie2);
		crewSet2.add(crew3);
		Crew crew4 = new Crew();
		crew4.setName("Yılmaz Erdoğan");
		crew4.setPosition(Position.WRITER);
		crew4.setMovie(movie2);
		crewSet2.add(crew4);
		movie2.setCrew(crewSet2);

		Set actorSet2 = new HashSet<>();
		Role role2 = new Role();
		role2.setActor(actor2);
		role2.setRoleName("Gülseren");
		role2.setMovie(movie2);
		role2.setDescription("başrol");
		actorSet2.add(role2);
		movie2.setActors(actorSet2);

		movieRepository.save(movie2);

		// Movie3

		User user6 = new User();
		user6.setUserName("timsb");
		user6.setPassword("aaa");
		user6.setCustomerType(CustomerType.COMPANY);
		userRepository.save(user6);

		Company company3=new Company();
		company3.setName("Tims&B");
		company3.setCompanyUser(user6);
		companyRepository.save(company3);
		
		Movie movie3 = new Movie();
		movie3.setTitle("Gülperi");
		movie3.setCompany(company3);
		movie3.setProductionDate(formatter.parse("14-09-2018"));

		Set crewSet3 = new HashSet();
		Crew crew5 = new Crew();
		crew5.setName("Metin Balekoğlu");
		crew5.setPosition(Position.DIRECTOR);
		crew5.setMovie(movie3);
		crewSet3.add(crew5);
		Crew crew6 = new Crew();
		crew6.setName("Sema Ergenekon");
		crew6.setPosition(Position.WRITER);
		crew6.setMovie(movie3);
		crewSet3.add(crew6);
		movie3.setCrew(crewSet3);

		Set actorSet3 = new HashSet<>();
		Role role3 = new Role();
		role3.setActor(actor3);
		role3.setRoleName("Hasan Taşın");
		role3.setMovie(movie3);
		role3.setDescription("Büyük oğlan");
		actorSet3.add(role3);
		movie3.setActors(actorSet3);

		movieRepository.save(movie3);

		// Movie4

		User user7 = new User();
		user7.setUserName("tmc");
		user7.setPassword("aaa");
		user7.setCustomerType(CustomerType.COMPANY);
		userRepository.save(user7);

		Company company4=new Company();
		company4.setName("TMC");
		company4.setCompanyUser(user7);
		companyRepository.save(company4);
		
		Movie movie4 = new Movie();
		movie4.setTitle("Güzel Günler");
		movie4.setCompany(company4);
		movie4.setProductionDate(formatter.parse("06-11-2022"));

		Set crewSet4 = new HashSet();
		Crew crew7 = new Crew();
		crew7.setName("Osman Taşçı");
		crew7.setPosition(Position.DIRECTOR);
		crew7.setMovie(movie4);
		crewSet4.add(crew7);
		Crew crew8 = new Crew();
		crew8.setName("Selin Tunç");
		crew8.setPosition(Position.WRITER);
		crew8.setMovie(movie4);
		crewSet4.add(crew8);
		movie4.setCrew(crewSet4);

		Set actorSet4 = new HashSet<>();
		Role role4 = new Role();
		role4.setActor(actor1);
		role4.setRoleName("Kıymet");
		role4.setMovie(movie4);
		role4.setDescription("3 çocuk annesi");
		actorSet4.add(role4);
		
		Role role5 = new Role();
		role5.setActor(actor2);
		role5.setRoleName("Füsun");
		role5.setMovie(movie4);
		role5.setDescription("Büyük kız");
		actorSet4.add(role5);
		
		Role role6 = new Role();
		role6.setActor(actor3);
		role6.setRoleName("Mihran");
		role6.setMovie(movie4);
		role6.setDescription("Füsun kuzen");
		actorSet4.add(role6);
		movie4.setActors(actorSet4);

		movieRepository.save(movie4);

		// MOvie 5

		User user8 = new User();
		user8.setUserName("plato");
		user8.setPassword("aaa");
		user8.setCustomerType(CustomerType.COMPANY);
		userRepository.save(user8);

		Company company5=new Company();
		company5.setName("Plato");
		company5.setCompanyUser(user8);
		companyRepository.save(company5);
		
		Movie movie5 = new Movie();
		movie5.setTitle("Avrupa Yakası");
		movie5.setCompany(company5);
		movie5.setProductionDate(formatter.parse("11-02-2004"));

		Set crewSet5 = new HashSet();
		Crew crew9 = new Crew();
		crew9.setName("Sinan Çetin");
		crew9.setPosition(Position.DIRECTOR);
		crew9.setMovie(movie5);
		crewSet5.add(crew9);
		Crew crew10 = new Crew();
		crew10.setName("Gülse Birsel");
		crew10.setPosition(Position.WRITER);
		crew10.setMovie(movie5);
		crewSet5.add(crew10);
		movie5.setCrew(crewSet5);

		Set actorSet5 = new HashSet<>();
		Role role7 = new Role();
		role7.setActor(actor1);
		role7.setRoleName("Şahika");
		role7.setMovie(movie5);
		role7.setDescription("Zengin şımarık");
		actorSet5.add(role7);
		movie5.setActors(actorSet5);

		movieRepository.save(movie5);
		
		User user9 = new User();
		user9.setUserName("innova");
		user9.setPassword("aaa");
		user9.setCustomerType(CustomerType.NORMAL);
		userRepository.save(user9);
		
	}
	
	/*
	 * @Test
	 * 
	 * @Rollback(false)
	 * 
	 * @Order(2) public void testUpdateUser() { User newUser =
	 * userRepository.getById((long) 1); newUser.setUserName("buket");
	 * newUser.setPassword("buket"); User dbUser = userRepository.save(newUser);
	 * 
	 * assertEquals(dbUser.getUserName(), newUser.getUserName());
	 * 
	 * }
	 * 
	 * @Test
	 * 
	 * @Order(3) public void testFindUserByName() { String userName = "buket"; User
	 * user = userRepository.findByUserName(userName);
	 * 
	 * assertEquals(userName, user.getUserName());
	 * 
	 * }
	 * 
	 * @Test
	 * 
	 * @Order(4) public void testFindAllUserByName() { String userName = "buket";
	 * List<User> userList = userRepository.findAll(); for (User user : userList) {
	 * System.out.println(user); } assertNotNull(userList); }
	 * 
	 * @Test
	 * 
	 * @Rollback(false)
	 * 
	 * @Order(5) public void testDeleteUser() { User user = new User();
	 * user.setId((long) 4); user.setUserName("deneme"); user.setPassword("deneme");
	 * User savedUser = userRepository.save(user);
	 * 
	 * Optional<User> userdbUser = userRepository.findById((long) 4);
	 * assertTrue(userdbUser.isPresent()); assertFalse(userdbUser.isEmpty());
	 * userRepository.delete(userdbUser.get());
	 * 
	 * assertFalse(userRepository.findById((long) 4).isPresent());
	 * 
	 * }
	 * 
	 * @Test public void testFindUserNamesStartWith() { List<User> users =
	 * userRepository.findUserNamesStartsWith((String) "b"); for (User user : users)
	 * { System.out.println(user); } assertNotNull(users); assertThat(users.size()
	 * == 2);
	 * 
	 * }
	 */
}
