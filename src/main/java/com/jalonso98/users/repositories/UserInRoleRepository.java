package com.jalonso98.users.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.jalonso98.users.entities.Role;
import com.jalonso98.users.entities.UserInRole;

public interface UserInRoleRepository extends CrudRepository<UserInRole, Integer> {
	
	@Query("SELECT ur.role FROM UserInRole ur WHERE ur.user.id=?1")
	List<Role> findByUserId(Integer userId);
	
}