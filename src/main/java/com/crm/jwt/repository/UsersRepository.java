package com.crm.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.crm.jwt.model.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {

	@Query(nativeQuery = true , value = "SELECT * FROM USERS WHERE USERNAME = :username")
	public Users findUser(@Param("username")String username);
	
}
