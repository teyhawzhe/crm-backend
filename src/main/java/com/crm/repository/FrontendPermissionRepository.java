package com.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.crm.model.entity.FrontendPermission;
import com.crm.model.entity.FrontendPermissionPk;

@Repository
public interface FrontendPermissionRepository extends JpaRepository<FrontendPermission, FrontendPermissionPk> {

	@Query(nativeQuery = true , value = "SELECT PATH ||'&'|| TIER FROM FRONTEND_PERMISSION WHERE ROLE = :role")
	public List<String> query(@Param("role") String role);
	
	@Modifying
	@Query(nativeQuery = true , value = "DELETE FRONTEND_PERMISSION WHERE ROLE = :role")
	public void deleteByRole(@Param("role") String role);
}
