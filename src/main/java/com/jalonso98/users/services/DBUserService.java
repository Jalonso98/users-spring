package com.jalonso98.users.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.github.javafaker.Faker;
import com.jalonso98.users.entities.Role;
import com.jalonso98.users.entities.User;
import com.jalonso98.users.entities.UserInRole;
import com.jalonso98.users.repositories.RoleRepository;
import com.jalonso98.users.repositories.UserInRoleRepository;
import com.jalonso98.users.repositories.UserRepository;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;

@Service
public class DBUserService {
	
	@Autowired
	private Faker faker;
	
	private static final Logger log = LoggerFactory.getLogger(DBUserService.class);

	@Resource
	private UserRepository userRepository;
	
	@Resource
	private UserInRoleRepository userInRoleRepository;
	
	@Resource 
	private RoleRepository roleRepository;
	
	@PostConstruct
	public void init() {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		User firstUser = new User();
		firstUser.setUsername("admin");
		firstUser.setPassword(encoder.encode("admin"));
		userRepository.save(firstUser);
		
		UserInRole userInRole = new UserInRole();
		userInRole.setUser(firstUser);
		roleRepository.save(new Role("ROLE_ADMIN"));
		userInRole.setRole(roleRepository.findById(1).get());
		userInRoleRepository.save(userInRole);
		
		for (int i = 0; i < 999; i++) {
			User user = new User();
			user.setUsername(faker.name().username());
			user.setPassword(encoder.encode(("user"+(i+2))));
			userRepository.save(user);
		}
	}
	
	@Cacheable("users")
	public User getUserById(Integer id) {
		log.info("Getting ser by id {}", id);
		return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
	}
	
	@Cacheable("users")
	public Page<User> getUsers(int page, int size){
		return userRepository.findAll(PageRequest.of(page, size));
	}
	
	public Page<String> getUsernames(int page, int size){
		return userRepository.findUsernames(PageRequest.of(page, size));
	}
	
	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
	}
	
	public User getUserByUsernameAndPassword(String username, String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return userRepository.findByUsernameAndPassword(username, encoder.encode(password)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
	}
	
	public User createUser(User user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
	
	public User updateUser(User user, Integer id) {
		User dbUser = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
		dbUser.setUsername(user.getUsername());
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		dbUser.setPassword(encoder.encode(user.getPassword()));
		return userRepository.save(dbUser);
	}
	
	@CacheEvict("users")
	public void deleteUser(Integer id) {
		userRepository.delete(userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")));
	}
	
}
