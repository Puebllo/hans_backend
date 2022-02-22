package com.pueblo.software.service;

import java.util.List;

import com.pueblo.software.model.User;
import com.pueblo.software.model.UserRole;

public interface UserService {

	User getUserByLogin(String login);

	List<UserRole> getUserRolesByUser(User user);

	byte[] getSecretForUser(User user) throws Exception;
	
	byte[] getSecretByLogin(String login) throws Exception;

	void removeSecretByLogin(String login);


}
