package com.jalonso98.users.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jalonso98.users.entities.UserInRole;
import com.jalonso98.users.services.UserInRoleServices;

@RestController
@RequestMapping("/users/{userId}/roles")
public class UserInRoleController {

	@Autowired
	private UserInRoleServices userInRoleServices;

	@PostMapping("/{roleId}")
	public ResponseEntity<UserInRole> assingRole(@PathVariable("userId") Integer userId,
			@PathVariable("roleId") Integer roleId) {

		return new ResponseEntity<UserInRole>(userInRoleServices.assingRole(userId, roleId), HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<UserInRole>> getRolesByUserId(@PathVariable("userId") Integer userId){
		return new ResponseEntity<List<UserInRole>>(userInRoleServices.getRolesByUserId(userId), HttpStatus.OK);
	}

}