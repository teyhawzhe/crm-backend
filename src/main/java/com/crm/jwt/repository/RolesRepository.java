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
	public List<Roles> findAllByIdUsername(String username);
	
	@Modifying
	@Query(nativeQuery = true , value = "DELETE ROLES WHERE USERNAME = :username")
	public void delete(@Param("username") String username);
	
}
