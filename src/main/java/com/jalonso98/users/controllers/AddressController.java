package com.jalonso98.users.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jalonso98.users.entities.Address;
import com.jalonso98.users.services.AddressService;

@RestController
@RequestMapping("/users/{userId}/profiles/{profileId}/addresses")
public class AddressController {

	@Autowired
	private AddressService addressService;
	
	@GetMapping
	public ResponseEntity<List<Address>> getAddressesByUserIdAndProfileId(@PathVariable Integer userId, @PathVariable Integer profileId){
		return new ResponseEntity<List<Address>>(addressService.getAddressesByUserIdAndProfileId(userId, profileId), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Address> createAddress(@PathVariable Integer userId, @PathVariable Integer profileId, @RequestBody Address address){
		return new ResponseEntity<Address>(addressService.createAddress(address, userId, profileId), HttpStatus.CREATED);
	}
	
}
