package com.pueblo.software.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationRequest implements Serializable{ 

	private static final long serialVersionUID = 8646374741477234563L;
	
	String login;
	String password;
}
