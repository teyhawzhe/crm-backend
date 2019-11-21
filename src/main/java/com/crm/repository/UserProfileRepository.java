package com.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.crm.model.entity.UserProfile;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, String> {

	@Modifying
	@Query(nativeQuery = true , value = "DELETE USER_PROFILE WHERE USERNAME = :username")
	public void delete(@Param("username") String username);
	
}
