package com.jalonso98.users.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.jalonso98.users.entities.Address;
import com.jalonso98.users.entities.Profile;
import com.jalonso98.users.repositories.AddressRepository;
import com.jalonso98.users.repositories.ProfileRepository;

@Service
public class AddressService {

	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	
	public List<Address> getAddressesByUserIdAndProfileId(Integer userId, Integer profileId){
		return addressRepository.findByUserIdAndProfileId(userId, profileId);
	}
	
	public Address createAddress(Address address, Integer userId, Integer profileId) {
		Optional<Profile> profile = profileRepository.findByUserIdAndProfileId(userId, profileId);
		if(!profile.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Profile %d not found for user %d", profileId, userId));
		}
		address.setProfile(profile.get());
		return addressRepository.save(address);
	}
	
}
