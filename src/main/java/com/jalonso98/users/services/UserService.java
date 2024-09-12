package com.jalonso98.users.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.github.javafaker.Faker;
import com.jalonso98.users.models.User;

import jakarta.annotation.PostConstruct;

@Service
public class UserService {

	@Autowired
	private Faker faker;

	private List<User> users = new ArrayList<>();

	@PostConstruct
	public void init() {
		for (int i = 0; i < 10; i++) {
			users.add(new User(faker.funnyName().name(), faker.name().username(), faker.dragonBall().character()));
		}
	}

	public List<User> getUsers(String startsWith) {
		if(startsWith != null) {
			return users.stream().filter(u -> u.getUsername().startsWith(startsWith)).collect(Collectors.toList());
		}
		
		return users;
	}

	public User getUserByUsername(String username) {
		return users.stream().filter(u -> u.getUsername().equals(username)).findAny()
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
	}
	
	public User createUser(User user) {
		if(users.stream().anyMatch(u -> u.getUsername().equals(user.getUsername()))) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("User %s already exists", user.getUsername()));
		}
		users.add(user);
		return user;
	}
	
	public User updateUser(User user, String username) {
		User userToBeUpdated = getUserByUsername(username);
		userToBeUpdated.setNickname(user.getNickname());
		userToBeUpdated.setPassword(user.getPassword());
		return userToBeUpdated;
	}
	
	public void deleteUser(String username) {
		users.remove(getUserByUsername(username));
	}

}
