package com.jalonso98.users.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.jalonso98.users.entities.Role;
import com.jalonso98.users.repositories.RoleRepository;

import jakarta.annotation.PostConstruct;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	@PostConstruct
	public void init() {
		roleRepository.save(new Role("ADMIN"));
		roleRepository.save(new Role("USER"));
		roleRepository.save(new Role("GUEST"));
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
