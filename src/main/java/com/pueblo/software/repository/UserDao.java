package com.pueblo.software.repository;

import java.util.List;

import com.pueblo.software.model.User;

 
 
public interface UserDao {
    User findById(Long id);
     
    User findBySSO(String sso);
     
    void save(User user);
     
    void deleteBySSO(String sso);
     
    List<User> findAllUsers();
}