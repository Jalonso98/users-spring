package com.jalonso98.users.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.github.javafaker.Faker;
import com.jalonso98.users.entities.Role;
import com.jalonso98.users.repositories.RoleRepository;

import jakarta.annotation.PostConstruct;

@Service
public class RoleService {
	
	@Autowired
	private Faker faker;

	@Autowired
	private RoleRepository roleRepository;
	
	@PostConstruct
	public void init() {
		for (int i = 0; i < 10; i++) {
			roleRepository.save(new Role(faker.business().toString()));
		}
	}

	public List<Role> getRoles() {
		return (List<Role>) roleRepository.findAll();
	}

	public Role findRoleById(Integer id) {
		return roleRepository.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Role %d does not exist", id)));
	}

	public Role createRole(Role role) {
		return roleRepository.save(role);
	}

	public Role updateRole(Integer id, Role role) {
		if (!roleRepository.findById(id).isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Role %d does not exist", id));
		}
		role.setId(id);
		return roleRepository.save(role);
	}

	public void deleteRole(Integer id) {
		roleRepository.delete(roleRepository.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Role %d does not exist", id))));
	}

}
