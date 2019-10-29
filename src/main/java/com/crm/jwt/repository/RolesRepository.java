package com.crm.jwt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crm.jwt.model.Roles;
import com.crm.jwt.model.RolesPk;

@Repository
public interface RolesRepository extends JpaRepository<Roles, RolesPk> {
	public List<Roles> findAllByIdUsername(String username);
}
