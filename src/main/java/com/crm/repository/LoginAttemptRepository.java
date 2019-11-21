package com.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.crm.model.entity.LoginAttempt;

@Repository
public interface LoginAttemptRepository extends JpaRepository<LoginAttempt, String> {

	@Query(nativeQuery = true , value = "SELECT * FROM LOGIN_ATTEMPT WHERE USERNAME = :username")
	public LoginAttempt findByOne(@Param("username") String username);
	
	@Modifying
	@Query(nativeQuery = true , value = "DELETE LOGIN_ATTEMPT WHERE USERNAME = :username")
	public void delete(@Param("username") String username);
	
}
