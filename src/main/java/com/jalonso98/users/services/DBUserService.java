package com.jalonso98.users.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.github.javafaker.Faker;
import com.jalonso98.users.entities.User;
import com.jalonso98.users.repositories.UserRepository;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;

@Service
public class DBUserService {
	
	@Autowired
	private Faker faker;
	
	@Resource
	private UserRepository userRepository;

	@PostConstruct
	public void init() {
		for (int i = 0; i < 1000; i++) {
			User user = new User();
			user.setUsername(faker.name().username());
			user.setPassword(faker.crypto().md5());
			userRepository.save(user);
		}
	}
	
	public User getUserById(Integer id) {
		return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
	}
	
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
		return userRepository.findByUsernameAndPassword(username, password).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
	}
	
	public User createUser(User user) {
		return userRepository.save(user);
	}
	
	public User updateUser(User user, Integer id) {
		User dbUser = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
		dbUser.setUsername(user.getUsername());
		dbUser.setPassword(user.getPassword());
		return userRepository.save(dbUser);
	}
	
	public void deleteUser(Integer id) {
		userRepository.delete(userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")));
	}

}
