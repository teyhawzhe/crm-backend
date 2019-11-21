package com.crm.jwt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.crm.jwt.model.Roles;
import com.crm.jwt.model.RolesPk;

@Repository
public interface RolesRepository extends JpaRepository<Roles, RolesPk> {
	
	@Query(nativeQuery = true , value = "SELECT ROLES.* FROM ROLES LEFT JOIN SETTING_ROLE ON SETTING_ROLE.ROLE =  ROLES.ROLE WHERE SETTING_ROLE.STATUS=1 AND ROLES.USERNAME = :username")
	public List<Roles> findAllByIdUsername(@Param("username") String username);
	
	@Modifying
	@Query(nativeQuery = true , value = "DELETE ROLES WHERE USERNAME = :username")
	public void delete(@Param("username") String username);
	
	@Modifying
	@Query(nativeQuery = true , value = "DELETE ROLES WHERE USERNAME = :username and ROLE NOT IN (:role)")
	public void deleteUsernameAndExcludeRole(@Param("username") String username,@Param("role") List<String> role);
	
	
}
