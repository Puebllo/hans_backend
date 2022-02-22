package com.pueblo.software.security;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pueblo.software.model.User;
import com.pueblo.software.model.UserRole;
import com.pueblo.software.service.UserServiceImpl;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService{

	static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

	@Autowired
	private UserServiceImpl userService;

	@Transactional(readOnly=true)
	@Override
	public UserDetails loadUserByUsername(String login)
			throws UsernameNotFoundException {
		User user = userService.getUserByLogin(login);
		//logger.info("User : {}", user);
		if(user==null){
			logger.info("User not found");
			throw new UsernameNotFoundException("Username not found");
		}
		List<GrantedAuthority> authList = getGrantedAuthorities(user);
		return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), user.isUserEnabled, user.isAccountNonExpired, user.areCredentialsNonExpired, user.isUserLocked,authList);
	}


	//@Transactional(readOnly=true)
	public List<GrantedAuthority> getGrantedAuthorities(User user){
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		List<UserRole> userRoles = userService.getUserRolesByUser(user);

		for(UserRole userProfile : userRoles){
		//	logger.info("UserProfile : {}", userProfile);
			authorities.add(new SimpleGrantedAuthority("ROLE_"+userProfile.getType().name()));
		}

		//logger.info("authorities : {}", authorities);
		return authorities;
	}
}