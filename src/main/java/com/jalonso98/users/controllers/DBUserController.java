package com.jalonso98.users.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import com.jalonso98.users.entities.User;
import com.jalonso98.users.services.DBUserService;

import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/db-users")
@Tag(name = "DBUser", description = "Operations related to users stored in DB")
public class DBUserController {

	@Autowired
	private DBUserService userService;

	@GetMapping()
	@Timed(value = "get.users", description = "Time taken to return Gets")
	@Operation(summary = "Get a list of users", description = "This endpoint returns a list of users", responses = {
			@ApiResponse(responseCode = "200", description = "List of users", content = @Content(mediaType = "application/json",
					// schema = @Schema(implementation = User.class)
					examples = { @ExampleObject(name = "List of users") })) })
	public ResponseEntity<Page<User>> getUsers(
			@RequestParam(required = false, value = "page", defaultValue = "0") int page,
			@RequestParam(required = false, value = "size", defaultValue = "10") int size) {
		return new ResponseEntity<Page<User>>(userService.getUsers(page, size), HttpStatus.OK);
	}

	@GetMapping("usernames")
	public ResponseEntity<Page<String>> getUsernames(
			@RequestParam(required = false, value = "page", defaultValue = "0") int page,
			@RequestParam(required = false, value = "size", defaultValue = "100") int size) {
		return new ResponseEntity<Page<String>>(userService.getUsernames(page, size), HttpStatus.OK);
	}

	@GetMapping("/username/{username}")
	@Operation(summary = "Get a user from DB", description = "This endpoint returns a db-user", responses = {
			@ApiResponse(responseCode = "200", description = "DB-user", content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = User.class),
					examples = { @ExampleObject(name = "DBUser", value = "{\"id\": 1, \"username\": \"mana.kautzer\", \"password\": \"5847f06fe11cc315c12c7078f7740e55\", \"profile\": null}") })) })
	public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
		return new ResponseEntity<User>(userService.getUserByUsername(username), HttpStatus.OK);
	}

	@PostMapping("/auth")
	public ResponseEntity<User> getUserByUsernameAndPassword(@RequestBody User user) {
		return new ResponseEntity<User>(
				userService.getUserByUsernameAndPassword(user.getUsername(), user.getPassword()), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable final Integer id) {
		return new ResponseEntity<User>(userService.getUserById(id), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user) {
		return new ResponseEntity<User>(userService.createUser(user), HttpStatus.CREATED);
	}

	@PutMapping("/{username}")
	public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable Integer id) {
		return new ResponseEntity<User>(userService.updateUser(user, id), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable final Integer id) {
		userService.deleteUser(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
