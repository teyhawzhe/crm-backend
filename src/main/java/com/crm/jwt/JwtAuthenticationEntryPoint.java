package com.crm.jwt;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -790754456132629139L;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		final String expired = (String) request.getAttribute("expired");

        if (expired!=null){
        	response.sendError(HttpServletResponse.SC_UNAUTHORIZED,expired);
        }else{
        	response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
		
		
	}

}
