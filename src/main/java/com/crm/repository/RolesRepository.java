package com.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.crm.model.entity.Roles;
import com.crm.model.entity.RolesPk;

@Repository
public interface RolesRepository extends JpaRepository<Roles, RolesPk> {

	@Query(value="SELECT * FROM ROLES WHERE USERNAME=:username",nativeQuery = true)
	public List<Roles> getAllRoleByUsername(String username);
	
}
