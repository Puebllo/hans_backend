package com.pueblo.software.util;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class QuickPasswordEncodingGenerator {

	public static void main(String[] args) { 
		String password = "pwdtest";
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		System.out.println(passwordEncoder.encode(password)); 
		
	
		/* if (BCrypt.checkpw(password,
		 * "$2a$10$DUMXf3GWS38vWVV5Fa4DVuhpZRqXurGndesBoH4cneZvUwu/s7nVq")) {
		 * System.out.println("Node verified !");
		 * }
		 * else {
		 * System.out.println("Invalid credentials for node. Access denied !!!");
		 * } */
	}

}
