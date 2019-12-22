package com.crm.config.webSocket;

import java.security.Principal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import com.crm.jwt.JwtTokenUtil;
import com.crm.jwt.JwtUserDetailsService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PrincipalHandshakeHandler extends DefaultHandshakeHandler {
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;
	
	@Override
	protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
			Map<String, Object> attributes) {
		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) request;
			HttpServletRequest httpRequest = servletServerHttpRequest.getServletRequest();
			String token = httpRequest.getParameter("token");
			log.info("token " + token);
			if(token.startsWith("Bearer ")) {
				String jwtToken = token.substring(7);
				String username = null;
				try {
					username = jwtTokenUtil.getUsernameFromToken(jwtToken);
				}catch (IllegalArgumentException e) {
					log.info("IllegalArgumentException");
				} catch (ExpiredJwtException e) {
					log.info("ExpiredJwtException");
				}catch (MalformedJwtException e) {
					log.info("ExpiredJwtException");
				}catch (Exception e) {
					log.info("Exception");
				}
				
				if(null==username) {
					return null;
				}
				UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
				try {
					if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
						UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
								userDetails, null, userDetails.getAuthorities());
						usernamePasswordAuthenticationToken
								.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
						SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				log.info("SecurityContextHolder.getContext().getAuthentication() " + SecurityContextHolder.getContext().getAuthentication().getName());
				return SecurityContextHolder.getContext().getAuthentication();
			}else {
				return null;
			}
		}
		return null;
	}

}
