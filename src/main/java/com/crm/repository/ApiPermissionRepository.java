package com.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.crm.model.entity.ApiPermission;
import com.crm.model.entity.ApiPermissionPk;

@Repository
public interface ApiPermissionRepository extends JpaRepository<ApiPermission, ApiPermissionPk> {

	@Query(nativeQuery = true , value = "SELECT API FROM API_PERMISSION WHERE ROLE = :role")
	public List<String> findAllByRole(@Param("role") String role);
	
	@Modifying
	@Query(nativeQuery = true , value = "DELETE API_PERMISSION WHERE ROLE = :role")
	public void delete(@Param("role") String role);
}
