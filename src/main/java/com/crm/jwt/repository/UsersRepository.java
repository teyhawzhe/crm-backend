package com.crm.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crm.jwt.model.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {

	
	
}
