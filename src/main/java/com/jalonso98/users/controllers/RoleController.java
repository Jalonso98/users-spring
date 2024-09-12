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
import org.springframework.web.bind.annotation.RestController;

import com.jalonso98.users.entities.Role;
import com.jalonso98.users.services.RoleService;

@RestController
@RequestMapping(path = "/roles")
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@GetMapping
	public ResponseEntity<List<Role>> getRoles(){
		return new ResponseEntity<List<Role>>(roleService.getRoles(),HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Role> getRoleById(@PathVariable Integer id){
		return new ResponseEntity<Role>(roleService.findRoleById(id), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Role> createRole(@RequestBody Role role){
		return new ResponseEntity<Role>(roleService.createRole(role), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Role> updateRole(@PathVariable Integer id, @RequestBody Role role){
		return new ResponseEntity<Role>(roleService.updateRole(id, role), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteRole(@PathVariable Integer id){
		roleService.deleteRole(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
}
