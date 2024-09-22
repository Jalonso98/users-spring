package com.jalonso98.users.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.jalonso98.users.entities.Profile;
import com.jalonso98.users.entities.User;
import com.jalonso98.users.repositories.ProfileRepository;
import com.jalonso98.users.repositories.UserRepository;

@Service
public class ProfileService {
	
	@Autowired
	private ProfileRepository profileRepository;
	
	
	@Autowired
	private UserRepository userRepository;
	
	public Profile getByUserIdAndProfileId(Integer userId, Integer profileId) {
		return profileRepository.findByUserIdAndProfileId(userId, profileId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Profile %d not found for User %d", profileId, userId)));
	}

	public Profile create(Profile profile, Integer userId) {
		Optional<User> user = userRepository.findById(userId);
		if(!user.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User %d not found", userId));
		}
		profile.setUser(user.get());
		return profileRepository.save(profile);
	}
	
}
