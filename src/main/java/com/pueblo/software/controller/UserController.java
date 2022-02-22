package com.pueblo.software.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pueblo.software.dto.AuthenticationRequest;
import com.pueblo.software.model.Book;
import com.pueblo.software.model.User;
import com.pueblo.software.model.UserRole;
import com.pueblo.software.security.CustomUserDetailsService;
import com.pueblo.software.security.JWTUtils;
import com.pueblo.software.service.BookService;
import com.pueblo.software.service.UserService;
import com.pueblo.software.service.UserServiceImpl;

@RestController
@CrossOrigin
public class UserController {
	
	@Autowired
	BookService bservice;
	
	@Autowired
	CustomUserDetailsService userDetails;

	@Autowired
	private UserService userService;

	@Autowired
	AuthenticationManager authenticationManager;
	
	@GetMapping("/hello")
	public List<Book> getNode() {
		return bservice.getBooksByName("JP2GMD");
	}
	
	
	@GetMapping("/secured")
	public String getSecured() {
		return "shhhh, it's secure....";
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> doLogin(@RequestBody AuthenticationRequest authenticationRequest) {
		
		try {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getLogin(), authenticationRequest.getPassword()));
		
		User user = userService.getUserByLogin(authenticationRequest.getLogin());
		
		HashMap<String, Object> claims = new HashMap<String, Object>();
		List<UserRole> userRoles = userService.getUserRolesByUser(user);
		List<String> roles = new ArrayList<>();

		for(UserRole userProfile : userRoles){
			roles.add("ROLE_"+userProfile.getType().name());
		}

		claims.put("role", roles);
		
		byte[] secret = null;
		try {
			secret = userService.getSecretForUser(user);
		} catch (Exception e) {
			ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);	
			}
		String token = JWTUtils.createToken(claims, authenticationRequest.getLogin(), secret);
		
		
		return ResponseEntity.ok(token);
		
		}catch (BadCredentialsException e) {
			return new ResponseEntity<>("Error while authenticating", HttpStatus.UNAUTHORIZED);
		}
		
	}
	
	@GetMapping("/api/homescreen")
	public String getHomescreen() {
		return "homescreen data";
	}
}
