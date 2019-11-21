package com.crm.jwt;

import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Component
public class JwtTokenUtil implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9144177202888900937L;

	public static final long JWT_TOKEN_VALIDITY = 2*60*60;
	
	@Value("${jwt.secret}")
	private String secret;

	// retrieve username from jwt token
	public String getUsernameFromToken(String token){
		return getClaimFromToken(token, Claims::getSubject);
	}

	// retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token){
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver){
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	// for retrieveing any information from token we will need the secret key
	private Claims getAllClaimsFromToken(String token){
		try {
			Base64.Decoder decoder = Base64.getDecoder();
			return Jwts.parser().setSigningKey(decoder.decode(secret)).parseClaimsJws(token).getBody();
		} catch (SignatureException e) {
			throw new SignatureException("TOKEN簽章錯誤!");
		} catch (MalformedJwtException e) {
			throw new MalformedJwtException("TOKEN格式錯誤!");
		}catch (ExpiredJwtException e) {
			throw new ExpiredJwtException(null,null,"TOKEN已經過期!");
		}
	}

	// check if the token has expired
	private Boolean isTokenExpired(String token) throws ExpiredJwtException {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	// generate token for user
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails);
	}

	// while creating the token -
	// 1. Define claims of the token, like Issuer, Expiration, Subject, and the ID
	// 2. Sign the JWT using the HS512 algorithm and secret key.
	// 3. According to JWS Compact
	// Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
	// compaction of the JWT to a URL-safe string
	private String doGenerateToken(Map<String, Object> claims, UserDetails userDetails) {
		Base64.Decoder decoder = Base64.getDecoder();
		String[] authoritiesArray = userDetails.getAuthorities().stream()
				.map(a -> a.getAuthority()).toArray(String[]::new);
		
		return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
				.claim("roles", authoritiesArray).setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, decoder.decode(secret)).compact();
	}

	// validate token
	public Boolean validateToken(String token, UserDetails userDetails) throws Exception {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
