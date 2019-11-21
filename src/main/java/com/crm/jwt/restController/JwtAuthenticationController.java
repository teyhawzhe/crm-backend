package com.crm.jwt.restController;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;

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

import com.crm.common.AttemptException;
import com.crm.common.HttpStatus;
import com.crm.common.Result;
import com.crm.jwt.JwtTokenUtil;
import com.crm.jwt.JwtUserDetailsService;
import com.crm.jwt.model.JwtRequest;
import com.crm.jwt.model.UserInfo;
import com.crm.model.entity.LoginAttempt;
import com.crm.service.login.attempt.LoginAttemptService;
import com.crm.service.login.history.LoginHistoryService;
import com.crm.service.userProfile.UserProfileService;

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

	@Value("${jwt.secret}")
	private String secret;

	@Autowired
	private LoginAttemptService loginAttemptService;
	@Autowired
	private LoginHistoryService loginHistoryService;

	@Autowired
	private UserProfileService userProfileService;
	
	@PostMapping("/user/logout")
	public ResponseEntity<Result<String>> logout() {
		log.info("/logout");
		return ResponseEntity.ok(new Result<String>(HttpStatus.ok, null, "success"));
	}

	@GetMapping("/getUserInfo")
	public ResponseEntity<Result<UserInfo>> getToken(@RequestParam("token") String token) {
		Base64.Decoder decoder = Base64.getDecoder();
		Claims claims = Jwts.parser().setSigningKey(decoder.decode(secret)).parseClaimsJws(token.replace("Bearer ", ""))
				.getBody();

		List<String> roles = (List<String>) claims.get("roles");

		UserInfo userInfo = new UserInfo();
		userInfo.setName(claims.getSubject());
		userInfo.setRoles(roles);
		userInfo.setAvatar(userProfileService.getImage());
		return ResponseEntity.ok(new Result<UserInfo>(HttpStatus.ok, "查詢成功!", userInfo));
	}

	@PostMapping("/authenticate")
	public ResponseEntity<Result<String>> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
			throws Exception {
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new Result<String>(HttpStatus.ok, "登入成功!", token));
	}

	private void authenticate(String username, String password)
			throws DisabledException, BadCredentialsException, AttemptException, Exception {
		try {
			// 取出登入錯誤
			LoginAttempt loginAttempt = loginAttemptService.findByOne(username);
			// 如果登入錯誤
			if (null != loginAttempt) {
				if (loginAttempt.getAttempt() >= 3) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HHmm");
					Date date = sdf.parse(loginAttempt.getAllowDate());
					Date current = new Date();
					if (current.after(date)) {
						loginAttemptService.delete(username);
					} else {
						sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
						throw new AttemptException(username + "已連續登入三次錯誤了，請於" + sdf.format(date) + "後再登入!");
					}
				}
			}
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			// 記錄登入記錄
			loginHistoryService.save(username, true, null);
			// 登入成功 刪除嘗試登入的紀錄
			loginAttemptService.delete(username);
		} catch (DisabledException e) {
			// 記錄登入記錄
			loginHistoryService.save(username, false, "USER_DISABLED");
			throw new DisabledException("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			LoginAttempt loginAttempt = loginAttemptService.findByOne(username);
			// 記錄嘗試登入錯誤的次數
			if (null == loginAttempt) {
				loginAttemptService.save(username, 1);
			} else {
				loginAttemptService.save(username, loginAttempt.getAttempt() + 1);
			}
			// 記錄登入記錄
			loginHistoryService.save(username, false, "INVALID_CREDENTIALS");
			throw new BadCredentialsException("INVALID_CREDENTIALS", e);
		}
	}

}
