package com.jalonso98.users.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.jalonso98.users.entities.Role;
import com.jalonso98.users.entities.User;
import com.jalonso98.users.entities.UserInRole;
import com.jalonso98.users.repositories.RoleRepository;
import com.jalonso98.users.repositories.UserInRoleRepository;
import com.jalonso98.users.repositories.UserRepository;

@Service
public class UserInRoleServices {

	@Autowired
	private UserInRoleRepository userInRoleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	public UserInRole assingRole(Integer userId, Integer roleId) {
		Optional<User> resultUser = userRepository.findById(userId);
		Optional<Role> resultRole = roleRepository.findById(roleId);
		if (resultUser.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User %d not found", userId));
		} else if (resultRole.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Role %d not found", roleId));
		} else {
			UserInRole userInRole = new UserInRole();
			userInRole.setUser(resultUser.get());
			userInRole.setRole(resultRole.get());
			return userInRoleRepository.save(userInRole);
		}
	}
	
	public List<UserInRole> getRolesByUserId(Integer userId){
		return userInRoleRepository.findByUserId(userId);
	}
	
}