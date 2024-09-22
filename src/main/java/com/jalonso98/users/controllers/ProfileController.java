package com.jalonso98.users.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jalonso98.users.entities.Profile;
import com.jalonso98.users.services.ProfileService;

@RestController
@RequestMapping("/users/{userId}/profiles")
public class ProfileController {
	
	@Autowired
	private ProfileService profileService;
	
	@GetMapping("/{profileId}")
	public ResponseEntity<Profile> getByUserIdAndProfileId(@PathVariable Integer userId, @PathVariable Integer profileId){
		return new ResponseEntity<Profile>(profileService.getByUserIdAndProfileId(userId, profileId), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Profile> create(@RequestBody Profile profile, @PathVariable Integer userId){
		return new ResponseEntity<Profile>(profileService.create(profile, userId), HttpStatus.CREATED);
	}
	
}
