package com.pueblo.software.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.pueblo.software.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultJwtParser;

public class JWTFilter extends BasicAuthenticationFilter {

	private static final Logger LOG = Logger.getLogger(JWTFilter.class);



	private UserService userService;

	public JWTFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		getUserService(request);
		String header = request.getHeader("Authorization");
		UsernamePasswordAuthenticationToken authResult = getAuthenticationByToken(header);
		SecurityContextHolder.getContext().setAuthentication(authResult);
		chain.doFilter(request, response);
	}

	private void getUserService(HttpServletRequest request) {
		if(userService==null){
			ServletContext servletContext = request.getServletContext();
			WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			userService = webApplicationContext.getBean(UserService.class);
		}		
	}

	private UsernamePasswordAuthenticationToken getAuthenticationByToken(String header) {

		String login = getLoginFromToken(header);
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		byte[] secret;
		try {
			secret = userService.getSecretByLogin(login);

			Jws<Claims> claimsJws = null;
			try {
				claimsJws = Jwts.parser()
						.setSigningKey(secret)
						.parseClaimsJws(header.replace("Bearer ", ""));
			} catch (ExpiredJwtException e) {
				userService.removeSecretByLogin(e.getClaims().getSubject());
				throw e;
			} 

			String role = claimsJws.getBody().get("role").toString();

			role = role.replace("[", "");
			role = role.replace("]", "");

			String roles[] = role.split(",");
			for (String rrole : roles) {
				rrole = rrole.replaceAll("\\s+", "");
				authorities.add(new SimpleGrantedAuthority(rrole));
			}

		} catch (Exception e1) {
			LOG.error("Error while getting secret by Login");
		}

		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(login, null, authorities);
		return usernamePasswordAuthenticationToken;	}


	private String getLoginFromToken(String token) {
		String[] splitToken = token.replace("Bearer ", "").split("\\.");
		String unsignedToken = splitToken[0] + "." + splitToken[1] + ".";

		DefaultJwtParser parser = new DefaultJwtParser();
		Jwt<?, ?> jwt = null;
		try {
			jwt = parser.parse(unsignedToken);
		} catch (ExpiredJwtException e) {
			userService.removeSecretByLogin(e.getClaims().getSubject());
			throw e;
		} 
		Claims claims = (Claims) jwt.getBody();
		return claims.getSubject();
	}

}