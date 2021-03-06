package com.crm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

import com.crm.jwt.CustomAccessDecisionManager;
import com.crm.jwt.CustomAccessDeniedHandler;
import com.crm.jwt.CustomFilterInvocationSecurityMetadataSource;
import com.crm.jwt.JwtAuthenticationEntryPoint;
import com.crm.jwt.filter.JwtRequestFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
//@Order(-1)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private UserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(getPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub

		/*
		 * http.cors().and().csrf().disable().authorizeRequests()
		 * .requestMatchers(CorsUtils::isPreFlightRequest)
		 * .permitAll().and().authorizeRequests().antMatchers("/authenticate").permitAll
		 * () .anyRequest()
		 * .authenticated().and().exceptionHandling().authenticationEntryPoint(
		 * jwtAuthenticationEntryPoint).and()
		 * .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
		 * and().httpBasic();
		 */

		http.cors().and().csrf().disable().authorizeRequests().anyRequest().authenticated()
				.withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
					@Override
					public <O extends FilterSecurityInterceptor> O postProcess(O object) {
						// TODO Auto-generated method stub
						object.setSecurityMetadataSource(customSecurityMetadataSource());
						object.setAccessDecisionManager(customAccessDecisionManager());
						return object;
					}
				}).and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
				.accessDeniedHandler(customAccessDeniedHandler()).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().httpBasic();

		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	public FilterInvocationSecurityMetadataSource customSecurityMetadataSource() {
		return new CustomFilterInvocationSecurityMetadataSource();
	}

	@Bean
	public AccessDecisionManager customAccessDecisionManager() {
		return new CustomAccessDecisionManager();
	}

	@Bean
	public AccessDeniedHandler customAccessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}

}
