package com.jalonso98.users.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.jalonso98.users.entities.Role;
import com.jalonso98.users.entities.User;
import com.jalonso98.users.repositories.UserInRoleRepository;
import com.jalonso98.users.repositories.UserRepository;

import jakarta.annotation.Resource;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Resource
	private UserRepository userRepository;

	@Resource
	private UserInRoleRepository userInRoleRepository;

	private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

	@Override
	 public UserDetails loadUserByUsername(String username) throws ResponseStatusException {
		
	        Optional<User> user = userRepository.findByUsername(username);
	        if (user.isEmpty()) {
	            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(("User not found with username: " + username)));
	        }
	        
	        log.info(user.get().getUsername());
	        log.info(user.get().getPassword());
	        
	     // Convertir roles en una colección de GrantedAuthority
	        List<Role> dbRoles = userInRoleRepository.findByUserId(user.get().getId());
	        String[] rolesArray = dbRoles.stream()
	        		.map(Role::getName)
	        		.toArray(String[]::new);
	        List<GrantedAuthority> authorities = Arrays.stream(rolesArray)
	                .map(SimpleGrantedAuthority::new)
	                .collect(Collectors.toList());

	        	for (int i = 0; i < rolesArray.length; i++) {
					log.info("rolesArray: " + rolesArray[i]);
				}
	        	
	        	for (GrantedAuthority grantedAuthority : authorities) {
					log.info("authorities: " + grantedAuthority.toString());
				}

	        // Devolver un objeto UserDetails que Spring Security usará para la autenticación
	        return new org.springframework.security.core.userdetails.User(
	                user.get().getUsername(),
	                user.get().getPassword(),
	                authorities
	                //new ArrayList<>()
	        );
	    }

}
