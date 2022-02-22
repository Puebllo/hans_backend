package com.pueblo.software.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pueblo.software.model.User;
import com.pueblo.software.model.UserRole;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
}
