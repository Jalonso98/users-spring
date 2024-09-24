package com.jalonso98.users.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jalonso98.users.entities.Role;
import com.jalonso98.users.models.AuditDetails;
import com.jalonso98.users.repositories.RoleRepository;

import jakarta.annotation.PostConstruct;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private KafkaTemplate<Integer, String> kafkaTemplate;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@PostConstruct
	public void init() {
		roleRepository.save(new Role("ROLE_USER"));
		roleRepository.save(new Role("ROLE_GUEST"));
	}

	public List<Role> getRoles() {
		return (List<Role>) roleRepository.findAll();
	}

	public Role findRoleById(Integer id) {
		return roleRepository.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Role %d does not exist", id)));
	}

	public Role createRole(Role role) {
		Role roleCreated = roleRepository.save(role);
		AuditDetails auditDetails = new AuditDetails(SecurityContextHolder.getContext().getAuthentication().getName(), roleCreated.getName());
		try {
			kafkaTemplate.send("users-app",objectMapper.writeValueAsString(auditDetails));
		} catch (JsonProcessingException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ("Error parsing the message: " + e.getMessage()));
		}
		return roleCreated;
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
