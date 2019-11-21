package com.crm.jwt.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.crm.common.HttpStatus;
import com.crm.jwt.JwtTokenUtil;
import com.crm.jwt.JwtUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String requestTokenHeader = request.getHeader("Authorization");

		String username = null;
		String jwtToken = null;
		// JWT Token is in the form "Bearer token". Remove Bearer word and get
		// only the Token
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);// INVALID_CREDENTIALS
			} catch (IllegalArgumentException e) {
				log.info("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				log.info("JWT Token has expired");
				ObjectMapper mapper = new ObjectMapper();
				ObjectNode json = mapper.createObjectNode();
				json.put("status", HttpStatus.exception);
				json.put("message", "TOKEN_EXPIRED");
				response.setStatus(HttpServletResponse.SC_OK);
				response.setContentType("application/json;charset=UTF-8");
				response.getWriter().write(json.toString());
				response.getWriter().flush();
				response.getWriter().close();
				return;
			} catch (MalformedJwtException e) {
				ObjectMapper mapper = new ObjectMapper();
				ObjectNode json = mapper.createObjectNode();
				json.put("status", HttpStatus.exception);
				json.put("message", e.getMessage());
				response.setStatus(HttpServletResponse.SC_OK);
				response.setContentType("application/json;charset=UTF-8");
				response.getWriter().write(json.toString());
				response.getWriter().flush();
				response.getWriter().close();
				return;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			logger.warn("JWT Token does not begin with Bearer String");
		}

		// 取得token
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
			// if token is valid configure Spring Security to manually set
			// authentication
			try {
				if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					// After setting the Authentication in the context, we specify
					// that the current user is authenticated. So it passes the
					// Spring Security Configurations successfully.
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		filterChain.doFilter(request, response);

	}

}
