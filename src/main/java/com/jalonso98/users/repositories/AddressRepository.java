package com.jalonso98.users.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.jalonso98.users.entities.Address;

public interface AddressRepository extends CrudRepository<Address, Integer>{

	@Query("SELECT a from Address a WHERE a.profile.id=?2 AND a.profile.user.id=?1")
	List<Address> findByUserIdAndProfileId(Integer userId, Integer profileId);
	
}
