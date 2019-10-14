package com.example.springsecurity.services;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

public class JwtService {

	public static final String JWT_SECRET = "n2r5u8x/A%D*G-KaPdSgVkYp3s6v9y$B&E(H+MbQeThWmZq4t7w!z%C*F-J@NcRf";
	public static final String TOKEN_HEADER = "Authorization";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String TOKEN_TYPE = "JWT";
	public static final String TOKEN_ISSUER = "secure-api";
	public static final String TOKEN_AUDIENCE = "secure-app";

	public String createToken(User user, List<String> roles) {
		byte[] signingKey = JWT_SECRET.getBytes();
		String token = Jwts.builder().signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
				.setHeaderParam("typ", TOKEN_TYPE).setIssuer(TOKEN_ISSUER).setAudience(TOKEN_AUDIENCE)
				.setSubject(user.getUsername())
				.setExpiration(new Date(System.currentTimeMillis() + Long.parseLong("3600000"))).claim("rol", roles)
				.compact();
		return token;
	}

	public UsernamePasswordAuthenticationToken checkToken(String token) {
		try {
			byte[] signingKey = JWT_SECRET.getBytes();

			Jws<Claims> parsedToken = Jwts.parser().setSigningKey(signingKey)
					.parseClaimsJws(token.replace("Bearer ", ""));

			String username = parsedToken.getBody().getSubject();

			List<SimpleGrantedAuthority> authorities = ((List<?>) parsedToken.getBody().get("rol")).stream()
					.map(authority -> new SimpleGrantedAuthority((String) authority)).collect(Collectors.toList());

			if (!StringUtils.isEmpty(username)) {
				return new UsernamePasswordAuthenticationToken(username, null, authorities);
			}
		} catch (ExpiredJwtException exception) {
		} catch (UnsupportedJwtException exception) {
		} catch (MalformedJwtException exception) {
		} catch (SignatureException exception) {
		} catch (IllegalArgumentException exception) {
		}
		return null;
	}

}
