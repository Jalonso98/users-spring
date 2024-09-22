package com.jalonso98.users.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.jalonso98.users.entities.UserInRole;

public interface UserInRoleRepository extends CrudRepository<UserInRole, Integer> {
	
	@Query("SELECT ur FROM UserInRole ur WHERE ur.user.id=?1")
	List<UserInRole> findByUserId(Integer userId);
	
}