package com.jalonso98.users.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jalonso98.users.models.User;
import com.jalonso98.users.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<List<User>> getUsers(@RequestParam(name = "startsWith", required = false) String startsWith){
		return new ResponseEntity<List<User>>(userService.getUsers(startsWith), HttpStatus.OK);
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<User> getUserByUsername(@PathVariable final String username){
		return new ResponseEntity<User>(userService.getUserByUsername(username), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user) {
		return new ResponseEntity<User>(userService.createUser(user), HttpStatus.CREATED);
	}
	
	@PutMapping("/{username}")
	public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable String username){
		return new ResponseEntity<User>(userService.updateUser(user, username), HttpStatus.OK);
	}
	
	@DeleteMapping("/{username}")
	public ResponseEntity<Void> deleteUser(@PathVariable final String username){
		userService.deleteUser(username);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
