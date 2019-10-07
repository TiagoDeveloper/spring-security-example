package com.example.springsecurity.filters;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	public static final String JWT_SECRET = "n2r5u8x/A%D*G-KaPdSgVkYp3s6v9y$B&E(H+MbQeThWmZq4t7w!z%C*F-J@NcRf";
	public static final String TOKEN_HEADER = "Authorization";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String TOKEN_TYPE = "JWT";
	public static final String TOKEN_ISSUER = "secure-api";
	public static final String TOKEN_AUDIENCE = "secure-app";
	//parametrizar esses caras aqui de cima
	private AuthenticationManager authenticationManager;
	
	public AuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)throws AuthenticationException {

		String header = request.getHeader("Authorization");

		if (header == null || !header.toLowerCase().startsWith("basic ")) {
			throw new AuthenticationServiceException("Authentication FAIL");
		}
		
		try {
			String[] tokens = extractAndDecodeHeader(header);
			assert tokens.length == 2;

			String username = tokens[0];


				UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
						username, tokens[1]);
				authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
				Authentication authResult = this.authenticationManager.authenticate(authRequest);


				SecurityContextHolder.getContext().setAuthentication(authResult);
				
				return authResult;

		}catch (AuthenticationException | IOException failed) {
			throw new RuntimeException(failed);
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
		
		 User user = ((User) authResult.getPrincipal());
	        
	        List<String> roles = user.getAuthorities()
	                .stream()
	                .map(GrantedAuthority::getAuthority)
	                .collect(Collectors.toList());
	        
	        byte[] signingKey = JWT_SECRET.getBytes();
	        String token = Jwts.builder()
	        		.signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
	        		.setHeaderParam("typ", TOKEN_TYPE)
	        		.setIssuer(TOKEN_ISSUER)
	        		.setAudience(TOKEN_AUDIENCE)
	        		.setSubject(user.getUsername())
	                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong("3600000")))
	                .claim("rol", roles)
	                .compact();
	   
	        response.addHeader("Authorization","Bearer "+token);
	        response.addHeader("username",user.getUsername());
		chain.doFilter(request, response);
	}
	
	private String[] extractAndDecodeHeader(String header)throws IOException {

		byte[] base64Token = header.substring(6).getBytes("UTF-8");
		byte[] decoded;
		try {
			decoded = Base64.getDecoder().decode(base64Token);
		}
		catch (IllegalArgumentException e) {
			throw new BadCredentialsException(
					"Failed to decode basic authentication token");
		}

		String token = new String(decoded, "UTF-8");

		int delim = token.indexOf(":");

		if (delim == -1) {
			throw new BadCredentialsException("Invalid basic authentication token");
		}
		return new String[] { token.substring(0, delim), token.substring(delim + 1) };
	}
}
