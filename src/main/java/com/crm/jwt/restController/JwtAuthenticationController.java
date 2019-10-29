package com.crm.jwt.restController;

import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crm.common.HttpStatus;
import com.crm.common.Result;
import com.crm.jwt.JwtTokenUtil;
import com.crm.jwt.JwtUserDetailsService;
import com.crm.jwt.model.JwtRequest;
import com.crm.jwt.model.UserInfo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@Slf4j
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private JwtUserDetailsService userDetailsService;

	private Base64 base64 = new Base64();
	@Value("${jwt.secret}")
	private String secret;
	
	@PostMapping("/user/logout")
	public ResponseEntity<Result<String>> logout(){
		log.info("/logout");
		return ResponseEntity.ok(new Result<String>(HttpStatus.ok,null,"success")); 
	}
	
	@GetMapping("/getUserInfo")
	public ResponseEntity<Result<UserInfo>> getToken(@RequestParam("token") String token){
		
		Claims claims = Jwts.parser().setSigningKey(base64.decode(secret)).parseClaimsJws(token.replace("Bearer ", "")).getBody();
	 
		List<String> roles = (List<String>) claims.get("roles");
		
		UserInfo userInfo = new UserInfo();
		userInfo.setName(claims.getSubject());
		userInfo.setRoles(roles);
		userInfo.setIntroduction("I hate U!");
		userInfo.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
		return ResponseEntity.ok(new Result<UserInfo>(HttpStatus.ok,userInfo));
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<Result<String>> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new Result<String>(HttpStatus.ok,token));
	}

	private void authenticate(String username, String password) throws DisabledException,BadCredentialsException {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new DisabledException("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("INVALID_CREDENTIALS", e);
		}
	}
	
}
