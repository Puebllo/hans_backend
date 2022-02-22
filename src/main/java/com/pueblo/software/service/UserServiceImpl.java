package com.pueblo.software.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pueblo.software.model.User;
import com.pueblo.software.model.UserRole;
import com.pueblo.software.model.UserRoleUserIndirect;
import com.pueblo.software.repository.UserRepository;

@Service("userService")
public class UserServiceImpl implements UserService{
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	UserRepository repo;
	
	private HashMap<User,byte[]> userSecretByUserHM = new HashMap<>();
	
	@Override
	public User getUserByLogin(String login) {
		Query q = entityManager.createQuery("SELECT u FROM " + User.class.getSimpleName() + " u WHERE u."+User.LOGIN + " =:login");
		q.setParameter("login", login);
		return (User) q.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserRole> getUserRolesByUser(User user) {
		List<UserRole> toReturn = new ArrayList<UserRole>();
		Query q = entityManager.createQuery("SELECT ur FROM " + UserRoleUserIndirect.class.getSimpleName() + " ur WHERE ur."+UserRoleUserIndirect.USER+ " =:user");
		q.setParameter("user", user);
		List<UserRoleUserIndirect> uruiList = q.getResultList();
		toReturn = uruiList.stream().map(urui -> urui.getUserRole()).collect(Collectors.toList());
		return toReturn;
	}

	public byte[] getSecretForUser(User user) throws Exception{
		
		if (userSecretByUserHM.get(user)!=null) {
			return userSecretByUserHM.get(user);
		}else {
			
			String secret = user.getLogin()+"!"+user.getId()+RandomStringUtils.randomAlphanumeric(10);

			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] encodedhash = digest.digest(secret.getBytes(StandardCharsets.UTF_8));
			userSecretByUserHM.put(user, encodedhash);
			return encodedhash;
		}
	}
	
	private void removeSecretForUser(User user) {
		userSecretByUserHM.remove(user);
	}

	public byte[] getSecretByLogin(String login) throws Exception{
		return getSecretForUser(getUserByLogin(login));
	}

	public void removeSecretByLogin(String login) {
		removeSecretForUser(getUserByLogin(login));
		
	}

}
