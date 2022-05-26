package com.fundamentosplatzi.springboot.fundamentos;

import com.fundamentosplatzi.springboot.fundamentos.bean.MyBean;
import com.fundamentosplatzi.springboot.fundamentos.bean.MyBeanWithDependency;
import com.fundamentosplatzi.springboot.fundamentos.bean.MyBeanWithProperties;
import com.fundamentosplatzi.springboot.fundamentos.component.ComponentDependency;
import com.fundamentosplatzi.springboot.fundamentos.entity.User;
import com.fundamentosplatzi.springboot.fundamentos.pojo.UserPojo;
import com.fundamentosplatzi.springboot.fundamentos.repository.UserRepository;
import com.fundamentosplatzi.springboot.fundamentos.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class FundamentosApplication implements CommandLineRunner{

	private final Log LOGGER = LogFactory.getLog(FundamentosApplication.class);
	private final ComponentDependency componentDependency;
	private final MyBean myBean;
	private final MyBeanWithDependency myBeanWithDependency;
	private final MyBeanWithProperties myBeanWithProperties;
	private final UserPojo userPojo;
	private final UserRepository userRepository;
	private final UserService userService;

	public  FundamentosApplication(
			@Qualifier("componentTwoImplement") ComponentDependency componentDependency,
			MyBean myBean,
			MyBeanWithDependency myBeanWithDependency,
			MyBeanWithProperties myBeanWithProperties,
			UserPojo userPojo,
			UserRepository userRepository,
			UserService userService
	){
		this.componentDependency =componentDependency;
		this.myBean = myBean;
		this.myBeanWithDependency = myBeanWithDependency;
		this.myBeanWithProperties = myBeanWithProperties;
		this.userPojo = userPojo;
		this.userRepository = userRepository;
		this.userService = userService;
	}
	public static void main(String[] args) {
		SpringApplication.run(FundamentosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		ejemplosAnteriore();
		saveUsersInDatabase();
		getInformationJpqlFromUser();
		saveWithErrorTransactional();
	}

	private void getInformationJpqlFromUser(){
//		LOGGER.info("User getting with method findByUserEmail :: " +
//				userRepository.findByUserEmail("armando@mail.com")
//						.orElseThrow(()-> new RuntimeException("No exist User")));
//
//		userRepository.findAndSort("A", Sort.by("id").descending())
//				.stream()
//				.forEach(user -> LOGGER.info("User getting with method findAndSort :: " + user));
//
//		userRepository.findByName("Armando")
//				.stream()
//				.forEach(user -> LOGGER.info("user with findByName query method :: " + user));
//
//		LOGGER.info("User with findByEmailAndName query method :: " + userRepository.findByEmailAndName("juan@mail.com","Juan")
//				.orElseThrow(()-> new RuntimeException("User no exist with findByEmailAndName")));
//
//		userRepository.findByNameLike("%A%").stream().forEach(user -> LOGGER.info("User found with findByNameLike method :: " + user));
//
//		userRepository.findByNameOrEmail(null,"agustin@mail.com").stream().forEach(user -> LOGGER.info("User found with findByNameOrEmail method :: " + user));
//
//		userRepository.findByBirthDateBetween(LocalDate.of(1980,01,01),LocalDate.of(1982,01,01)).stream().forEach(user -> LOGGER.info("User found with findByBirthDateBetween method :: " + user));
//
//		userRepository.findByNameLikeOrderByIdDesc("%A%").stream().forEach(user -> LOGGER.info("User found with findByNameLikeOrderByIdDesc :: " + user));
//
//		userRepository.findByNameContainingOrderByIdAsc("A").stream().forEach(user -> LOGGER.info("User found with findByNameContainingOrderByIdDesc :: " + user));

		LOGGER.info("User getting with getAllByBirthdateAndEmail method :: " +
				userRepository.getAllByBirthdateAndEmail(LocalDate.of(1980,01,01),"juan@mail.com")
						.orElseThrow(()-> new RuntimeException("User Not Found")));

	}

	private void saveWithErrorTransactional(){
		User test1 = new User("test1","test1@mail.com",LocalDate.of(1980,02,02));
		User test2 = new User("test2","test2@mail.com",LocalDate.of(1985,02,02));
		User test3 = new User("test3","test1@mail.com",LocalDate.of(1986,02,02));
		User test4 = new User("test4","test4@mail.com",LocalDate.of(1987,02,02));
		List<User> list = Arrays.asList(test1, test2, test3, test4);
		try {
			userService.saveTransactional(list);
		}catch (Exception ex){
			LOGGER.error("Error Transactional :: " + ex.getMessage());
		}

		userService.getAllUsers().stream().forEach(user -> LOGGER.info("User found with getAllUsers method :: " + user));
	}

	private void saveUsersInDatabase(){
		User user1 = new User("Juan","juan@mail.com", LocalDate.of(1980,01,01));
		User user2 = new User("Andres","andres@mail.com", LocalDate.of(1981,01,01));
		User user3 = new User("Armando","armando@mail.com", LocalDate.of(1982,01,01));
		User user4 = new User("Agustin","agustin@mail.com", LocalDate.of(1983,01,01));
		User user5 = new User("Aldo","aldo@mail.com", LocalDate.of(1984,01,01));
		List<User> list = Arrays.asList(user1, user2, user3, user4, user5);
		list.stream().forEach(userRepository::save);


	}

	private void ejemplosAnteriore(){
		componentDependency.saludar();
		myBean.print();
		myBeanWithDependency.printWithDependency();
		System.out.println(myBeanWithProperties.function());
		System.out.println(userPojo.getUser() + " - " + userPojo.getPassword());

		try {
			int value = 10/0;
			LOGGER.info("Mi valor es: " + value);
		}catch (Exception ex){
			LOGGER.error("Esto es un error: " + ex.getMessage());
		}
	}
}
